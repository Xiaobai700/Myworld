import Vue from 'vue'
import Router from 'vue-router'
import HelloWorld from '@/components/HelloWorld'
import Login from '@/views/login/login'
import Index from '@/views/index/index'

const _import = require('./_import_' + process.env.NODE_ENV)

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
    redirect: '/article',
    name: '文章',
    meta:{title:'文章'},
    children: [
      {
        path: '/article',
        name: 'article',
        component: _import('article/article'),
        meta:{title:'文章列表'},
        menu:'article'
      },
      {
        path: '/newArticle',
        name: 'newArticle',
        component: _import('article/newArticle'),
        meta:{title:'创建文章'},
        menu:'newArticle'
      },
      {
        path: '/page2',
        name: 'page2',
        component: _import('other/page2'),
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
        component: _import('other/page3'),
        meta:{title:'transition例子'},
        menu:'page3'
      },
      {
        path: '/page4',
        name: 'page4',
        component: _import('other/page4'),
        meta:{title:'测试页面2'},
        menu:'page4'
      }
    ]
  },
  {
    path: '/index',
    component: Index,
    redirect: '/role',
    meta:{title:'用户权限'},
    name: '用户权限',
    children: [
      {
        path: '/role',
        name: 'role',
        component: _import('user/role1'),
        meta:{title:'角色管理'},
        menu:'role'
      },
      {
        path: '/user',
        name: 'user',
        component: _import('user/user1'),
        meta:{title:'用户列表'},
        menu:'user'
      },
      {
        path: '/menu',
        name: 'menu',
        component: _import('menu/menu'),
        meta:{title:'菜单列表'},
        menu:'menu'
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
        component: _import('other/page1'),
        meta:{title:'文章列表'},
        menu:'page1'
      },
      {
        path: '/page2',
        name: 'page2',
        component: _import('other/page2'),
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
        component: _import('other/page3'),
        meta:{title:'测试页面1'},
        menu:'page3'
      },
      {
        path: '/page4',
        name: '测试页面2',
        component: _import('other/page4'),
        meta:{title:'测试页面2'},
        menu:'page4'
      }
    ]
  }
]

export default new Router({
  routes:constantRouterMap
})
