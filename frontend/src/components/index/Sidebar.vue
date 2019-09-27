<template>
  <div class="leftBanner-box">
    <el-menu
      :router="true"
      class="el-menu-vertical-demo">
      <el-submenu index="1">
        <template slot="title">
          <i class="el-icon-location"></i>
          <span>订单</span>
        </template>
        <el-menu-item-group>
          <el-menu-item :route="{name: 'page1'}" index="1-1">page1</el-menu-item>
          <el-menu-item :route="{name: 'page2'}" index="1-2">page2</el-menu-item>
        </el-menu-item-group>
      </el-submenu>
      <el-menu-item :route="{name: 'page3'}" index="2">
        <i class="el-icon-menu"></i>
        <span slot="title">活动</span>
      </el-menu-item>
      <el-menu-item :route="{name: 'page4'}" index="3">
        <i class="el-icon-document"></i>
        <span slot="title">用户</span>
      </el-menu-item>
    </el-menu>
  </div>
</template>

<script>
    export default {
        data () {
            return {
            }
        },
        components: { // 组件
        },
        computed: { // 计算
        },
        methods: { // 方法事件
        },
        mounted () { // 加载完成
        },
        created () { // 创建
        }
    }
</script>

<style lang='less'>
  .leftBanner-box{
    height: 100%;
    .el-menu-vertical-demo{
      height: 100%;
      background-color: #D3DCE6;
    }
  }
</style>
