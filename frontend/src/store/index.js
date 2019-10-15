import Vue from 'vue'
import Vuex from 'vuex'
import createVuexAlong from 'vuex-along'
import app from './modules/app'
import user from './modules/user'
import permission from './modules/permission'
import article from './modules/article'
import getters from './getters'

Vue.use(Vuex)

const store = new Vuex.Store({
modules:{
  app,
  user,
  permission,
  myArticle:article
},
  plugins: [createVuexAlong({
    name:'article-vuex-along',
    local:{
      list: ["user"],
      isFilter: true
    },
    session:{
      list:["myArticle.myArticle"]
    }
  })],
  getters
});

export default store
