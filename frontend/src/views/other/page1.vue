<template>
  <div class="app-container">
    <!--<div class="table">-->
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
  import axios from 'axios';
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
                //查询列表
               // this.listLoading = true;
               /* axios.get('/article/listArticle',this.listQuery).then(data =>{
                    console.log(data.data.info.list);
                    this.list = data.data.info.list;
                    this.totalCount = data.data.info.totalCount;
                })*/
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
