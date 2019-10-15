<template>
  <div class="app-container">
    <!--<div class="table">-->
    <div class="filter-container">
      <el-form>
        <el-form-item>
          <el-button type="danger" icon="el-icon-delete" @click="deleteArticles">批量删除</el-button>
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
    <el-table
      ref="multipleTable"
      :data="list"
      @cell-click="cellClick"
      tooltip-effect="dark"
      style="width: 100%"
      @selection-change="handleSelectionChange">
      <el-table-column
        type="selection"
        width="55">
      </el-table-column>
      <el-table-column align="center" label="序号" width="80">
        <template slot-scope="scope">
          <span v-text="getIndex(scope.$index)"> </span>
        </template>
      </el-table-column>
      <el-table-column
        prop="title"
        label="文章"
        width="320"
        show-overflow-tooltip>
      </el-table-column>
      <el-table-column
        label="日期">
        <template slot-scope="scope">{{scope.row.createTime}}</template>
      </el-table-column>
    </el-table>
    <el-pagination
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="listQuery.pageNum"
      :page-sizes="[10, 20, 30, 40]"
      :page-size='listQuery.pageRow'
      layout="total, sizes, prev, pager, next, jumper"
      :total=this.totalCount>
    </el-pagination>
  </div>
  <!-- </div>-->

</template>

<script>
  import axios from 'axios'
  export default {
    name: "page1",
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
        currentPage1: 1,
        currentPage2: 2,
        currentPage3: 3,
        currentPage4: 4,
        list: [],
        totalCount: 0,
        multipleSelection: [],
        listQuery: {
          pageNum: 1,//页码
          pageRow: 10,//每页条数
          name: ''
        }
      }
    },
    created(){
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
      uploadFile() {
        if(this.form.file){
          this.$refs.uploadExcel.submit();
          let formData = new FormData();
          formData.append('file', this.form.file)
          axios.post('/article/importExcel',
            formData,
            {"Content-Type": "multipart/form-data" }
          ).then(res => {
            if (res.data && res.data.code == '0') {
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
      toggleSelection(rows) {
        if (rows) {
          rows.forEach(row => {
            this.$refs.multipleTable.toggleRowSelection(row);
          });
        } else {
          this.$refs.multipleTable.clearSelection();
        }
      },
      getList() {
        this.api({
          url: "/article/listArticle",
          method: "get",
          params: this.listQuery
        }).then(data => {
          this.list = data.list;
          console.log("data.list");
          console.log(data.list);
          this.totalCount = data.totalCount;
        })
      },
      handleSelectionChange(val) {
        this.multipleSelection = val;
      },
      handleSizeChange(val) {
        this.listQuery.pageRow = val;
        this.handleFilter(val);
      },
      handleCurrentChange(val) {
        this.listQuery.pageNum = val;
        this.getList();
      },
      handleFilter(val) {
        //查询事件
        this.listQuery.pageNum = 1;
        this.listQuery.pageRow = val;
        this.getList()
      },
      deleteArticles() {
        let _this = this;
        let selection = _this.$refs.multipleTable.selection;
        let deleteArr = [];
        let selectedNum = selection.length;
        if(selectedNum < 1){
          this.$message.error('请至少选中一条数据进行删除！');
          return ""
        }
        for (let i = 0; i < selectedNum; i++) {
          let id = selection[i].id;
          deleteArr.push(id);
        }
        this.$confirm('确定删除选中的' + selectedNum + '条数据?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(function() {
          _this.api({
            url: "/article/deleteArticles",
            method: "post",
            data: deleteArr
          }).then(() => {
            _this.$message({
              type: 'success',
              message: "删除成功!"
            });
            _this.getList();
          }).catch(function () {
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
      getIndex($index) {
        //表格序号
        return (this.listQuery.pageNum - 1) * this.listQuery.pageRow + $index + 1
      },
      cellClick(row, column, cell, event){
        this.$router.push({path:'/newArticle',query:{row:row}})
      }
    }
  }
</script>

<style scoped>
  .table{
    padding: 2px;
    height: 85%;
    background-color: white;
  }
  .block{
    height: 14%;
    background-color: white;
    margin-top: 3px;
  }
  .filter-container{
    margin-top: 5px;
    margin-left: 5px;
    float: left;
  }
</style>
