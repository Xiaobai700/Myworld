<template>
  <div class="app-container">
    <div class="filter-container">
      <el-form style="display: flex;">
        <el-form-item>
          <el-button type="primary" v-if="hasPerm('user:add')" @click="showCreate">添加</el-button>
        </el-form-item>
        <el-form-item>
          <el-button type="danger" v-if="hasPerm('user:delete')" @click="removeUsers">批量删除</el-button>
        </el-form-item>
      </el-form>
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
      <el-table-column prop="nickname" label="昵称" width="200" show-overflow-tooltip></el-table-column>
      <el-table-column prop="username" label="用户名" width="200"></el-table-column>
      <el-table-column label="角色" width="200">
        <template slot-scope="scope">
          <el-tag type="success" v-text="scope.row.roleName" v-if="scope.row.roleId===1"></el-tag>
          <el-tag type="primary" v-text="scope.row.roleName" v-else></el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="createTime" label="创建时间" width="300"></el-table-column>
      <el-table-column prop="updateTime" label="修改时间" show-overflow-tooltip></el-table-column>
      <el-table-column label="管理" v-if="hasPerm('user:update')">
        <template slot-scope="scope">
          <el-button type="primary" icon="edit" @click="showUpdate(scope.$index)">修改</el-button>
          <el-button type="danger" v-if="hasPerm('user:delete')&&scope.row.userId!==userId" @click="removeUser(scope.$index)">删除</el-button>
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
    <el-dialog :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible" align="center">
      <el-form class="small-space" :model="tempUser" label-position="left" label-width="80px"
               style='width: 300px; margin-left:50px;'>
        <el-form-item label="用户名" required v-if="dialogStatus=='create'">
          <el-input type="text" v-model="tempUser.username">
          </el-input>
        </el-form-item>
        <el-form-item label="密码" v-if="dialogStatus=='create'" required>
          <el-input type="password" v-model="tempUser.password">
          </el-input>
        </el-form-item>
        <el-form-item label="新密码" v-else>
          <el-input type="password" v-model="tempUser.password" placeholder="不填则表示不修改">
          </el-input>
        </el-form-item>
        <el-form-item label="角色" required>
          <el-select v-model="tempUser.roleId" placeholder="请选择">
            <el-option
              v-for="item in roles"
              :key="item.roleId"
              :label="item.roleName"
              :value="item.roleId">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="昵称" required>
          <el-input type="text" v-model="tempUser.nickname">
          </el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button v-if="dialogStatus=='create'" type="success" @click="createUser">创 建</el-button>
        <el-button type="primary" v-else @click="updateUser">修 改</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
  import {mapGetters} from 'vuex'

    export default {
      name: "user1",
      data() {
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
            create:'新建用户'
          },
          tempUser: {
            username:'',
            password:'',
            nickname:'',
            roleId:'',
            userId:''
          }
        }
      },
      created(){
        this.getList();
        if (this.hasPerm('user:add') || this.hasPerm('user:update')) {
          this.getAllRoles();
        }
      },
      computed: {
        ...mapGetters([
          'userId'
        ])
      },
      methods: {
        getAllRoles() {
          this.api({
            url: "/user/getAllRoles",
            method: "get"
          }).then(data => {
            this.roles = data.list;
          })
        },
        getList(){
          this.api({
            url:"user/list",
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
        },
        showCreate() {
          //显示新增对话框
          this.tempUser.username = "";
          this.tempUser.password = "";
          this.tempUser.nickname = "";
          this.tempUser.roleId = "";
          this.tempUser.userId = "";
          this.dialogStatus = "create";
          this.dialogFormVisible = true;
        },
        showUpdate($index) {
          let user = this.list[$index];
          this.tempUser.username = user.username;
          this.tempUser.nickname = user.nickname;
          this.tempUser.roleId = user.roleId;
          this.tempUser.userId = user.userId;
          this.tempUser.deleteStatus = '1';
          this.tempUser.password = '';
          this.dialogStatus = "update";
          this.dialogFormVisible = true;
        },
        createUser() {
          //添加新用户
          this.api({
            url: "/user/addUser",
            method: "post",
            data: this.tempUser
          }).then(() => {
            this.$message({
              type: 'success',
              message: "添加用户成功!"
            });
            this.getList();
            this.dialogFormVisible = false;

          })
        },
        updateUser() {
          //修改用户信息
          let _vue = this;
          this.api({
            url: "/user/updateUser",
            method: "post",
            data: this.tempUser
          }).then(() => {
            let msg = "修改成功";
            this.dialogFormVisible = false
            if (this.userId === this.tempUser.userId) {
              msg = '修改成功,部分信息重新登录后生效'
            }
            this.$message({
              message: msg,
              type: 'success',
              duration: 1 * 1000,
              onClose: () => {
                _vue.getList();
              }
            })
          })
        },
        removeUser($index) {
          let _vue = this;
          this.$confirm('确定删除此用户?', '提示', {
            confirmButtonText: '确定',
            showCancelButton: false,
            type: 'warning'
          }).then(() => {
            let user = _vue.list[$index];
            user.deleteStatus = '2';
            _vue.api({
              url: "/user/updateUser",
              method: "post",
              data: user
            }).then(() => {
              _vue.getList()
            }).catch(() => {
              _vue.$message.error("删除失败")
            })
          })
        },
        removeUsers(){
          let _this = this;
          let selection = _this.$refs.multipleTable.selection;
          let deleteArr = [];
          let selectedNum = selection.length;
          if(selectedNum < 1){
            this.$message.error('请至少选中一条数据进行删除！');
            return "";
          }
          for(let i = 0; i < selectedNum;i++){
            let id = selection[i].userId;
            deleteArr.push(id);
          }
          this.$confirm('确定删除选中的' + selectedNum + '条数据?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(function() {
            _this.api({
              url: "/user/deleteUsers",
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

        }
      }
    }
</script>

<style scoped>
  .filter-container{
    float: left;
    margin-left: 5px;
    margin-top: 5px;
  }
  .el-form-item{
    margin-left: 5px;
  }
</style>
