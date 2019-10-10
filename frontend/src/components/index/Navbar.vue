<template>
  <el-menu class="navbar" mode="horizontal">
    <breadcrumb></breadcrumb>
  </el-menu>
</template>

<script>
  import { mapGetters } from 'vuex'
  import Breadcrumb from '../index/Breadcrumb'

  export default {
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
    float: left;
  }
</style>

