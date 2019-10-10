<template>
  <div class="leftBanner-box">
    <el-menu
      :router="true"
      class="el-menu-vertical-demo">
      <el-submenu index="1">
        <template slot="title">
          <i class="el-icon-notebook-1"></i>
          <span>文章</span>
        </template>
        <el-menu-item-group>
          <el-menu-item :route="{name: 'page1'}" index="1-1">文章列表</el-menu-item>
          <el-menu-item :route="{name: 'page2'}" index="1-2">page2</el-menu-item>
        </el-menu-item-group>
      </el-submenu>

      <el-submenu index="2">
        <template slot="title">
          <i class="el-icon-umbrella"></i>
          <span>测试</span>
        </template>
        <el-menu-item-group>
          <el-menu-item :route="{name: 'page3'}" index="2-1">
            <i class="el-icon-umbrella"></i>
            <span slot="title">transition例子</span>
          </el-menu-item>
          <el-menu-item :route="{name: 'page4'}" index="2-2">
            <i class="el-icon-basketball"></i>
            <span slot="title">测试页面2</span>
          </el-menu-item>
        </el-menu-item-group>
      </el-submenu>

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
