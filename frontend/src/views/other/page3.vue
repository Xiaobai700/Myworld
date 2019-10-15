<template xmlns:v-slot="http://www.w3.org/1999/XSL/Transform">
  <div id="sudoku-demo" class="demo">
    <h1>Lazy Sudoku</h1>
    <p>Keep hitting the shuffle button until you win.</p>
    <page2 v-bind:todos="todos">
      <template v-slot:todo="{ todo }">
        <span v-if="todo.isComplete">✓</span>
        {{ todo.text }}
      </template>
    </page2>
    <button @click="shuffle">
      Shuffle
    </button>
    <transition-group name="cell" tag="div" class="container">
      <div v-for="cell in cells" :key="cell.id" class="cell">
        {{ cell.number }}
      </div>
    </transition-group>
  </div>
</template>

<script>
  /*nide.js安装lodash*/
  import page2 from "./page2";

  var _ = require('lodash');
    export default {
      name: "page3",
      components: {page2},
      comments:{
        page2
      },
      data() {
        return {
          cells: Array.apply(null, { length: 81 })
            .map(function (_, index) {
              return {
                id: index,
                number: index % 9 + 1
              }
            }),
          todos:[
            {
              isComplete:true,
              text:'测试数据1'
            },
            {
              isComplete:true,
              text:'测试数据2'
            }
          ]
        }
      },
      methods: {
        shuffle: function () {
          this.cells = _.shuffle(this.cells)
        }
      }
    }
</script>

<style scoped>
  .container {
    display: flex;
    flex-wrap: wrap;
    width: 238px;
    margin-top: 10px;
  }
  .cell {
    display: flex;
    justify-content: space-around;
    align-items: center;
    width: 25px;
    height: 25px;
    border: 1px solid #aaa;
    margin-right: -1px;
    margin-bottom: -1px;
  }
  .cell:nth-child(3n) {
    margin-right: 0;
  }
  .cell:nth-child(27n) {
    margin-bottom: 0;
  }
  .cell-move {
    transition: transform 1s;
  }

</style>
