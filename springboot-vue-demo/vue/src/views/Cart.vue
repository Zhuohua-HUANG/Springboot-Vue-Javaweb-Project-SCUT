<template>
  <div>
    <el-table :data="tableData" border stripe style="width: 100%">
      <el-table-column label="商品ID" prop="id" />
      <el-table-column label="商户ID" prop="storeId"></el-table-column>
      <el-table-column label="名称" prop="name" />
      <el-table-column label="价格(¥)" prop="price" />
      <el-table-column label="品牌" prop="brand" />
      <el-table-column label="出厂时间" prop="createTime" />
      <el-table-column label="购买数量" prop="num" />
      <el-table-column
          label="封面">
        <template #default="scope">
          <el-image
              style="width: 100px; height: 100px"
              :src="scope.row.cover"
              :preview-src-list="[scope.row.cover]"
              :initial-index="1"
          >
          </el-image>
        </template>
      </el-table-column>
      <!--      <el-table-column label="状态操作">-->
      <!--        <template #default="scope">-->
      <!--          <el-button size="mini" @click="switchState(1)"-->
      <!--          >上架</el-button-->
      <!--          >-->
      <!--          <el-button size="mini" @click="switchState(0)"-->
      <!--          >下架</el-button-->
      <!--          >-->
      <!--        </template>-->
      <!--      </el-table-column>-->
      <el-table-column label="数量修改" >
        <template #default="scope">
          <el-input
              v-model="scope.row.num"
              autocomplete="off"
              style="width: 80%"></el-input>
          <el-button
              size="mini"
              @click="save(scope.row.num,scope.row.id)"
          >确认</el-button
          >
        </template>
      </el-table-column>
      <el-table-column label="操作">
        <template #default="scope">
          <el-popconfirm title="你确定要提交订单嘛？" @confirm="handleSubmit(scope.row.num,scope.row.id,scope.row.price,scope.row.storeId)">
            <template #reference>
              <el-button
                  size="mini"
              >支付并提交</el-button
              >
            </template>
          </el-popconfirm>
          <el-popconfirm title="你确定要删除嘛？" @confirm="handleDelete(scope.row.num,scope.row.id)">
            <template #reference>
              <el-button
                  size="mini"
                  type="danger"
                  style="width: 70px"
              >删除</el-button
              >
            </template>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>
    <div class="demo-pagination-block">
      <!--    以下是显示页面码-->
      <el-pagination
          v-model:current-page="currentPage"
          :page-sizes="[5, 10, 20, 25]"
          :page-size="10"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
      >
      </el-pagination>
      <!-- Form -->
    </div>
  </div>
</template>

<script>
import request from "../../utils/request";

export default {
  name: 'cart' ,
  data() {
    return {
      dialogFormVisible:false,
      form:{},
      currentPage :1,
      tableData: [
      ],
      roleData:[],
      search: '',
      total:10,
      dialogImageUrl: '',
      user_form:{},
    }
  },
  created() {
    let str = sessionStorage.getItem("user") || "{}"
    this.user_form = JSON.parse(str)
    this.form.userId=this.user_form.id
    this.form.role=this.user_form.role
    this.load()
  },
  methods: {
    filesUploadSuccess(res,file){
      console.log(res)
      this.form.cover=res.data
    },
    load(){
      request.get("/USC",{
        params:{
          pageNum: this.currentPage,
          pageSize: this.pageSize,
          search: this.search,
          id:this.user_form.id
        }
      }).then(res=>{
        console.log(res)
        this.tableData=res.data.records
        this.total=res.data.total
      })
    },
    //保存购物车修改
    save(num,id){
      this.form.num=num
      this.form.storageId=id
      console.log(this.form)
      request.put("/USC",this.form).then(res =>{
        console.log(res)
        if(res.code==='0'){
          this.$message({
            type:"success",
            message:"更新成功"
          })
        }else {
          this.$message({
            type:"error",
            message:res.msg
          })
        }
        this.load()
      })

    },

    //提交订单
    handleSubmit(num,id,price,sid){
      this.form.num=num
      this.form.storageId=id
      this.form.customerId=this.user_form.id
      this.form.storeId=sid
      this.form.ostate=0
      console.log(this.form)
      request.post("/Order",this.form, {params:{
        id:this.user_form.id,
        price:price

      }
        }).then(res =>{
        console.log(res)
        if(res.code==='0'){
          this.$message({
            type:"success",
            message:"提交订单成功"
          })
        }else {
          this.$message({
            type:"error",
            message:res.msg
          })
        }
        this.load()
      })

      this.handleDelete(num,id)
    },

    //删除购物车
    handleDelete(num,id) {
      this.form.num=num
      this.form.storageId=id
      console.log(this.form)
      request.post("/USC/deleteEntity",this.form).then(res=>{
        if(res.code==='0'){
          this.$message({
            type:"success",
            message:"删除成功"
          })
        }else {
          this.$message({
            type: "error",
            message: res.msg
          })
        }
        this.load()
      })
    },

    //页面修改
    handleSizeChange(pageSize){
      this.pageSize=pageSize
      this.load()
    },
    handleCurrentChange(pageNum){//改变页码
      this.currentPage=pageNum
      this.load()
    }
  },
}
</script>
