<template>
  <div>
    <!--    搜索区域-->
    <div style="margin:10px 0">
      <el-input v-model="search" placeholder="请输入关键字" style="width: 50%;border-bottom-color: black" clearable></el-input>
      <el-button type="primary" style="margin-left:  5px ;background-color: black; color: white;border-color: white;" @click="load">查询</el-button>
    </div>
    <div>
      <el-row>
        <el-col
            v-for="(o, index) in tableData.length"
            :key="o"
            :span="110"
            :offset="1"
        >
          <el-card
              style="width: 300px; height:430px"

              :body-style="{ padding: '0px' }">
            <el-image
                style="width: 300px; height:300px"
                :src="tableData[index].cover"
                fit="cover"
                class="image"
            />
            <div style="padding: 14px">
              <span style="font-weight: bold;" >{{tableData[index].name}}</span>
              <div style="color:gray;">{{tableData[index].price+"元"}}</div>
              <div class="bottom">
                <el-button type="text"  class="button" @click="handleEdit( tableData[index])" >查看详情</el-button>
              </div>
            </div>
          </el-card>
        </el-col>
      </el-row>
    </div>


    <div>
      <el-row>
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
      </el-row>
      <el-dialog v-model="dialogFormVisible" title="添加">
        <el-form
            :rules="rules"
            :model="form"
            label-width="120px">
          <el-form-item >
            <el-image
                style="width: 300px; height:300px"
                :src="form.cover"
                fit="cover"
                class="image"
            />
          </el-form-item>
          <el-form-item label="名称" >
            <el-input disabled v-model="form.name" autocomplete="off" style="width: 80%"></el-input>
          </el-form-item>
          <el-form-item label="价格" >
            <el-input disabled v-model="form.price" autocomplete="off" style="width: 80%"></el-input>
          </el-form-item>
          <el-form-item label="品牌" >
            <el-input disabled v-model="form.brand" autocomplete="off" style="width: 80%"></el-input>
          </el-form-item>
          <el-form-item label="出厂时间" >
            <el-date-picker disabled v-model="form.createTime" fvalue-format="YYYY-MM-DD" type="date" style="width: 80%" clearable></el-date-picker>
          </el-form-item>
          <el-form-item prop="num" label="购买数量" >
            <el-input
                v-model="form.num"
                autocomplete="off"
                style="width: 80%"></el-input>
          </el-form-item>
        </el-form>
        <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取消</el-button>
        <el-button type="primary" @click="AddToCard" style="margin-left:  5px ;background-color: black; color: white;border-color: white;"
        >放入购物车</el-button
        >
      </span>
        </template>
      </el-dialog>
    </div>
  </div>
</template>

<script>
import request from "../../utils/request";
import { UploadFilled } from '@element-plus/icons'

export default {
  name: 'customer' ,
  components:{
    UploadFilled,
  },
  data() {
    return {
      dialogFormVisible:false,
      form:{
      },
      currentPage :1,
      tableData: [
      ],
      rules: {
        num: [
          {
            required: true,
            message: '请输入数量',
            trigger: 'blur',
          },
        ],
      },
      search: '',
      total:10,
      dialogImageUrl: '',
      shoppingCart:{
      },
      user:{},
      log_form:{},
    }
  },
  created() {
    let str = sessionStorage.getItem("user")
    this.user = JSON.parse(str)
    this.load()
  },
  methods: {
    // switchState(st) {
    //   this.form.state=st
    //   request.put("/customer",this.form).then(res => {
    //     console.log(res)
    //     if (res.code === '0') {
    //       this.$message({
    //         type: "success",
    //         message: "更新成功"
    //       })
    //     } else {
    //       this.$message({
    //         type: "error",
    //         message: res.msg
    //       })
    //     }
    //   })
    // },
    filesUploadSuccess(res,file){
      console.log(res)
      this.form.cover=res.data

    },
    load(){
      request.get("/storage",{
        params:{
          pageNum: this.currentPage,
          pageSize: this.pageSize,
          search: this.search
        }
      }).then(res=>{
        console.log(res)
        this.tableData=res.data.records
        this.total=res.data.total
      })
    },
    AddToCard(){
      if(this.form.id) {
        this.shoppingCart.userId = this.user.id+ ''
        this.shoppingCart.storageId = this.form.id+ ''
        this.shoppingCart.num = this.form.num
        this.shoppingCart.role = this.user.role
        //update
        console.log("start")
        console.log(this.shoppingCart)
        console.log("end")
        request.post("/USC", this.shoppingCart).then(res => {
          console.log(res)
          if (res.code === '0') {
            this.$message({
              type: "success",
              message: "已加入购物车"
            })
          } else {
            this.$message({
              type: "error",
              message: res.msg
            })
          }
          this.load()
          this.dialogFormVisible = false
        })
      }
    },

    detail(row){
      this.form=JSON.parse(JSON.stringify(row))
      this.dialogFormVisible=true
      this.$nextTick(()=>{
        this.$refs['upload'].clearFiles()
      })
    },
    handleEdit(row) {
      this.form=JSON.parse(JSON.stringify(row))
      this.form.num=1
      this.dialogFormVisible=true
      this.log_form.userId=this.user.id+ ''
      this.log_form.storageId=this.form.id+ ''
      this.log_form.action="浏览"
      console.log(this.log_form)
      request.post("/logController",this.log_form).then(res => {
        console.log(res)
        if (res.code === '0') {
          this.$message({
            type: "success",
            message: "浏览日志保存成功"
          })
        } else {
          this.$message({
            type: "error",
            message: res.msg
          })
        }
      })
    },
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


<style>
.el-row {
  margin-bottom: 20px;
}
.el-col {
  border-radius: 4px;
}
.bottom {
  margin-top: 13px;
  line-height: 12px;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.button {
  padding: 0;
  min-height: auto;
}

.image {
  width: 100%;
  display: block;
}
</style>