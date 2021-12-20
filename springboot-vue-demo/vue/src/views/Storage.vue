<template>
  <div>
    <!--    功能-->
    <div v-if="user.role===2" style="margin:10px 0">
      <el-button type="primary" @click="add">新增</el-button>
    </div>
    <!--    搜索区域-->
    <div style="margin:10px 0">
      <el-input v-model="search" placeholder="请输入商品名称" style="width: 50%" clearable></el-input>
      <el-button type="primary" style="margin-left:  5px" @click="load">查询</el-button>
    </div>
    <el-table :data="tableData" border stripe style="width: 100%">
      <el-table-column label="商户ID" prop="storeId"></el-table-column>
      <el-table-column label="ID" prop="id" />
      <el-table-column label="名称" prop="name" />
      <el-table-column label="价格(¥)" prop="price" />
      <el-table-column label="品牌" prop="brand" />
      <el-table-column label="出厂时间" prop="createTime" />
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
      <el-table-column label="销量" prop="salesVolume" />
      <el-table-column label="状态" >
        <template #default="scope">
          <span v-if="scope.row.state===0">
            下架
          </span>
          <span v-if="scope.row.state===1">上架</span>
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
      <el-table-column label="操作">
        <template #default="scope">
          <el-button size="mini" @click="handleEdit( scope.row)"
          >编辑</el-button
          >
          <el-popconfirm title="你确定要删除嘛？" @confirm="handleDelete(scope.row.id)">
            <template #reference>
              <el-button
                  size="mini"
                  type="danger"
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

      <el-dialog v-model="dialogFormVisible" title="添加">
        <el-form :model="form" label-width="120px">
          <el-form-item label="名称" >
            <el-input v-model="form.name" autocomplete="off" style="width: 80%"></el-input>
          </el-form-item>
          <el-form-item label="价格" >
            <el-input v-model="form.price" autocomplete="off" style="width: 80%"></el-input>
          </el-form-item>
          <el-form-item label="品牌" >
            <el-input v-model="form.brand" autocomplete="off" style="width: 80%"></el-input>
          </el-form-item>
          <el-form-item label="出厂时间" >
            <el-date-picker v-model="form.createTime" fvalue-format="YYYY-MM-DD" type="date" style="width: 80%" clearable></el-date-picker>
          </el-form-item>
          <el-form-item label="封面">
            <el-upload
                ref="upload"
                drag
                :action="filesUploadUrl"
                :on-success="filesUploadSuccess"
                :on-preview="handlePictureCardPreview"
                :on-remove="handleRemove"
                multiple
            >
              <el-icon class="el-icon--upload"><upload-filled /></el-icon>
              <div class="el-upload__text">
                把文件拖拽到这里或者 <em>点击这里上传</em>
              </div>
              <template #tip>
                <div class="el-upload__tip">
                  请放置jpg或png文件
                </div>
              </template>
            </el-upload>
          </el-form-item>
          <el-form-item label="状态" >
            <el-radio v-model="form.state" label="1">上架</el-radio>
            <el-radio v-model="form.state" label="0">下架</el-radio>
          </el-form-item>
        </el-form>
        <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取消</el-button>
        <el-button type="primary" @click="save"
        >确认</el-button
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
  name: 'storage' ,
  components:{
    UploadFilled,
  },
  data() {
    return {
      dialogFormVisible:false,
      form:{},
      currentPage :1,
      tableData: [
      ],
      search: '',
      total:10,
      dialogImageUrl: '',
      user:{},
      filesUploadUrl: "http://"+window.server.filesUploadUrl+":9090/files/upload"
    }
  },
  created() {
    let str = sessionStorage.getItem("user")
    this.user = JSON.parse(str)
    if(this.user.role===2){
      this.form.storeId=this.user.id
    }
    this.load()
  },
  methods: {
    // switchState(st) {
    //   this.form.state=st
    //   request.put("/storage",this.form).then(res => {
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
      if(this.user.role===3){
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
      }else{
        request.get("/storage/getStore",{
          params:{
            pageNum: this.currentPage,
            pageSize: this.pageSize,
            search: this.search,
            id:this.user.id,
          }
        }).then(res=>{
          console.log(res)
          this.tableData=res.data.records
          this.total=res.data.total
        })
      }
    },
    add(){
      this.dialogFormVisible = true;
      this.form={};
      this.$refs['upload'].clearFiles()
    },
    save(){
      console.log(this.user)
      if(this.user.role===2){
        this.form.storeId=this.user.id
      }
      if(this.form.id){
        //update
        console.log(this.form)
        request.put("/storage",this.form).then(res =>{
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
          this.dialogFormVisible =false
        })
      }else{
        request.post("/storage",this.form).then(res =>{
          console.log(res)
          if(res.code==='0'){
            this.$message({
              type:"success",
              message:"新增成功"
            })
          }else {
            this.$message({
              type:"error",
              message:res.msg
            })
          }
          this.load()
          this.dialogFormVisible=false
        })
      }
    },

    handleEdit(row) {
      this.form=JSON.parse(JSON.stringify(row))
      this.dialogFormVisible=true
      this.$nextTick(()=>{
        this.$refs['upload'].clearFiles()
      })
    },
    handleDelete(id) {
      console.log(id)
      request.delete("/storage/"+id).then(res=>{
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
