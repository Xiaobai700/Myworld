import Vue from 'vue'
import Router from 'vue-router'
import HelloWorld from '@/components/HelloWorld'
import Login from '@/views/login/login'
import Index from '@/views/index/index'
import page1 from '@/views/other/page1'
import page2 from '@/views/other/page2'
import page3 from '@/views/other/page3'
import page4 from '@/views/other/page4'

Vue.use(Router)
export const constantRouterMap = [
  {
    path: '/',
    name: 'Login',
    component: Login
  },
  {
    path: '/index',
    name: 'Index',
    component: Index,
    redirect:'/hello',
    children:[
      {
        path:'/hello',
        name:'hello',
        component:HelloWorld
      }
    ]
  },
  {
    path: '/index',
    component: Index,
    redirect: '/page1',
    name: '文章',
    meta:{title:'文章'},
    children: [
      {
        path: '/page1',
        name: 'page1',
        component: page1,
        meta:{title:'文章列表'},
        menu:'page1'
      },
      {
        path: '/page2',
        name: 'page2',
        component: page2,
        meta:{title:'page2'},
        menu:'page2'
      }
    ]
  },
  {
    path: '/index',
    component: Index,
    redirect: '/page3',
    meta:{title:'测试'},
    name: '测试',
    children: [
      {
        path: '/page3',
        name: 'page3',
        component: page3,
        meta:{title:'transition例子'},
        menu:'page3'
      },
      {
        path: '/page4',
        name: 'page4',
        component: page4,
        meta:{title:'测试页面2'},
        menu:'page4'
      }
    ]
  }
]
export const asyncRouterMap = [
  {
    path: '/index',
    component: Index,
    redirect: '/index/page1',
    name: '文章',
    meta:{title:'文章'},
    children: [
      {
        path: '/page1',
        name: '文章列表',
        component: page1,
        meta:{title:'文章列表'},
        menu:'page1'
      },
      {
        path: '/page2',
        name: 'page2',
        component: page2,
        meta:{title:'page2'},
        menu:'page2'
      }
    ]
  },
  {
    path: '/index',
    component: Index,
    redirect: '/index/page3',
    name: '测试',
    children: [
      {
        path: '/page3',
        name: '测试页面1',
        component: page3,
        meta:{title:'测试页面1'},
        menu:'page3'
      },
      {
        path: '/page4',
        name: '测试页面2',
        component: page4,
        meta:{title:'测试页面2'},
        menu:'page4'
      }
    ]
  }
]

export default new Router({
  routes:constantRouterMap
})
/*
import Vue from 'vue'
import Router from 'vue-router'
// in development env not use Lazy Loading,because Lazy Loading too many pages will cause webpack hot update too slow.so only in production use Lazy Loading
/!* layout *!/
import Layout from '../views/layout/Layout'
import Index from '../views/index/index'
import Hello from '../components/HelloWorld'

const _import = require('./_import_' + process.env.NODE_ENV)
Vue.use(Router)
export const constantRouterMap = [
  {path: '/', component: _import('login/login'), hidden: true},
  {path: '/404', component: _import('404'), hidden: true},
  {
    path: '/index',
    component: Index,
    redirect: '/dashboard',
    name: '首页',
    hidden: true,
    children: [{
      path: '/dashboard',
      component: Hello,
      name:'首页',
      meta: {title: '首页'}
    }]
  }
]
export default new Router({
  // mode: 'history', //后端支持可开
  scrollBehavior: () => ({y: 0}),
  routes: constantRouterMap
})
export const asyncRouterMap = [
  {
    path: '/system',
    component: Layout,
    redirect: '/system/article',
    name: '功能模块',
    meta: {title: '功能模块', icon: 'tree'},
    children: [
      {
        path: 'article',
        name: '文章',
        component: _import('article/article'),
        meta: {title: '文章', icon: 'example'},
        menu: 'article'
      },
    ]
  },
  {
    path: '/user',
    component: Layout,
    redirect: '/user/',
    name: '',
    meta: {title: '用户权限', icon: 'table'},
    children: [
      {
        path: '', name: '用户列表', component: _import('user/user'), meta: {title: '用户列表', icon: 'user'}, menu: 'user'
      },
      {
        path: 'role',
        name: '权限管理',
        component: _import('user/role'),
        meta: {title: '权限管理', icon: 'password'},
        menu: 'role'
      },
    ]
  },
  {path: '*', redirect: '/404', hidden: true}
]
*/

