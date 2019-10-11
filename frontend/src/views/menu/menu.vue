<template>
  <div class="app-container">
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
      <el-table-column prop="menuCode" label="菜单编码" width="200" show-overflow-tooltip></el-table-column>
      <el-table-column prop="menuName" label="菜单名称" width="200"></el-table-column>
      <el-table-column prop="permissionCode" label="权限编码" width="300"></el-table-column>
      <el-table-column prop="permissionName" label="权限名称" show-overflow-tooltip></el-table-column>
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
</template>

<script>
    export default {
      name: "menu",
      data(){
        return{
          totalCount:0,
          list:[],
          listQuery: {
            pageNum:1,
            pageRow:10,
          },
          roles: [],
          multipleSelection:'',
          dialogStatus:'create',
          dialogFormVisible:false,
          textMap: {
            update:'编辑',
            create:'新建菜单'
          },
          tempMenu: {
            menuCode:'',
            menuName:'',
            permissionCode:'',
            permissionName:'',
            isRequires:false
          }
        }
      },
      created(){
        this.getList();
      },
      methods:{
        getList(){
          this.api({
            url:"menu/listMenu",
            method:"get",
            params:this.listQuery
          }).then(data =>{
            this.list = data.list;
            this.totalCount = data.totalCount;
          })
        },
        handleSizeChange(val) {
          //改变每页数量
          this.listQuery.pageRow = val;
          this.handleFilter();
        },
        handleCurrentChange(val) {
          //改变页码
          this.listQuery.pageNum = val;
          this.getList();
        },
        handleFilter() {
          //查询事件
          this.listQuery.pageNum = 1;
          this.getList()
        },
        getIndex($index) {
          //表格序号
          return (this.listQuery.pageNum - 1) * this.listQuery.pageRow + $index + 1
        },
        handleSelectionChange(val) {
          this.multipleSelection = val;
        }
      }
    }
</script>

<style scoped>

</style>
