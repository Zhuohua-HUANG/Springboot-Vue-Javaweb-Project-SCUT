<template>
  <div>
    <el-card style="width: 40%; margin: 10px">
      <el-form ref="form"
               :rules="rules"
               :model="user_form"
               label-width="80px">
        <el-form-item style="text-align: center" label-width="0">
          <el-upload
              class="avatar-uploader"
              action="http://localhost:9876/files/upload"
              :show-file-list="false"
              :on-success="handleAvatarSuccess"
          >
            <img v-if="user_form.avatar" :src="user_form.avatar" class="avatar">
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
        </el-form-item>
        <el-form-item label="用户名">
          <el-input v-model="user_form.username" disabled></el-input>
        </el-form-item>
        <el-form-item label="昵称">
          <el-input v-model="user_form.nickName"></el-input>
        </el-form-item>
        <el-form-item label="年龄">
          <el-input v-model="user_form.age"></el-input>
        </el-form-item>
        <el-form-item label="性别">
          <el-input v-model="user_form.sex"></el-input>
        </el-form-item>
        <!--        <el-form-item label="密码">-->
        <!--          <el-input v-model="form.password" show-password></el-input>-->
        <!--        </el-form-item>-->
        <el-form-item prop="account" label="余额(￥)">
          <el-input v-model="user_form.account" show-password></el-input>
        </el-form-item>
        <el-form-item label="邮箱地址">
          <el-input v-model="user_form.email"></el-input>
        </el-form-item>
      </el-form>
      <div style="text-align: center">
        <el-button type="primary" @click="update">保存</el-button>
      </div>
      <div style="text-align: center">
        <el-button type="primary" @click="unsubscribe">注销</el-button>
      </div>
    </el-card>

  </div>
</template>

<script>
import request from "../../utils/request";

export default {
  name: "Person",
  data() {
    return {
      user_form: {},
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
    let str = sessionStorage.getItem("user") || "{}"
    this.user_form = JSON.parse(str)
  },
  methods: {
    unsubscribe(){
      request.delete("/user/"+this.user_form.id).then(res => {
        console.log(res)
        if (res.code === '0') {
          this.$message({
            type: "success",
            message: "注销成功"
          })
          sessionStorage.setItem("user", JSON.stringify(null))
          this.$router.push('/login')
        } else {
          this.$message({
            type: "error",
            message: res.msg
          })
        }
      })
    },
    update() {
      console.log(this.user_form)
      request.put("/user", this.user_form).then(res => {
        console.log(res)
        if (res.code === '0') {
          this.$message({
            type: "success",
            message: "更新成功"
          })
          sessionStorage.setItem("user", JSON.stringify(this.user_form))
          // 触发Layout更新用户信息
          this.$emit("userInfo")
        } else {
          this.$message({
            type: "error",
            message: res.msg
          })
        }
      })
    }
  }
}
</script>

<style>
.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}
.avatar-uploader .el-upload:hover {
  border-color: #409EFF;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 120px;
  height: 120px;
  line-height: 120px;
  text-align: center;
}
.avatar {
  width: 178px;
  height: 178px;
  display: block;
}
</style>
