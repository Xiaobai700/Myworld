<template>
    <el-container>
      <el-header>
        <page-header></page-header>
      </el-header>
      <el-container>
        <el-aside width="200px">
          <sidebar></sidebar>
        </el-aside>
        <el-container>
          <el-header style="height: 38px;background-color: rgb(245,245,245)">
            <navbar></navbar>
          </el-header>
          <el-main>
            <app-main></app-main>
          </el-main>
        </el-container>
      </el-container>
    </el-container>
</template>

<script>
    import PageHeader from '../../components/index/PageHeader'
    import Sidebar from '../../components/index/Sidebar'
    import AppMain from '../../components/index/AppMain'
    import Navbar from "../../components/index/Navbar";
    export default {
        name: 'index',
        components: {
            Navbar,
            PageHeader,
            Sidebar,
            AppMain
        },
      computed: {
        sidebar() {
          return this.$store.state.app.sidebar
        }
      }
    }
</script>

<style lang='less'>
  body{
    margin: 0;
    padding: 0;
    border: 0;
  }
  .el-container{
     height: 93.5vh;
  }
  .el-header{
    background-color: #4868AC;
    color: #333;
    text-align: center;
    line-height: 60px;
  }
  .el-aside {
    background-color: rgb(84,92,100);
    color: white;
    text-align: center;
    line-height: 200px;
  }
  .el-main {
    background-color: #E9EEF3;
    color: #333;
    text-align: center;
    margin: 0;
    padding: 0;
    border: 0;
  }
  .main-container{
   //max-width: 800px;
    height: 100%;
    margin: 0 auto;
  }
  body > .el-container {
    margin-bottom: 40px;
  }
  .el-container:nth-child(5) .el-aside,
  .el-container:nth-child(6) .el-aside {
    line-height: 260px;
  }
  .el-container:nth-child(7) .el-aside {
    line-height: 320px;
  }
</style>
