import axios from 'axios'
import {Message, MessageBox} from 'element-ui'
import {getToken} from '@/utils/auth'
import store from '../store'
// 创建axios实例
const service = axios.create({
  baseURL: process.env.BASE_URL, // api的base_url
  timeout: 15000                  // 请求超时时间2
});
// request拦截器 在请求或响应被 then 或 catch 处理前拦截它们
service.interceptors.request.use(config => {
  //在发送请求之前做些什么
  return config
}, error => {
 //对请求错误做些什么
  console.log("请求错误出现错误....")
  console.error(error);// for debug
  Promise.reject(error)
});
// respone拦截器
service.interceptors.response.use(
  response => {
    const res = response.data;
    if (res.code == '1000') {
      return res;
    }
    if (res.code == '100') {
      return res.info;
    } else if (res.code == "20011") {
      Message({
        showClose: true,
        message: res.msg,
        type: 'error',
        duration: 500,
        onClose: () => {
          store.dispatch('FedLogOut').then(() => {
            location.reload()// 为了重新实例化vue-router对象 避免bug
          })
        }
      });
      return Promise.reject("未登录")
    } else {
      Message({
        message: res.msg,
        type: 'error',
        duration: 3 * 1000
      });
      return Promise.reject(res)
    }
  },
  error => {
    console.error('err' + error)// for debug
    Message({
      message: error.message,
      type: 'error',
      duration: 3 * 1000
    });
    return Promise.reject(error)
  }
);
export default service

