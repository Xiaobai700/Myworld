<template>
  <!--多个组件的过渡，使用动态组件-->
  <!--过渡模式 in-out  out-in-->
  <!--
  in-out：新元素先进行过渡，完成之后当前元素过渡离开。
  out-in：当前元素先进行过渡，完成之后新元素过渡进入。
  -->
  <transition name="move" mode="out-in">
    <router-view class="main-container">
    </router-view>
  </transition>
</template>

<script>
export default {
  name: 'AppMain',
  computed: {
    // key() {
    //   return this.$route.name !== undefined ? this.$route.name + +new Date() : this.$route + +new Date()
    // }
  }
}
</script>
