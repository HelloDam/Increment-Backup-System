<template>
  <div class="container">

    <el-menu
        :default-active="activeIndex"
        class="el-menu-demo"
        mode="horizontal"
        :ellipsis="false"
        @select="handleSelect"
    >
      <el-menu-item index="0">
        <img
            style="width: 100px"
            src="../assets/element-plus-logo.svg"
            alt="Element logo"
        />
      </el-menu-item>
      <div class="flex-grow"/>
      <el-menu-item index="1">
        <el-badge :value="12" class="item">
          备份任务
        </el-badge>
      </el-menu-item>
      <el-menu-item index="2">已备份文件</el-menu-item>
      <el-menu-item index="3">文件备份记录</el-menu-item>
      <!--      <el-sub-menu index="2">-->
      <!--        <template #title>Workspace</template>-->
      <!--        <el-menu-item index="2-1">item one</el-menu-item>-->
      <!--        <el-menu-item index="2-2">item two</el-menu-item>-->
      <!--        <el-menu-item index="2-3">item three</el-menu-item>-->
      <!--        <el-sub-menu index="2-4">-->
      <!--          <template #title>item four</template>-->
      <!--          <el-menu-item index="2-4-1">item one</el-menu-item>-->
      <!--          <el-menu-item index="2-4-2">item two</el-menu-item>-->
      <!--          <el-menu-item index="2-4-3">item three</el-menu-item>-->
      <!--        </el-sub-menu>-->
      <!--      </el-sub-menu>-->
    </el-menu>

    <!--    <div class="head">-->
    <!--      <div></div>-->
    <!--      <div style=" font-size: 2rem;font-weight: 500;">-->
    <!--        文 件 增 量 备 份 系 统-->
    <!--      </div>-->
    <!--      <div></div>-->
    <!--    </div>-->
    <div class="bodyDiv">

      <div class="tableDiv">
        <BackupSourceTable @changeSourceChange="changeSourceChange()" ref="backupSourceTable"></BackupSourceTable>
      </div>
      <div style="width: 10px"></div>
      <div class="tableDiv">
        <BackupTargetTable :selectBackupSource="selectBackupSource"></BackupTargetTable>
      </div>
    </div>

  </div>
</template>

<script>

import BackupSourceTable from "../components/BackupSourceTable.vue";
import BackupTargetTable from "../components/BackupTargetTable.vue";

export default {
  components: {BackupSourceTable, BackupTargetTable},
  data() {
    return {
      // 数据源
      selectBackupSource: {},
    };
  },
  computed: {},
  watch: {},
  methods: {
    changeSourceChange() {
      console.log("子组件选中数据源更新");
      this.selectBackupSource = this.$refs.backupSourceTable.selectBackupSource;
    },
  },
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

  .flex-grow {
    flex-grow: 1;
  }

  .item {
    margin-top: 0px;
    margin-right: 5px;
  }

  .head {
    text-align: center;
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
      width: calc(50% - 15px);
      height: 100%;

    }
  }
}
</style>

