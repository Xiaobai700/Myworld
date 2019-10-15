<template>
  <div>
    <h1>slot demo 我们可以将每个 todo 作为父级组件的插槽，以此通过父级组件对其进行控制，然后将 todo 作为一个插槽 prop 进行绑定：</h1>
    <ul>
      <li
        v-for="todo in filteredTodos"
        v-bind:key="todo.id"
      >
        <!--
        我们为每个todo 准备了一个插槽，
        将todo对象作为一个插槽的 prop 传入。
        -->
        <slot name="todo" v-bind:todo="todo">
          <!-- 后备内容 -->
          {{ todo.text }}
        </slot>
      </li>
    </ul>
  </div>
</template>

<script>
    export default {
        name: "page2",
      data(){
          return{
            filteredTodos:[
              {
                id:1,
                name:"张三0",
                text:"我是张三0"
              },
              {
                id:2,
                name:"张三1",
                text:"我是张三1"
              },
              {
                id:3,
                name:"张三2",
                text:"我是张三2"
              }
            ]
          }
      }
    }
</script>

<style scoped>

</style>
