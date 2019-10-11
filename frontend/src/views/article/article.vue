<template>
  <div class="app-container">
    <!--<div class="table">-->
    <div style="float: left; margin-top: 5px;margin-left: 5px;">
      <el-button type="danger" icon="el-icon-delete" @click="deleteArticles">批量删除</el-button>
    </div>
    <el-table
      ref="multipleTable"
      :data="list"
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
        prop="content"
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
  export default {
    name: "page1",
    data() {
      return {
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
</style>
