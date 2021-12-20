<template>
  <div style="width: 100%;height: 100vh;background-color: white; overflow: hidden">
    <div style="width: 400px;margin:100px auto">
      <div
          style="text-align: center; font-weight: bold;color: black; font-size: 100px"
      >ABSURD </div>
      <div style="color: black ;font-size: 30px;text-align: center;padding: 30px 0" >
        欢迎登录
      </div>
      <el-form
          ref="ruleForm"
          :model="ruleForm"
          status-icon
          :rules="rules"
          size="normal"
          label-width="20px"
          style=""
          class="demo-ruleForm"
      >
        <el-form-item   prop="username"        >
          <el-input
              prefix-icon="el-icon-user-solid"
              v-model="ruleForm.username"
              style="color: white;border-color: white;"
              type="username"
              autocomplete="off"
          ></el-input>
        </el-form-item>
        <el-form-item   prop="password"         >
          <el-input
              prefix-icon="el-icon-user-lock"
              style="color: white;border-color: white;"
              v-model="ruleForm.password"
              type="password"
              autocomplete="off"
          ></el-input>
        </el-form-item>
        <el-form-item>
          <el-button style="width: 100%;text-align: center;background-color: black;color: white;border-color: white" type="primary"  @click="login"
          >登录</el-button
          >
        </el-form-item>
        <el-form-item>
          <el-button style="width: 100%;text-align: center;background-color: black;color: white;border-color: white" type="primary"   @click="register"
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
  name: "Login",
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
      }
    }
  },
  created() {
  },
  methods: {
    register(){
      this.$router.push("/register")
    },
    login() {
      let str = sessionStorage.getItem("user") || "{}"
      this.$refs["ruleForm"].validate((valid) => {
        if (valid) {
          request.post("/user/login",this.ruleForm).then(res=>{
            console.log(res)
            if(res.code==='0'){
              this.$message({
                type:"success",
                message:"登录成功"
              })

              sessionStorage.setItem("user", JSON.stringify(res.data))  // 缓存用户信息
              if (res.data.role===1)
              {
                this.$router.push("/customer")//跳转到用户
              }else if(res.data.role===2) {
                this.$router.push("/storage")//跳转到主页}
              }else {
                this.$router.push("/user")
              }

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