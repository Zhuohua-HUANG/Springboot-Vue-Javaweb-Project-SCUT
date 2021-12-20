<template>
  <div>
    <el-table :data="tableData" border stripe style="width: 100%">
      <el-table-column label="日志ID" prop="id" />
      <el-table-column label="时间" prop="LocalDateTime" />
      <el-table-column label="顾客ID" prop="userId" />
      <el-table-column label="顾客账号" prop="username" />
      <el-table-column label="商品ID" prop="storageId"/>
      <el-table-column label="品名" prop="storageName" />
      <el-table-column label="操作" prop="action" />
    </el-table>
  </div>
</template>

<script>
import request from "../../utils/request";

export default {
  name: 'log' ,
  data() {
    return {
      dialogFormVisible:false,
      currentPage :1,
      tableData: [
      ],
      roleData:[],
      search: '',
      total:10,
      dialogImageUrl: '',
      user:0,
      user_form:{},
      Visible:false,
      customer_form:{},
      storage_form:{},
      log_form:{},
    }
  },
  created() {
    let str = sessionStorage.getItem("user") || "{}"
    this.user_form = JSON.parse(str)
    this.load()
  },
  methods: {
    load(){
      if(this.user_form.role===2){
        request.get("/logController",{
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
      }else if(this.user_form.role===1){
        this.$router.push("/customer")
      }else {
        request.get("/logController/admin",{
          params:{
            pageNum: this.currentPage,
            pageSize: this.pageSize,
          }
        }).then(res=>{
          console.log(res)
          this.tableData=res.data.records
          this.total=res.data.total
        })
      }
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
