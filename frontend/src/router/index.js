import Vue from 'vue'
import Router from 'vue-router'
import Index from '@/views/index/index'

const _import = require('./_import_' + process.env.NODE_ENV)
Vue.use(Router)
export const constantRouterMap = [
  {
    path: '/',
    component: _import('login/login'),
    hidden:true
  },
  {path: '/404', component: _import('404'), hidden: true},
  {
    path: '/index',
    component: Index,
    redirect: '/hello',
    name: 'index',
    hidden: true,
    children: [
      {
        path: '/hello',
        component:_import('index/HelloWorld')
      }]
  }
]
export default new Router({
  scrollBehavior: () => ({y: 0}),
  routes: constantRouterMap
})
export const asyncRouterMap = [
  {
    path: '/system',
    component: Index,
    redirect: '/system/article',
    meta:{title:'文章'},
    name:'文章管理',
    children: [
      {
        path: 'article',
        name: 'article',
        component: _import('article/article'),
        meta:{title:'文章列表'},
        menu:'article'
      },
      {
        path: 'newArticle',
        name: 'newArticle',
        component: _import('article/newArticle'),
        meta:{title:'创建文章'},
        menu:'newArticle'
      }
    ]
  },
  {
    path: '/test',
    component: Index,
    redirect: '/test/page3',
    meta:{title:'测试'},
    name:'测试模块',
    children: [
      {
        path: 'page3',
        name: 'page3',
        component: _import('other/page3'),
        meta:{title:'transition例子'},
        menu:'page3'
      },
      {
        path: 'page2',
        name: 'page2',
        component: _import('other/page2'),
        meta:{title:'page2'},
        menu:'page2'
      }
    ]
  },
  {
    path: '/user',
    component: Index,
    redirect: '/user/role',
    meta: {title: '用户权限'},
    name:'权限管理',
    children: [
      {
        path: 'role',
        name: '角色列表',
        component: _import('user/role1'),
        meta: {title: '角色管理'},
        menu: 'role'
      },
      {
        path: 'user',
        name: '用户列表',
        component: _import('user/user1'),
        meta: {title: '用户列表'},
        menu: 'user'
      },
      {
        path: 'menu',
        name: '菜单列表',
        component: _import('menu/menu'),
        meta: {title: '菜单列表'},
        menu: 'menu'
      }
    ]
  },
  {
    path: '/sys',
    component:Index,
    redirect:'/sys/message',
    meta:{title:'系统管理'},
    name:'系统管理',
    children: [
      {
        path: 'message',
        name: '消息列表',
        component:_import('message/message'),
        meta: {title:'消息列表'},
        menu:'message'
      },
      {
        path: 'log',
        name: '日志列表',
        component:_import('log/log'),
        meta: {title:'日志列表'},
        menu:'log'
      },
      {
        path: 'errCode',
        name: '错误码管理',
        component:_import('errorCode/errorCode'),
        meta: {title:'错误码管理'},
        menu:'errorCode'
      }
    ]
  },
  {path: '*', redirect: '/404', hidden: true}
]

