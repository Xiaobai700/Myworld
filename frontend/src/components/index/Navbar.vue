<template>
  <el-menu class="navbar" mode="horizontal">
    <div style="float: left;">
      <breadcrumb></breadcrumb>
    </div>
    <div style="float: right;">
      <el-badge :value="12">
        <el-button size="small">消息</el-button>
      </el-badge>
    </div>
  </el-menu>
</template>

<script>
  import { mapGetters } from 'vuex'
  import Breadcrumb from '../index/Breadcrumb'

  export default {
    data(){
      return{
        infoVisible:false
      };
    },
    components: {
      Breadcrumb
    },
    computed: {
      ...mapGetters([
        'sidebar',
        'avatar'
      ])
    },
    methods: {
    }
  }
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
  .navbar {
    height: 38px;
    line-height: 38px;
    background-color: rgb(245,245,245);
    //float: left;
  }
</style>

