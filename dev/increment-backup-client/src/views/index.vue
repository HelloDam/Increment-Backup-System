<template>
  <div class="container">

    <div class="head">
      文 件 增 量 备 份 系 统
    </div>
    <div class="bodyDiv">

      <div class="tableDiv">
        <BackupSourceTable></BackupSourceTable>
      </div>
      <div style="width: 10px"></div>
      <div class="tableDiv">
        <el-table :data="sourceList">
          <el-table-column prop="id" label="编号" width="180"/>
          <el-table-column prop="rootPath" label="根目录路径" width="180"/>
          <el-table-column prop="backupName" label="简要介绍" width="180"/>
          <el-table-column prop="createTime" label="创建时间"/>
          <el-table-column prop="updateTime" label="修改时间"/>
        </el-table>
      </div>
    </div>

  </div>
</template>

<script>

import BackupSourceTable from "../components/BackupSourceTable.vue";

export default {
  components: {BackupSourceTable},
  data() {
    return {};
  },
  computed: {},
  watch: {},
  methods: {}
  ,
  beforeCreate() {
  }
  ,

  created() {

  }
  ,

  beforeMount() {

  }
  ,

  mounted() {

  }
  ,

  beforeUpdate() {

  }
  ,

  updated() {

  }
  ,

  beforeDestroy() {

  }
  ,
  destroyed() {

  }
  ,

  activated() {

  }
  ,
}


</script>

<style scoped lang="scss">

.container {

  margin: 0;
  min-height: 100vh;
  background-color: #abc6f8;
  background-image: radial-gradient(closest-side, rgb(255, 255, 255), rgba(235, 105, 78, 0)), radial-gradient(closest-side, rgb(250, 203, 203), rgba(243, 11, 164, 0)), radial-gradient(closest-side, rgb(237, 252, 202), rgba(254, 234, 131, 0)), radial-gradient(closest-side, rgb(197, 248, 241), rgba(170, 142, 245, 0)), radial-gradient(closest-side, rgb(206, 200, 243), rgba(248, 192, 147, 0));
  background-size: 130vmax 130vmax, 80vmax 80vmax, 90vmax 90vmax, 110vmax 110vmax, 90vmax 90vmax;
  background-position: -80vmax -80vmax, 60vmax -30vmax, 10vmax 10vmax, -30vmax -10vmax, 50vmax 50vmax;
  background-repeat: no-repeat;
  /* 背景颜色4秒循环一次 */
  animation: 4s movement linear infinite;

  padding: 1rem;
  font-family: Arial, Helvetica, sans-serif, Times New Roman;

  .head {
    text-align: center;
    font-size: 2rem;
    font-weight: 500;
    background: rgba(255, 255, 255, 0.6);
    box-shadow: 0px 0px 10px 0px rgba(20, 20, 20, 0.116);
    border-radius: 5px;
    padding: 10px 0;
  }

  .bodyDiv {
    margin-top: 10px;
    display: flex;
    height: 80vh;

    .tableDiv {
      background: rgba(255, 255, 255, 0.6);
      box-shadow: 0px 0px 10px 0px rgba(20, 20, 20, 0.116);
      border-radius: 5px;
      padding: 5px;
      width: calc(50% - 10px);
      height: 100%;

    }
  }
}
</style>

