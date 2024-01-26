<template>
  <div class="container">

    <el-menu
        :default-active="activeIndex"
        class="menuClass"
        mode="horizontal"
        :ellipsis="false"
        @select="handleSelectActive"
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
        <div
            style="border: #069ad2 solid 1px;height: 30px;border-radius: 5px;padding: 2px 5px;display: flex;align-items: center;justify-items: center">
          <div>备份中任务</div>
        </div>
        <span class="badge">{{ backupTaskList.length }}</span>
      </el-menu-item>
      <el-menu-item index="2">备份</el-menu-item>
      <el-menu-item index="3">备份任务管理</el-menu-item>
      <el-menu-item index="4">备份文件管理</el-menu-item>
      <el-menu-item index="5">备份记录管理</el-menu-item>
    </el-menu>

    <!--    <div class="head">-->
    <!--      <div></div>-->
    <!--      <div style=" font-size: 2rem;font-weight: 500;">-->
    <!--        文 件 增 量 备 份 系 统-->
    <!--      </div>-->
    <!--      <div></div>-->
    <!--    </div>-->
    <div class="bodyDiv" v-if="activeIndex==='2'">
      <div class="table1Div">
        <BackupSourceTable @changeSourceChange="changeSourceChange()"
                           ref="backupSourceTable"></BackupSourceTable>
      </div>
      <div style="width: 10px"></div>
      <div class="table1Div">
        <BackupTargetTable :selectBackupSource="selectBackupSource"></BackupTargetTable>
      </div>
    </div>

    <div v-if="activeIndex==='3'" class="bodyDiv">
      <div class="table2Div">
        <BackupTaskTable></BackupTaskTable>
      </div>
    </div>

    <div v-if="activeIndex==='4'" class="bodyDiv">
      <div class="table2Div">
        <BackupFileTable></BackupFileTable>
      </div>
    </div>

    <div v-if="activeIndex==='5'" class="bodyDiv">
      <div class="table2Div">
        <BackupFileHistoryTable></BackupFileHistoryTable>
      </div>
    </div>


    <el-dialog
        v-model="backupTaskListDialogVisible"
        title="任务列表"
        width="85%"
    >
      <el-table :data="backupTaskList">
        <el-table-column type="selection" width="55"/>
        <el-table-column prop="id" label="编号" width="100" :show-overflow-tooltip="true"/>
        <el-table-column prop="backupSourceRoot" label="备份数据源根目录" width="200"
                         :show-overflow-tooltip="true"/>
        <el-table-column prop="backupTargetRoot" label="备份目标根目录" width="200"
                         :show-overflow-tooltip="true"/>
        <el-table-column prop="backupProgress" label="备份进度" width="200" :show-overflow-tooltip="true">
          <template #default="scope">
            <!-- 进度条 -->
            <el-progress
                :percentage="scope.row.backupProgress"
                :stroke-width="15"
                :status="scope.row.backupStatus==2?'success':null"
                striped
                :striped-flow="scope.row.backupStatus==1?true:false"
                :duration="10"
            />
          </template>
        </el-table-column>
        <el-table-column prop="backupStatus" label="状态" width="100" :show-overflow-tooltip="true">
          <template #default="scope">
            <el-tag class="ml-2" v-if="scope.row.backupStatus==0">刚创建</el-tag>
            <el-tag class="ml-2" type="warning" v-if="scope.row.backupStatus==1">进行中</el-tag>
            <el-tag class="ml-2" type="success" v-if="scope.row.backupStatus==2">完 成</el-tag>
            <el-tag class="ml-2" type="danger" v-if="scope.row.backupStatus==3">失 败</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="totalFileNum" label="已备份文件数 / 总文件数" width="200"
                         :show-overflow-tooltip="true">
          <template #default="scope">
            <span>{{ scope.row.finishFileNum }} / {{ scope.row.totalFileNum }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="totalByteNum" label="已备份字节数 / 总字节数" width="300"
                         :show-overflow-tooltip="true">
          <template #default="scope">
            <span>{{ scope.row.finishByteNum }} / {{ scope.row.totalByteNum }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="createTime" label="创建时间" width="180" :show-overflow-tooltip="true"
                         :formatter="formatDate"/>
        <el-table-column prop="updateTime" label="修改时间" width="180" :show-overflow-tooltip="true"
                         :formatter="formatDate"/>
      </el-table>
      <template #footer>
          <span class="dialog-footer">
            <el-button @click="backupTaskListDialogVisible = false">关 闭</el-button>
          </span>
      </template>
    </el-dialog>

  </div>
</template>

<script>

import BackupSourceTable from "../components/BackupSourceTable.vue";
import BackupTargetTable from "../components/BackupTargetTable.vue";
import BackupTaskTable from "../components/BackupTaskTable.vue";
import BackupFileTable from "../components/BackupFileTable.vue";
import BackupFileHistoryTable from "../components/BackupFileHistoryTable.vue";
import backupTaskApi from "../api/backupTaskApi.js";

export default {
  components: {BackupSourceTable, BackupTargetTable, BackupTaskTable, BackupFileTable, BackupFileHistoryTable},
  data() {
    return {
      // 数据源
      selectBackupSource: {},
      // 备份任务列表
      backupTaskList: [],
      activeIndex: '2',
      backupTaskListDialogVisible: false,
    };
  },
  computed: {},
  watch: {},
  methods: {
    /**
     * 切换数据源
     */
    changeSourceChange() {
      console.log("子组件选中数据源更新");
      this.selectBackupSource = this.$refs.backupSourceTable.selectBackupSource;
    },
    /**
     * 查询正在处理的任务
     */
    listProcessingTask() {
      backupTaskApi.listProcessingTask().then(
          res => {
            // console.log("res:" + JSON.stringify(res));
            this.backupTaskList = res.data;
          }
      )
    },
    /**
     * 初始化websocket连接
     */
    initWebSocket() {
      let socket = {};
      let _this = this;
      if (typeof (WebSocket) == "undefined") {
        console.log("您的浏览器不支持WebSocket协议");
      } else {
        console.log("您的浏览器支持WebSocket协议");
        let socketUrl = "ws://localhost:8899/websocket/Admin";
        // 开启一个websocket服务
        socket = new WebSocket(socketUrl);
        //打开日志事件
        socket.onopen = function () {
          console.log("websocket已打开");
        };
        //浏览器端收消息，获得从服务端发送过来的文本消息
        socket.onmessage = function (msg) {
          // 对收到的json数据进行解析
          // let data = JSON.parse(JSON.stringify(msg));
          // console.log("收到备份进度消息：" + JSON.stringify(msg));
          // console.log(msg);
          let task = JSON.parse(msg.data);
          let isExit = false;
          for (let i = 0; i < _this.backupTaskList.length; i++) {
            if (_this.backupTaskList[i].id === task.id) {
              _this.backupTaskList[i] = task;
              isExit = true;
            }
          }
          if (isExit === false) {
            _this.backupTaskList.push(task);
          }
          console.log("收到备份任务进度消息：");
        };
        //关闭事件
        socket.onclose = function () {
          console.log("websocket已关闭");
        };
        //发生了错误事件
        socket.onerror = function () {
          console.log("websocket发生错误");
        }
      }
    },
    /**
     * 切换所激活主题
     */
    handleSelectActive(activeIndex) {
      console.log("handleSelectActive:" + JSON.stringify(activeIndex));
      if (activeIndex === '1') {
        console.log("查看任务列表");
        for (let i = 0; i < this.backupTaskList.length; i++) {
          console.log(JSON.stringify(this.backupTaskList[i]));
        }
        this.backupTaskListDialogVisible = true;
      } else {
        this.activeIndex = activeIndex;
      }
    },
    /**
     * 格式化日期和时间
     */
    formatDate(row, column, cellValue, index) {
      let date = new Date(cellValue);
      return date.toLocaleDateString() + " " + date.toLocaleTimeString();
    },
  },
  beforeCreate() {
  }
  ,

  created() {
    this.initWebSocket();
    this.listProcessingTask();
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

  .menuClass {
    border-radius: 5px;

    .badge {
      display: flex;
      align-items: center;
      justify-content: center;
      background-color: #f00;
      color: #fff;
      font-size: 12px;
      height: 15px;
      width: 15px;
      border-radius: 10%;
      position: absolute;
      top: 5px;
      right: 15px;
    }

    .flex-grow {
      flex-grow: 1;
    }
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

    .table1Div {
      background: rgba(255, 255, 255, 0.6);
      box-shadow: 0px 0px 10px 0px rgba(20, 20, 20, 0.116);
      border-radius: 5px;
      padding: 5px;
      width: calc(50% - 15px);
      height: 100%;
    }

    .table2Div {
      background: rgba(255, 255, 255, 0.6);
      box-shadow: 0px 0px 10px 0px rgba(20, 20, 20, 0.116);
      border-radius: 5px;
      padding: 5px;
      width: calc(100% - 10px);
      height: 100%;
    }
  }
}
</style>

