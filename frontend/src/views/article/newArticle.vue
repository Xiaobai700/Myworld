<template>
  <div class="myArticle">
    <div class="myButton">
      <el-button v-if="this.isButton" type="primary" @click="articleSave">发布</el-button>
    </div>
    <div class="one">
     <!-- 上传封面图片-->
      <el-upload
        class="avatar-uploader"
        action="http://localhost:8081/article/uploadImg"
        :show-file-list="false"
        :on-success="handleAvatarSuccess"
        :before-upload="beforeAvatarUpload">
        <img v-if="imageUrl" :src="imageUrl" class="avatar">
        <i v-else class="el-icon-plus avatar-uploader-icon"></i>
      </el-upload>
    </div>
    <div class="two">
      <!--填写标题-->
      <el-input
        type="textarea"
        placeholder="请输入标题（最多50个字）"
        v-model="myArticle.title"
        maxlength="50"
        show-word-limit
      >
      </el-input>
    </div>
    <mavon-editor ref=md
                  :toolbars="markdownOption"
                  @fullScreen="fullScreen"
                  @imgAdd="$imgAdd"
                  v-model="myArticle.content"/>
  </div>
</template>

<script>
  import axios from 'axios';
    export default {
        name: "newArticle",
        data(){
          return{
            value:'',
            imageUrl: '',
            imageFile:'',
            isButton:true,
            myArticle:{
              title:'',
              content:'',
              authorId:1,
              type:2,
              bgmImg:''
            },
            markdownOption: {
              bold: true, // 粗体
              italic: true, // 斜体
              header: true, // 标题
              underline: true, // 下划线
              strikethrough: true, // 中划线
              mark: true, // 标记
              superscript: true, // 上角标
              subscript: true, // 下角标
              quote: true, // 引用
              ol: true, // 有序列表
              ul: true, // 无序列表
              link: true, // 链接
              imagelink: true, // 图片链接
              code: true, // code
              table: true, // 表格
              fullscreen: true, // 全屏编辑
              readmodel: true, // 沉浸式阅读
              htmlcode: true, // 展示html源码
              help: true, // 帮助
              /* 1.3.5 */
              undo: true, // 上一步
              redo: true, // 下一步
              trash: true, // 清空
              save: true, // 保存（触发events中的save事件）
              /* 1.4.2 */
              navigation: true, // 导航目录
              /* 2.1.8 */
              alignleft: true, // 左对齐
              aligncenter: true, // 居中
              alignright: true, // 右对齐
              /* 2.2.1 */
              subfield: true, // 单双栏模式
              preview: true, // 预览
            },
          }
        },
      methods:{
        $imgAdd(pos, $file){
          // 第一步.将图片上传到服务器.
          let formdata = new FormData();
          formdata.append('image', $file);
          axios({
            url: '/article/uploadImg',
            method: 'post',
            data: formdata,
            headers: { 'Content-Type': 'multipart/form-data' },
          }).then((url) => {
            // 第二步.将返回的url替换到文本原位置![...](0) -> ![...](url)
            let $vm = this.$refs.md;
            $vm.$img2Url(pos,"http://localhost:8081/images/"+url.data);
          })
        },
        articleSave(){
          this.api({
            url: "/article/addArticle",
            method: "post",
            data: this.myArticle
          }).then(() => {
            this.myArticle.content='',
            this.myArticle.title='',
            this.myArticle.bgmImg='',
            this.imageUrl=''
            this.$message({
              type: 'success',
              message: "创建文章成功!"
            });
          })
        },
        handleAvatarSuccess(res, file) {
          this.imageUrl = URL.createObjectURL(file.raw);
          this.imageFile = file.raw;
          let formdata = new FormData();
          formdata.append('image',file.raw);
          axios({
            url: '/article/uploadImg',
            method: 'post',
            data: formdata,
            headers: { 'Content-Type': 'multipart/form-data' },
          }).then((url) => {
            this.imageUrl="http://localhost:8081/images/"+url.data;
            this.myArticle.bgmImg=this.imageUrl;
          })

        },
        beforeAvatarUpload(file) {
          const isJPG = file.type === 'image/jpeg';
          const isLt2M = file.size / 1024 / 1024 < 2;

         // if (!isJPG) {
           // this.$message.error('上传头像图片只能是 JPG 格式!');
          //}
          //if (!isLt2M) {
            //this.$message.error('上传头像图片大小不能超过 2MB!');
          //}
          return isJPG && isLt2M;
        },
        fullScreen(){
          //在全局展示的时候把保存按钮隐藏
          this.isButton = !this.isButton;
        }
      }
    }
</script>

<style scoped>
  .myButton{
    /*按钮固定不动，显示在所有组件之上*/
    position: fixed;
    top: 100px;
    z-index: 9999;
  }
  .myArticle{
    width: 900px;
  }
  .one{
    width: 900px;
    height: 200px;
    background-color: #d3dce6;
    margin-top: 50px;
  }
  .two{
    width: 900px;
    margin-top: 20px;
    margin-bottom: 20px;
  }
  .avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
  }
  .avatar-uploader .el-upload:hover {
    border-color: #409EFF;
  }
  .avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 178px;
    height: 178px;
    line-height: 178px;
    text-align: center;
  }
  .avatar {
    width: 178px;
    height: 178px;
    display: block;
  }
</style>
