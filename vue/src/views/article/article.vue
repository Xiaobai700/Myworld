<template>
  <div class="app-container">
    <div class="filter-container">
      <el-form>
        <el-form-item>
          <el-button type="primary" icon="plus" @click="showCreate" v-if="hasPerm('article:add')">添加</el-button>
          <el-button type="success" plain @click="exportExcel">批量导出</el-button>
          <el-button type="primary" plain @click="dialogVisible = true">批量导入</el-button>
        </el-form-item>
      </el-form>
    </div>
    <div>
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
    </div>
    <el-table :data="list" v-loading.body="listLoading" element-loading-text="拼命加载中" border fit
              highlight-current-row>
      <el-table-column align="center" label="序号" width="80">
        <template slot-scope="scope">
          <span v-text="getIndex(scope.$index)"> </span>
        </template>
      </el-table-column>
      <el-table-column align="center" prop="content" label="文章" style="width: 60px;"></el-table-column>
      <el-table-column align="center" label="创建时间" width="170">
        <template slot-scope="scope">
          <span>{{scope.row.createTime}}</span>
        </template>
      </el-table-column>
      <el-table-column align="center" label="管理" width="200" v-if="hasPerm('article:update')">
        <template slot-scope="scope">
          <el-button type="primary" icon="edit" @click="showUpdate(scope.$index)">修改</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="listQuery.pageNum"
      :page-size="listQuery.pageRow"
      :total="totalCount"
      :page-sizes="[10, 20, 50, 100]"
      layout="total, sizes, prev, pager, next, jumper">
    </el-pagination>
    <el-dialog :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible">
      <el-form class="small-space" :model="tempArticle" label-position="left" label-width="60px"
               style='width: 300px; margin-left:50px;'>
        <el-form-item label="文章">
          <el-input type="text" v-model="tempArticle.content">
          </el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button v-if="dialogStatus=='create'" type="success" @click="createArticle">创 建</el-button>
        <el-button type="primary" v-else @click="updateArticle">修 改</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
  import axios from 'axios'
  export default {
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
        totalCount: 0, //分页组件--数据总条数
        list: [],//表格的数据
        listLoading: false,//数据加载等待动画
        listQuery: {
          pageNum: 1,//页码
          pageRow: 50,//每页条数
          name: ''
        },
        dialogStatus: 'create',
        dialogFormVisible: false,
        textMap: {
          update: '编辑',
          create: '创建文章'
        },
        tempArticle: {
          id: "",
          content: ""
        }
      }
    },
    created() {
      this.getList();
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
      uploadFile() {
        if(this.form.file){
          this.$refs.uploadExcel.submit()
          let formData = new FormData()
          formData.append('file', this.form.file)
          axios.post('/article/importExcel',
            formData,
            {"Content-Type": "multipart/form-data" }
          ).then(res => {
            if (res.data && res.data.code == '0') {
              console.log(res.data)
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
        var _this = this;
        axios({
          method: "post",
          url: "/article/exportExcel",
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
      getList() {
        //查询列表
        if (!this.hasPerm('article:list')) {
          return
        }
        this.listLoading = true;
        this.api({
          url: "/article/listArticle",
          method: "get",
          params: this.listQuery
        }).then(data => {
          this.listLoading = false;
          this.list = data.list;
          this.totalCount = data.totalCount;
        })
      },
      handleSizeChange(val) {
        //改变每页数量
        this.listQuery.pageRow = val
        this.handleFilter();
      },
      handleCurrentChange(val) {
        //改变页码
        this.listQuery.pageNum = val
        this.getList();
      },
      getIndex($index) {
        //表格序号
        return (this.listQuery.pageNum - 1) * this.listQuery.pageRow + $index + 1
      },
      showCreate() {
        //显示新增对话框
        this.tempArticle.content = "";
        this.dialogStatus = "create"
        this.dialogFormVisible = true
      },
      showUpdate($index) {
        //显示修改对话框
        this.tempArticle.id = this.list[$index].id;
        this.tempArticle.content = this.list[$index].content;
        this.dialogStatus = "update"
        this.dialogFormVisible = true
      },
      createArticle() {
        //保存新文章
        this.api({
          url: "/article/addArticle",
          method: "post",
          data: this.tempArticle
        }).then(() => {
          this.getList();
          this.dialogFormVisible = false
        })
      },
      updateArticle() {
        //修改文章
        this.api({
          url: "/article/updateArticle",
          method: "post",
          data: this.tempArticle
        }).then(() => {
          this.getList();
          this.dialogFormVisible = false
        })
      },
    }
  }
</script>
