import Vue from 'vue'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import locale from 'element-ui/lib/locale/lang/zh-CN'
import App from './App'
import router from './router'
import store from './store'
import '@/permission' // 权限
import {default as api} from './utils/api'
import {hasPermission} from "./utils/hasPermission";
Vue.use(ElementUI, {locale})
Vue.prototype.api = api;
//全局的常量
Vue.prototype.hasPerm = hasPermission;
//生产环境时自动设置为 false 以阻止 vue 在启动时生成生产提示。
Vue.config.productionTip = (process.env.NODE_ENV != 'production')
import axios from 'axios';
Vue.prototype.$axios = axios;
axios.defaults.baseURL = '/api'; //解决axios跨域问题
new Vue({
  el: '#app',
  router,
  store,
  template: '<App/>',
  components: {App}
})
