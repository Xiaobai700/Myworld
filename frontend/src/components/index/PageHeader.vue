<template>
  <div>
    <div class="left">
      <img src="../../assets/header_logo.png" alt="">
    </div>
    <div class="right">
      <span class="nick-name">hello,<span @click="infoVisible=true" class="no-hello">{{username}}</span></span>
      <!--用户头像-->
      <!--消息-->
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
    <div>
      <el-dialog
        title="个人中心"
        :visible.sync="infoVisible"
        width="40%"
        :before-close="handleClose">
        <div style="width: 50%;margin-left: 200px;">
          <div class="input">
            <el-tag type="success">账号：</el-tag>
            <el-input
            prefix-icon="el-icon-user-solid"
            v-model="account"
            clearable>
            </el-input>
          </div>
          <!--查看自己的权限-->
          <div>
            <span>我的权限：</span>
            <template>
              <el-tag v-if="this.user.userId === 1" type="success">
                全部
              </el-tag>
              <div v-else>
                <div v-for="menu in menus">
                  <span style="width: 100px;display: inline-block;text-align: right">{{menu.menuName}}</span>
                  <el-tag v-for="perm in menu.permissions" :key="perm.permissionName" v-text="perm.permissionName"
                          style="margin-right: 3px;"
                          type="primary">
                  </el-tag>
                </div>
              </div>
            </template>
          </div>
        </div>
        <span slot="footer" class="dialog-footer">
        <el-button @click="infoVisible = false">取 消</el-button>
        <el-button type="primary" @click="infoVisible = false">确 定</el-button>
      </span>
      </el-dialog>
    </div>
  </div>
</template>

<script>
    export default {
      name: "Header",
      props:['username'],
      created(){
        this.getMyMenus();
      },
      data(){
        return{
          infoVisible:false,
          account:this.$store.state.user.username,
          menus:[],
          user: {
            userId:this.$store.state.user.userId
          },
        }
      },
      methods:{
        logout(){
          this.$store.dispatch('LogOut').then(() =>{
            //这里解决注销登录之后 不刷新页面 侧边导航栏无法正确显示菜单的问题
            //重新实例化vue-router对象 避免出现以上bug
            location.reload();
            //this.$router.push({path: '/'})
          })
        },
        handleClose(done) {
          this.$confirm('确认关闭？')
            .then(_ => {
              done();
            })
            .catch(_ => {});
          },
        getMyMenus(){
          let _this = this;
          _this.api({
            url: "/menu/getMyPermission",
            method: "post",
            data: this.user
          }).then(data => {
            _this.menus = data;
          }).catch((err)=>{
            console.log("err");
            console.log(err);
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
.nick-name{
  font-size: small;
  color: #d3dce6;
  margin-right: 10px;
}
.no-hello{
  font-style: italic;
  cursor: pointer;
}
.el-input {
  width: 70%;
}

</style>
