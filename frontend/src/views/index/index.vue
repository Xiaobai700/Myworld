<template>
  <el-container>
    <el-header>
      <navbar></navbar>
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
    import Navbar from '../../components/index/Navbar'
    import Sidebar from '../../components/index/Sidebar'
    import AppMain from '../../components/index/AppMain'
    export default {
        name: 'index',
        components: {
            Navbar,
            Sidebar,
            AppMain
        }
    }
</script>

<style lang='less'>
  .el-container{
    height: 100vh;
  }
  .el-header, .el-footer {
    background-color: #B3C0D1;
    color: #333;
    text-align: center;
    line-height: 60px;
  }
  .el-aside {
    background-color: #D3DCE6;
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
