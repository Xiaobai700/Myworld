import {asyncRouterMap, constantRouterMap} from '@/router/index'

/**
 * 判断用户是否拥有此菜单
 * */
function hasPermission(menus, route) {
  if (route.menu) {
    /*
    * 如果这个路由有menu属性,就需要判断用户是否拥有此menu权限
    */
    return menus.indexOf(route.menu) > -1;
  } else {
    return true
  }
}

/**
 * 递归过滤异步路由表，返回符合用户菜单权限的路由表
 * */
function filterAsyncRouter(asyncRouters,menus) {
  debugger
  const accessedRouters = asyncRouters.filter(route =>{
    if(hasPermission(menus,route)){
      if(route.children && route.children.length){
        route.children = filterAsyncRouter(route.children,menus);
        return (route.children && route.children.length);
      }
      return true;
    }
    return false;
  })
  return accessedRouters
}

const permission = {
  state: {
    routers:constantRouterMap,
    addRouters: []
  },
  mutations: {
    SET_ROUTERS: (state,routers) => {
      state.addRouters = routers;
      state.routers = constantRouterMap.concat(routers)
    }
  },
  actions: {
    GenerateRoutes({commit},userPermission){
      return new Promise(resolve => {
        const role = userPermission.roleName;
        const menus = userPermission.menuList;
        let accessRouters;
        if(role === '管理员'){
          accessRouters = asyncRouterMap;
        }else {
          accessRouters = filterAsyncRouter(asyncRouterMap,menus)
        }
        commit('SET_ROUTERS',accessRouters);
        resolve()
      })
    }
  }
};

export default permission
