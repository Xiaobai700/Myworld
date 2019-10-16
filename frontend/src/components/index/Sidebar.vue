<template>
  <div class="leftBanner-box">
    <el-menu
      :router="true"
      class="el-menu-vertical-demo">
      <el-submenu index="1">
        <template slot="title">
          <i class="el-icon-notebook-1"></i>
          <span>文章管理</span>
        </template>
        <el-menu-item-group>
          <el-menu-item :route="{name: 'article'}" index="1-1">
            <i class="el-icon-grape"></i>
            <span>文章列表</span>
          </el-menu-item>
          <el-menu-item :route="{name: 'newArticle'}" index="1-2">
            <i class="el-icon-watermelon"></i>
            <span>创建文章</span>
          </el-menu-item>
          <el-menu-item :route="{name: 'page2'}" index="1-3">
            <i class="el-icon-watermelon"></i>
            <span>page2</span>
          </el-menu-item>
        </el-menu-item-group>
      </el-submenu>

      <el-submenu index="2">
        <template slot="title">
          <i class="el-icon-scissors"></i>
          <span>测试管理</span>
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
          <el-menu-item :route="{name: 'page1'}" index="2-3">
            <i class="el-icon-umbrella"></i>
            <span slot="title">keep-alive例子</span>
          </el-menu-item>
        </el-menu-item-group>
      </el-submenu>

      <el-submenu index="3">
        <template slot="title">
          <i class="el-icon-s-check"></i>
          <span>权限管理</span>
        </template>
        <el-menu-item-group>
          <el-menu-item :route="{name: 'user'}" index="3-1">
            <i class="el-icon-s-custom"></i>
            <span slot="title">用户列表</span>
          </el-menu-item>
          <el-menu-item :route="{name: 'role'}" index="3-2">
            <i class="el-icon-basketball"></i>
            <span slot="title">角色管理</span>
          </el-menu-item>
          <el-menu-item :route="{name: 'menu'}" index="3-3">
            <i class="el-icon-s-order"></i>
            <span slot="title">菜单管理</span>
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
