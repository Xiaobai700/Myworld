<template>
  <el-form ref="form" :model="form" :rules="loginRules" label-width="80px" class="login-form">
    <el-form-item label="账号">
      <el-input v-model="form.username" placeholder="请输入内容"></el-input>
    </el-form-item>
    <el-form-item label="密码">
      <el-input placeholder="请输入密码" v-model="form.password" show-password></el-input>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" style="width: 100%" v-on:enter="handleLogin" @click.native.prevent="handleLogin">登录</el-button>
    </el-form-item>
  </el-form>
</template>

<script>
    export default {
        name: "login",
        data(){
            return{
                form: {
                    username:'admin',
                    password:'123456'
                },
                loginRules: {
                    username: [{required: true, trigger: 'blur', message: "请输入账号"}],
                    password: [{required: true, trigger: 'blur', message: "请输入密码"}]
                },
            }
        },
      created(){
        /*按下enter键进行登录*/
        /**
         * 注意：如果是对页面上的button添加键盘事件
         * 不能直接写在input和button上因为获取不到焦点
         * 要写在created下面
         * */
        let _this = this;
        document.onkeydown = function(e) {
          let key = window.event.keyCode;
          if (key === 13) {
            _this.handleLogin();
          }
        }
      },
        methods: {
          handleLogin() {
                this.$refs.form.validate(valid => {
                    if(valid){
                      //分发Action
                      this.$store.dispatch('Login', this.form).then(data => {
                        if ("success" === data.result) {
                          this.$store.dispatch('resetArticle');
                          this.$router.push({path: '/index'})
                        } else {
                          this.$message.error("账号/密码错误");
                        }
                      }).catch(() => {
                      })
                       /* axios.post('/login/auth',this.form).then(data =>{
                            if(data.data.code === '100'){
                                this.$router.push({path:'/index'})
                            }else{
                                const h = this.$createElement;
                                this.$notify({
                                    title: '消息',
                                    message: h('i', { style: 'color: teal'}, '登录失败！')
                                });
                            }
                        })*/
                    }else {
                      return false;
                    }
                })
            }
        }
    }
</script>
<style scoped>
  .login-form {
    position: absolute;
    left: 0;
    right: 0;
    width: 400px;
    padding: 35px 35px 15px 35px;
    margin: 120px auto;
  }
</style>
