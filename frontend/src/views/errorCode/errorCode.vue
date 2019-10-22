<template>
  <div class="app-container">
    <div class="filter-container">
      <el-button type="primary" icon="el-icon-plus" @click="addNewRowFun">新增</el-button>
      <el-button type="primary" icon="el-icon-edit" @click="operEditRowFun">编辑</el-button>
      <el-button type="danger" icon="el-icon-delete" @click="deleteRowFun">删除</el-button>
      <el-button type="success" plain @click="exportExcel">批量导出</el-button>
      <el-button type="primary" plain @click="uploadDialogVisible = true">批量导入</el-button>
    </div>
    <div>
      <el-dialog :title="dialogAdd.title" :visible.sync="dialogAdd.dialogVisible" width="50%" :before-close="handleClose">
        <el-form :model="dialogAdd.ruleForm" :rules="dialogAdd.rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
          <input type="hidden" v-model="dialogAdd.ruleForm.id">
          <el-row :gutter="20">
            <el-col :span="12">
              <el-form-item label="错误码" prop="errorCode">
                <el-input v-model="dialogAdd.ruleForm.errorCode"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="错误信息" prop="errorMsg">
                <el-input v-model="dialogAdd.ruleForm.errorMsg"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="备注" prop="remark">
                <el-input v-model="dialogAdd.ruleForm.remark"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="所属模块" prop="moduleId">
                <el-select v-model="dialogAdd.ruleForm.moduleId">
                  <el-option key="ams" value="ams" label="ams"></el-option>
                  <el-option key="bms" value="bms" label="bms"></el-option>
                </el-select>
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>
        <span slot="footer" class="dialog-footer">
            <el-button type="primary" @click="submitForm('ruleForm')">确 定</el-button>
        </span>
      </el-dialog>
      <el-dialog
        title="提示"
        :visible.sync="uploadDialogVisible"
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
        </el-form>
        <span slot="footer" class="dialog-footer">
                            <el-button @click="dialogVisible = false">取 消</el-button>
                            <el-button size="small" type="primary" @click="uploadFile">立即上传</el-button>
                         </span>
      </el-dialog>
    </div>
    <el-table
      :data="listTableObj.tableData"
      current-row-key="id"
      row-key="id"
      style="width: 100%"
      height="90%"
      ref="multipleTable">
      <el-table-column type="selection" width="50">
      </el-table-column>
      <el-table-column prop="errorCode" label="错误码" width="150">
      </el-table-column>
      <el-table-column prop="errorMsg" label="错误信息" width="300">
      </el-table-column>
      <el-table-column prop="remark" label="备注" width="200">
      </el-table-column>
      <el-table-column prop="moduleId" label="所属模块" width="150">
      </el-table-column>
      <el-table-column prop="createTime" label="创建时间" width="150">
      </el-table-column>
      <el-table-column prop="updateTime" label="更新时间">
      </el-table-column>
    </el-table>
    <el-pagination
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="listPageObj.pageNum"
      :page-sizes="listPageObj.pageSizes"
      :page-size="listPageObj.pageRow"
      :layout="listPageObj.layout"
      :total="listPageObj.total">
    </el-pagination>
  </div>
</template>

