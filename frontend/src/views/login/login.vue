<template>
  <el-form ref="form" :model="form" :rules="loginRules" label-width="80px" class="login-form">
    <el-form-item label="账号">
      <el-input v-model="form.account" placeholder="请输入内容"></el-input>
    </el-form-item>
    <el-form-item label="密码">
      <el-input placeholder="请输入密码" v-model="form.password" show-password></el-input>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" style="width: 100%" @click="onSubmit">登录</el-button>
    </el-form-item>
  </el-form>
</template>

<script>
    export default {
        name: "login",
        data(){
            return{
                form: {
                    account:'',
                    password:''
                },
                loginRules: {
                    account: [{required: true, trigger: 'blur', message: "请输入账号"}],
                    password: [{required: true, trigger: 'blur', message: "请输入密码"}]
                },
            }
        },
        methods: {
            onSubmit() {
                this.$refs.form.validate(valid => {
                    if(valid){
                        this.$router.push({path:'/index'})
                    }else {
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
