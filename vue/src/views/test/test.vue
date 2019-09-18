<template>
  <div class="login-container">
    <el-form autoComplete="on" :model="loginForm" :rules="loginRules" ref="loginForm" label-position="left"
             label-width="0px"
             class="card-box login-form">
      <el-button type="success" plain @click="exportExcel">批量导出</el-button>
      <el-button type="primary" plain @click="dialogVisible = true">批量导入</el-button>
      <el-dialog
        title="提示"
        :visible.sync="dialogVisible"
        width="30%"
        :before-close="handleClose">
        <el-form :model="form">
          <el-form-item label="上传文件" :label-width="formLabelWidth">
            <el-upload
              ref="uploadExcel"
              action="https://jsonplaceholder.typicode.com/posts/"
              :limit=limitNum
              :auto-upload="false"
              accept=".xlsx"
              :before-upload="beforeUploadFile"
              :on-change="fileChange"
              :on-exceed="exceedFile"
              :on-success="handleSuccess"
              :on-error="handleError"
              :file-list="fileList">
              <el-button size="small" plain>选择文件</el-button>
              <div slot="tip" class="el-upload__tip">只能上传xlsx(Excel2007)文件，且不超过10M</div>
            </el-upload>
          </el-form-item>
          <!--<el-form-item>
              <el-button size="small" type="primary" @click="uploadFile">立即上传</el-button>
          </el-form-item>-->
        </el-form>
        <span slot="footer" class="dialog-footer">
                            <el-button @click="dialogVisible = false">取 消</el-button>
                            <el-button size="small" type="primary" @click="uploadFile">立即上传</el-button>
                         </span>
      </el-dialog>
    </el-form>
  </div>
</template>
<script>
  export default {
    name: 'login',
    data() {
      return {
        file:[],
        dialogVisible: false,
        /*上传相关*/
        limitNum: 1,
        formLabelWidth: '80px',
        form: {
          file: ''
        },
        fileList: [],
        loginForm: {
          username: 'admin',
          password: '123456'
        },
        loginRules: {
          username: [{required: true, trigger: 'blur', message: "请输入用户名"}],
          password: [{required: true, trigger: 'blur', message: "请输入密码"}]
        },
        loading: false
      }
    },
    methods: {
      exceedFile(files, fileList) {
        this.$notify.warning({
          title: '警告',
          message: `只能选择 ${this.limitNum} 个文件，当前共选择了 ${files.length + fileList.length} 个`
        });
      },
      // 文件状态改变时的钩子
      fileChange(file, fileList) {
        console.log('change')
        console.log(file)
        this.form.file = file.raw
        console.log(this.form.file)
        console.log(fileList)
      },
      // 上传文件之前的钩子, 参数为上传的文件,若返回 false 或者返回 Promise 且被 reject，则停止上传
      beforeUploadFile(file) {
        console.log('before upload')
        console.log(file)
        let extension = file.name.substring(file.name.lastIndexOf('.')+1)
        let size = file.size / 1024 / 1024
        if(extension !== 'xlsx') {
          this.$notify.warning({
            title: '警告',
            message: `只能上传Excel2017（即后缀是.xlsx）的文件`
          });
        }
        if(size > 10) {
          this.$notify.warning({
            title: '警告',
            message: `文件大小不得超过10M`
          });
        }
      },
      // 文件上传成功时的钩子
      handleSuccess(res, file, fileList) {
        this.form.file = '';
      },
      // 文件上传失败时的钩子
      handleError(err, file, fileList) {
        this.$notify.error({
          title: '错误',
          message: `文件上传失败`
        });
      },
      uploadFile() {
        if(this.form.file){
          this.$refs.uploadExcel.submit()
          let formData = new FormData()
          formData.append('file', this.form.file)
          this.$axios.post('excel/importExcel',
            formData,
            { "Content-Type": "multipart/form-data" }
          ).then(res => {
            if (res.data && res.data.code == '0') {
              this.$message({
                showClose: true,
                message: '数据导入成功！',
                type: 'success'
              });
            }else {
              this.$message({
                showClose: true,
                message: '数据导入失败！',
                type: 'error'
              });
            }
            this.dialogVisible = false;
          }).catch(err => {
            this.$message({
              showClose: true,
              message: '数据导入失败！',
              type: 'error'
            });
          })
        }else {
          this.$message({
            showClose: true,
            message: '请选择文件！',
            type: 'error'
          });
        }

      },
      exportExcel(){
        alert(88);
        var _this = this;
        this.$axios({
          method: "post",
          url:"excel/exportExcel",
          responseType: "blob"
        }).then(function(res) {
          const link = document.createElement("a");
          let blob = new Blob([res.data], { type: "multipary/form-data" });
          link.style.display = "none";
          link.href = URL.createObjectURL(blob);
          link.setAttribute("download", decodeURI(res.headers['filename']));
          document.body.appendChild(link);
          link.click();
          document.body.removeChild(link);
        }).catch(function(err) {
          console.log('-----export catch----')
          _this.$message({
            type: 'error',
            message: "导出Excel接口出错！"
          })
        })
      },
      handleClose(done) {
        this.$confirm('确认关闭？')
          .then(_ => {
            done();
            this.$refs.ruleForm.resetFields()
          })
          .catch(_ => {
          });
      },
      handleLogin() {
        this.$refs.loginForm.validate(valid => {
          if (valid) {
            this.loading = true
            this.$store.dispatch('Login', this.loginForm).then(data => {
              this.loading = false
              if ("success" === data.result) {
                this.$router.push({path: '/'})
              } else {
                this.$message.error("账号/密码错误");
              }
            }).catch(() => {
              this.loading = false
            })
          } else {
            return false
          }
        })
      }
    }
  }
</script>