<script>
  import axios from 'axios'

    export default {
      name: "errorCode",
      data() {
        return {
          /*上传相关*/
          uploadDialogVisible: false,
          limitNum: 1,
          formLabelWidth: '80px',
          form: {
            file: ''
          },
          fileList: [],
          dialogAdd: {
            title: '',
            dialogVisible: false,
            ruleForm: {
              dictId:'',
              errorCode: '',
              errorMsg: '',
              remark:'',
              moduleId: '',
            },
            rules: {
              errorCode: [
                { required: true, message: '请输入错误码', trigger: 'blur' },
              ],
              errorMsg: [
                { required: true, message: '请输入错误信息', trigger: 'blur' },
              ],
              remark: [
                { required: true, message: '请输入备注', trigger: 'blur' }
              ],
              moduleId: [
                { required: true, message: '请选择模块', trigger: 'change' }
              ]
            }
          },
          // table表格
          listTableObj: {
            multipleSelection: {},
            tableData: []
          },
          file:[],
          dialogVisible: false,
          // footer翻页
          listPageObj: {
            pageNum: 1,
            pageSizes: [1, 10, 20, 50, 100],
            pageRow: 10,
            layout: "total, sizes, prev, pager, next, jumper",
            total: 1
          },
          listQuery: {
            pageNum: 1,//页码
            pageRow: 10,//每页条数
            name: ''
          }
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
          this.form.file = file.raw;
        },
        // 上传文件之前的钩子, 参数为上传的文件,若返回 false 或者返回 Promise 且被 reject，则停止上传
        beforeUploadFile(file) {
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
        /**
         * [handleClose 新增弹窗关闭时 确认]
         */
        handleClose(done) {
          this.$confirm('确认关闭？')
            .then(_ => {
              done();
              this.$refs.ruleForm.resetFields()
            })
            .catch(_ => {
            });
        },
        /**
         * 弹窗 新增
         * 将表单清空
         */
        addNewRowFun() {
          this.dialogAdd.title = '新增';
          this.dialogAdd.ruleForm.id = ''
          this.dialogAdd.ruleForm.errorCode = ''
          this.dialogAdd.ruleForm.errorMsg = ''
          this.dialogAdd.ruleForm.moduleId = ''
          this.dialogAdd.ruleForm.remark = ''
          this.dialogAdd.dialogVisible = true;
        },
        /**
         * 编辑按钮触发
         */
        operEditRowFun() {

          let selection = this.$refs.multipleTable.selection
          if (selection && selection.length == 1) {
            this.dialogAdd.dialogVisible = true;
            this.dialogAdd.title = '编辑';
            this.dialogAdd.ruleForm.id = selection[0].id
            this.dialogAdd.ruleForm.errorCode = selection[0].errorCode
            this.dialogAdd.ruleForm.errorMsg = selection[0].errorMsg
            this.dialogAdd.ruleForm.remark = selection[0].remark
            this.dialogAdd.ruleForm.moduleId = selection[0].moduleId
          } else {
            this.$message.error('请选中一条数据进行编辑，您选中了' + selection.length + '条！');
          }
        },
        /**
         * [handleSizeChange 分页条数改变时执行]
         */
        handleSizeChange(val) {
          this.listQuery.pageRow = val;
          this.getTableListFun();
        },
        /**
         * [handleCurrentChange 当前页改变时执行]
         */
        handleCurrentChange(val) {
          this.listQuery.pageNum = val;
          this.getTableListFun();
        },
        /**
         * [getTableListFun 获取表格数据]
         */
        getTableListFun() {
          let _this = this;
          // 获取查询数据键值对
          _this.api({
            url: "/errorCode/listErrorCode",
            method: "get",
            params: _this.listQuery
          }).then((data) => {
            _this.listTableObj.tableData = data.list
            _this.listPageObj.total = data.totalCount

          }).catch((err) => {
          })
        },
        /**
         * [handleClose 新增弹窗关闭时 确认]
         */
        handleClose(done) {
          this.$confirm('确认关闭？')
            .then(_ => {
              done();
              this.$refs.ruleForm.resetFields()
            })
            .catch(_ => {
            });
        },
        /**
         * [submitForm 新增|编辑弹窗 表单提交]
         */
        submitForm(formName) {
          let _this = this;
          this.$refs[formName].validate((valid) => {
            let id = this.dialogAdd.ruleForm.id;
            // 前端验证通过
            if (valid) {
              let url = null;
              let _formData = _this.dialogAdd.ruleForm;
              let _data = {};
              if (id) {
                url = "/errorCode/update";
                _data.id = id
              } else {
                url = "/errorCode/save"
              }
              _data.errorCode = _formData.errorCode;
              _data.errorMsg = _formData.errorMsg;
              _data.remark = _formData.remark;
              _data.moduleId = _formData.moduleId;
              axios.post(url, _data).then(function(res) {
                  _this.$message({
                    showClose: true,
                    message: id ? '编辑成功!' : '创建成功!',
                    type: 'success'
                  });
                  _this.getTableListFun();
                  _this.dialogAdd.dialogVisible = false;
              }).catch(err => {
//
              })
            } else {
              this.$message({
                showClose: true,
                message: id ? '编辑失败!' : '创建失败!',
                type: 'error'
              });
              return false;
            }
          });
        },
        deleteRowFun() {
          console.log('-----deleteRowFun----')
          let _this = this;
          let selection = this.$refs.multipleTable.selection;
          let deleteArr = [];
          let selectedNum = selection.length;
          if (selectedNum < 1) {
            this.$message.error('请至少选中一条数据进行删除！');
            return "";
          }
          for (let i = 0; i < selectedNum; i++) {
            let id = selection[i].id;
            deleteArr.push(id);
          }
          _this.$confirm('确定删除选中的' + selectedNum + '条数据?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(function() {
            _this.api({
              url: "/errorCode/delete",
              method: "post",
              data: deleteArr
            }).then((data) => {
              _this.$message({
                type: 'success',
                message: "删除成功"
              });
              _this.getTableListFun()
              _this.$refs.multipleTable.clearSelection()
            }).catch((err) => {
              _this.$message({
                type: 'error',
                message: "删除接口出错！"
              })
            });
          }).catch(function() {
            _this.$message({
              type: 'info',
              message: '已取消删除'
            });
          });
        },
        uploadFile() {
          if(this.form.file){
            this.$refs.uploadExcel.submit();
            let formData = new FormData();
            formData.append('file', this.form.file)
            axios.post('/errorCode/importExcel',
              formData,
              {"Content-Type": "multipart/form-data" }
            ).then(res => {
              if (res.data && res.data.code === '0') {
                this.getList();
                this.$message({
                  showClose: true,
                  message: '数据导入成功！',
                  type: 'success'
                });
              }else {
                this.$message({
                  showClose: true,
                  message: res.data.msg,
                  type: 'warning'
                });
              }
              this.dialogVisible = false;
            }).catch(err => {
              this.$message({
                showClose: true,
                message: "批量导入失败！",
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
          let _this = this;
          axios({
            method: "post",
            url: "/errorCode/exportExcel",
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
        reload: function (event) {
          this.getTableListFun()
        }
      },
      created: function () {
        this.getTableListFun()
      }

    }
</script>

<style scoped>
  .filter-container{
    margin-top: 5px;
    margin-left: 5px;
    float: left;
  }
</style>
