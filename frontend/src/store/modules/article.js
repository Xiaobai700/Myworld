const article={
  state:{
    myArticle:{
      id:'',
      title:'',
      content:'',
      authorId:1,
      type:2,
      bgmImg:''
    }
  },
  mutations:{
    SET_ARTICLE:(state,article)=>{
      state.myArticle.id = article.id;
      state.myArticle.title = article.title;
      state.myArticle.content = article.content;
      state.myArticle.authorId = article.authorId;
      state.myArticle.type = article.type;
      state.myArticle.bgmImg = article.bgmImg;
    },
    RESET_ARTICLE:(state)=>{
      state.myArticle.id = '';
      state.myArticle.title = '';
      state.myArticle.content = '';
      state.myArticle.authorId = '';
      state.myArticle.type = '';
      state.myArticle.bgmImg = '';
    }
  },
  actions:{
    setArticle({commit},row){
      commit('SET_ARTICLE',row);
    },
    resetArticle({commit}){
      commit('RESET_ARTICLE');
    }
  }
}

export default article
