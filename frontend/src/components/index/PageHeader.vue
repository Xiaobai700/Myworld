<template>
  <div>
    <div class="left">
      <img src="../../assets/header_logo.png" alt="">
    </div>
    <div class="right">
      <el-dropdown :hide-on-click="false">
        <span class="el-dropdown-link">
          <i class="el-icon-user-solid"></i><i class="el-icon-arrow-down el-icon--right"></i>
        </span>
        <el-dropdown-menu slot="dropdown">
          <el-dropdown-item command="a">
            <span @click="logout" style="display:block;">退出</span>
          </el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
    </div>
  </div>
</template>

<script>
    export default {
        name: "Header",
        methods:{
            logout(){
                console.log("退出");
                this.$store.dispatch('LogOut').then(data =>{
                    this.$router.push({path: '/'})
                })
            }
        }
    }
</script>

<style scoped>
.left{
  float: left;
}
.right{
    float: right;
}
.el-dropdown-link {
  cursor: pointer;
  color: #409EFF;
}
.el-icon-arrow-down {
  font-size: 12px;
}
</style>
