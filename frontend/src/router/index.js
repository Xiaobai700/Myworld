import Vue from 'vue'
import Router from 'vue-router'
import HelloWorld from '@/components/HelloWorld'
import Login from '../views/login/login'
import Index from '../views/index/index'
import page1 from '../views/other/page1'
import page2 from '../views/other/page2'
import page3 from '../views/other/page3'
import page4 from '../views/other/page4'

Vue.use(Router)

export default new Router({
  routes: [
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
      children: [
        {
          path: '/hello',
          name: 'hello',
          component: HelloWorld
        },
        {
          path: '/page1',
          name: 'page1',
          component: page1
        },
        {
          path: '/page2',
          name: 'page2',
          component: page2
        },
        {
          path: '/page3',
          name: 'page3',
          component: page3
        },
        {
          path: '/page4',
          name: 'page4',
          component: page4
        }
      ]
    }
  ]
})
