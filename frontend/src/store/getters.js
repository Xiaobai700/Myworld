const getters = {
  sidebar: state => state.app.sidebar,
  visitedViews: state => state.app.visitedViews,

  nickname: state => state.user.nickname,
  userId: state => state.user.userId,
  avatar: state => state.user.avatar,
  role: state => state.user.role,
  menus: state => state.user.menus,
  permissions: state => state.user.permissions,

  id:state => state.myArticle.myArticle.id,
  title:state => state.myArticle.myArticle.title,
  content:state => state.myArticle.myArticle.content,
  authorId:state => state.myArticle.myArticle.authorId,
  type:state => state.myArticle.myArticle.type,
  bgmImg:state => state.myArticle.myArticle.bgmImg,

  permission_routers: state => state.permission.routes,
  addRouters: state => state.permission.addRoutes
}
export default getters
