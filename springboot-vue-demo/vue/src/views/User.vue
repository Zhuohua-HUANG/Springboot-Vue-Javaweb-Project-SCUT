<template>
  <div>
    <!--    功能-->
    <div style="margin:10px 0">
      <el-button type="primary" @click="add">新增</el-button>
    </div>
    <!--    搜索区域-->
    <div style="margin:10px 0">
      <el-input v-model="search" placeholder="请输入用户名" style="width: 50%" clearable></el-input>
      <el-button type="primary" style="margin-left:  5px" @click="load">查询</el-button>
    </div>
    <el-table :data="tableData" border stripe style="width: 100%">
      <el-table-column label="ID" prop="id" />
      <el-table-column label="用户名" prop="username" />
      <el-table-column label="昵称" prop="nickName" />
      <el-table-column label="年龄" prop="age" />
      <el-table-column label="性别" prop="sex" />
      <el-table-column label="余额" prop="account" />
      <el-table-column label="邮箱" prop="email" />
      <el-table-column label="角色">
        <template #default="scope">
          <span v-if="scope.row.role===1">顾客</span>
          <span v-if="scope.row.role===2">商家</span>
          <span v-if="scope.row.role===3">管理员</span>
        </template>
      </el-table-column>
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
        <el-form
            :model="form"
            ref="ruleForm"
            label-width="120px">
          <el-form-item label="用户名" >
            <el-input v-model="form.username" autocomplete="off" style="width: 80%"></el-input>
          </el-form-item>
          <el-form-item label="昵称" >
            <el-input v-model="form.nickName" autocomplete="off" style="width: 80%"></el-input>
          </el-form-item>
          <el-form-item label="年龄" >
            <el-input v-model="form.age" autocomplete="off" style="width: 80%"></el-input>
          </el-form-item>
          <el-form-item label="性别" >
            <el-radio v-model="form.sex" label="男">男</el-radio>
            <el-radio v-model="form.sex" label="女">女</el-radio>
            <el-radio v-model="form.sex" label="未知">未知</el-radio>
          </el-form-item>
          <el-form-item prop="account" label="余额" >
            <el-input v-model="form.account" autocomplete="off" style="width: 80%"></el-input>
          </el-form-item>
          <el-form-item label="邮箱地址" >
            <el-input v-model="form.email" autocomplete="off" style="width: 80%"></el-input>
          </el-form-item>
          <el-form-item label="角色">
            <el-radio v-model="form.role" label="1">顾客</el-radio>
            <el-radio v-model="form.role" label="2">商家</el-radio>
            <el-radio v-model="form.role" label="3">管理员</el-radio>
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

export default {
  name: 'user' ,
  data() {
    return {
      dialogFormVisible:false,
      form:{},
      currentPage :1,
      tableData: [
      ],
      search: '',
      total:10,
      ruleForm: {},
      rules: {
        account: [
          {
            required: true,
            message: '请充值',
            trigger: 'blur',
          },
          {
            min: 0,
            max: 8,
            message: '长度在8八位数以内',
            trigger: 'blur',
          },
        ],
      },
    }
  },
  created() {
    this.load()
  },
  methods: {
    load(){
      request.get("/user",{
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
    add(){
      this.dialogFormVisible = true;
      this.form={};
    },
    save(){
      if(this.form.id){
        //update
        request.put("/user",this.form).then(res =>{
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
        request.post("/user",this.form).then(res =>{
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
    },
    handleDelete(id) {
      console.log(id)
      request.delete("/user/"+id).then(res=>{
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
