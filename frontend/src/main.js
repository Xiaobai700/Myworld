import Vue from 'vue'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import locale from 'element-ui/lib/locale/lang/zh-CN'
import App from './App'
import router from './router/index'
import store from './store'
import '@/permission' // 权限
import {default as api} from './utils/api'
import {hasPermission} from "./utils/hasPermission";
import mavonEditor from 'mavon-editor'
import 'mavon-editor/dist/css/index.css'
Vue.use(mavonEditor);
Vue.use(ElementUI, {locale});
Vue.prototype.api = api;
//全局的常量
Vue.prototype.hasPerm = hasPermission;
//生产环境时自动设置为 false 以阻止 vue 在启动时生成生产提示。
Vue.config.productionTip = (process.env.NODE_ENV != 'production');
Vue.config.keyCodes.enter = 112;
import axios from 'axios';
Vue.prototype.$axios = axios;
axios.defaults.baseURL = '/api'; //解决axios跨域问题

//自定义指令 把元素固定在页面上 并且显示在所有组件之上
Vue.directive('pin', {
  bind: function (el, binding, vnode) {
    el.style.position = 'fixed';
    el.style.top = binding.value + 'px';
    el.style.zIndex = 9999;
  }
});

new Vue({
  el: '#app',
  router,
  store,
  template: '<App/>',
  components: {App}
})
