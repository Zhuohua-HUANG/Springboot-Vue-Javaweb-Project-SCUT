
<template>
  <div style="height:80px;line-height: 50px;border-bottom: 1px solid #ccc;display: flex">
    <div
        style="width: 1200px; padding-left:30px;font-weight: bold;color: black; font-size: 50px"
    >ABSURD </div>
    <div style="flex: 1"></div>
    <div style="width: 100px">
      <el-dropdown>
        <span class="el-dropdown-link" >
          {{user.nickName}}
          <el-icon><arrow-down-bold /></el-icon>
        </span>
        <template #dropdown>
          <el-dropdown-menu>
            <el-dropdown-item @click="$router.push('/person')">个人信息</el-dropdown-item>
            <el-dropdown-item @click="logout">退出系统</el-dropdown-item>
          </el-dropdown-menu>
        </template>
      </el-dropdown>
    </div>
  </div>
</template>

<script>
import {defineComponent} from "vue";
import {ArrowDownBold} from "@element-plus/icons";

export default defineComponent({
  name:"Header",
  props:['user'],
  data(){
    return{
      user:{}
    }
  },
  created() {
    let str = sessionStorage.getItem("user") || "{}"
    this.user= JSON.parse(str)
  },
  components: {
    ArrowDownBold,
  },
  methods:{
    logout(){
      sessionStorage.setItem("user", JSON.stringify(null))
      this.$router.push('/login')
    }
  }
})
</script>

<style scoped>

</style>