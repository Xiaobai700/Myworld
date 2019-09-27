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
    export default {
        name: 'index',
        components: {
            PageHeader,
            Sidebar,
            AppMain
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
     height: 100vh;
  }
  .el-header{
    background-color: #4868AC;
    color: #333;
    text-align: center;
    line-height: 60px;
  }
  .el-aside {
    background-color: #304156;
    color: #333;
    text-align: center;
    line-height: 200px;
  }
  .el-main {
    background-color: #E9EEF3;
    color: #333;
    text-align: center;
  }
  .main-container{
    max-width: 800px;
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
