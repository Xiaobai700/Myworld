<template>
  <transition name="move" mode="out-in">
    <router-view class="main-container"></router-view>
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
