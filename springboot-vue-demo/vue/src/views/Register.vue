<template>
  <div style="width: 100%;height: 100vh;background-color: powderblue; overflow: hidden">
    <div style="width: 400px;margin:100px auto">
      <div style="color: dodgerblue ;font-size: 30px;text-align: center;padding: 30px 0" >
        欢迎注册
      </div>
      <el-form
          ref="ruleForm"
          :model="ruleForm"
          status-icon
          :rules="rules"
          size="normal"
          label-width="120px"
          class="demo-ruleForm"
      >
        <el-form-item   prop="username"         label="用户名">
          <el-input
              v-model="ruleForm.username"
              type="username"
              autocomplete="off"
          ></el-input>
        </el-form-item>
        <el-form-item   prop="nickName"         label="昵称">
          <el-input
              v-model="ruleForm.nickName"
              type="username"
              autocomplete="off"
          ></el-input>
        </el-form-item>
        <el-form-item    prop="password"        label="密码 ">
          <el-input
              v-model="ruleForm.password"
              type="password"
              autocomplete="off"
          ></el-input>
        </el-form-item>
        <el-form-item   prop="confirm"       label="确认密码 ">
          <el-input
              v-model="ruleForm.confirm"
              type="password"
              autocomplete="off"
          ></el-input>
        </el-form-item>
        <el-form-item prop="sex" label="性别">
          <el-radio v-model="ruleForm.sex" label="男">男</el-radio>
          <el-radio v-model="ruleForm.sex" label="女">女</el-radio>
          <el-radio v-model="ruleForm.sex" label="未知">未知</el-radio>
        </el-form-item>
        <el-form-item   prop="email"       label="电子邮箱 ">
          <el-input
              v-model="ruleForm.email"
              type="email"
              autocomplete="off"
          ></el-input>
        </el-form-item>
        <el-form-item prop="role" label="角色">
          <el-radio v-model="ruleForm.role" label="1">顾客</el-radio>
          <el-radio v-model="ruleForm.role" label="2">商家</el-radio>
        </el-form-item>
        <el-form-item>
          <el-button style="width: 100%" type="primary" @click="register"
          >注册</el-button
          >
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
import request from "../../utils/request";

export default {
  name: "Register",
  data() {
    return {
      ruleForm: {},
      rules: {
        username: [
          {
            required: true,
            message: '请输入用户名',
            trigger: 'blur',
          },
        ],
        password: [
          {
            required: true,
            message: '请输入密码',
            trigger: 'blur',
          },
        ],
        confirm: [
          {
            required: true,
            message: '请再次输入密码',
            trigger: 'blur',
          },
        ],
        email: [
          {
            required: true,
            message: '请输入邮箱地址',
            trigger: 'blur',
          },
        ],
        role: [
          {
            required: true,
            message: '请选择角色',
            trigger: 'blur',
          },
        ],
        sex: [
          {
            required: true,
            message: '请选择性别',
            trigger: 'blur',
          },
        ],
        nickName: [
          {
            required: true,
            message: '请输入昵称',
            trigger: 'blur',
          },
        ],
      }
    }
  },
  methods: {
    register() {
      if(this.ruleForm.password!==this.ruleForm.confirm){
        this.$message({
          type: "error",
          message: "两次输入密码不一致"
        })
        return
      }
      this.$refs["ruleForm"].validate((valid) => {
        if (valid) {
          request.post("/user/register",this.ruleForm).then(res=>{
            if(res.code==='0'){
              this.$message({
                type:"success",
                message:"注册成功"
              })
              this.$router.push("/login")//跳转到登录
            }else{
              this.$message({
                type: "error",
                message: res.msg
              })
            }
          })
        } else {
          console.log('error submit!!')
          return false
        }
      })

    },
  },
}
</script>

<style scoped>

</style>