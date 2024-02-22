<template>
  <div class="container">

    <div style="padding: 10px">
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
              src="../assets/EasyBackup.png"
              alt="Element logo"
          />
          <span style="color:#0489bb;font-weight: bold">备份系统</span>
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
        <el-menu-item index="6">
          <el-icon>
            <Setting/>
          </el-icon>
        </el-menu-item>

      </el-menu>

      <div class="bodyDiv" v-if="activeIndex==='2'">
        <div class="table1Div">
          <BackupSourceTable @changeSourceChange="changeSourceChange()"
                             @fileMessageSourceChange="fileMessageSourceChange()"
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

      <div v-if="activeIndex==='10'" class="bodyDiv">
        <div class="table2Div">
          <FileMessageTree :fileMessageSourceId="fileMessageSourceId"></FileMessageTree>
        </div>
      </div>

      <!-- 执行中任务列表-->
      <el-dialog
          v-model="backupTaskListDialogVisible"
          title="任务列表"
          width="85%"
      >
        <el-table :data="backupTaskList" border>
          <!--          <el-table-column type="selection" width="55"/>-->
          <!--                    <el-table-column prop="id" label="编号" width="100" :show-overflow-tooltip="true"/>-->
          <el-table-column prop="backupSourceRoot" label="备份信息" width="1200" align="center"
                           :show-overflow-tooltip="true">
            <template #default="scope">
              <div style="display: flex">

                <div>
                  <div style="color: #868585;font-size: 15px;display: flex;align-items: center">
                    <div
                        style="display:flex;justify-content: center;align-items: center;color:#888888;border:#c7c7c7 1px solid;border-radius: 2px;padding: 3px 3px;height: 20px">
                      id
                    </div>
                    <span style="margin-left: 5px">
                        {{ scope.row.id }}
                    </span>
                  </div>
                  <div class="gapDiv"></div>
                  <div style="color: #868585;font-size: 15px;display: flex;align-items: center">
                    <div
                        style="display:flex;justify-content: center;align-items: center;color:#888888;border:#c7c7c7 1px solid;border-radius: 2px;padding: 3px 3px;height: 20px">
                      状态
                    </div>
                    <div style="margin-left: 5px">
                      <span v-if="scope.row.backupStatus===0" style="color: #8a4f03">
                        刚创建
                      </span>
                      <span v-if="scope.row.backupStatus===1" style="color: #0d7df8">
                        进行中
                      </span>
                      <span v-if="scope.row.backupStatus===2" style="color: #069a1e">
                        完 成
                      </span>
                      <span v-if="scope.row.backupStatus===3" style="color: #fa070f">
                        失 败
                      </span>
                      <span v-if="scope.row.backupStatus===4" style="color: #f54248">
                        暂 停
                      </span>
                    </div>
                  </div>
                </div>

                <div style="text-align: center;margin-left: 10px">
                  <div style="font-size: 15px;display: flex;align-items: center">
                    <div
                        style="display:flex;justify-content: center;align-items: center;color:#4783e5;border:#4783e5 1px solid;border-radius: 2px;padding: 3px 3px;height: 20px">
                      源
                    </div>
                    <el-tooltip :content=scope.row.backupSourceRoot
                    >
                      <div style="color: #868585;display: flex;align-items: center;margin-left: 5px">
                        {{ truncateString(scope.row.backupSourceRoot, 30) }}
                        <Link style="width: 1.3em; height: 1.3em;color:#4783e5;margin-left: 5px"
                              @click="copyFilePath(scope.row.backupSourceRoot)"/>
                      </div>
                    </el-tooltip>
                  </div>
                  <div class="gapDiv"></div>
                  <div style="font-size: 15px;display: flex;align-items: center">
                    <div
                        style="display:flex;justify-content: center;align-items: center;color:#fa070f;border:#fa070f 1px solid;border-radius: 2px;padding: 3px 3px;height: 20px">
                      终
                    </div>
                    <el-tooltip :content=scope.row.backupTargetRoot
                    >
                      <div style="color: #868585;display: flex;align-items: center;margin-left: 5px">
                        {{ truncateString(scope.row.backupTargetRoot, 30) }}
                        <Link style="width: 1.3em; height: 1.3em;color:#4783e5;margin-left: 5px"
                              @click="copyFilePath(scope.row.backupTargetRoot)"/>
                      </div>
                    </el-tooltip>
                  </div>
                </div>

                <div style="align-items:center;margin-left: 10px">
                  <div style="font-size: 15px;display: flex;align-items: center">
                    <div style="color: #868585;font-size: 15px;display: flex;align-items: center">
                      <div
                          style="display:flex;justify-content: center;align-items: center;color:#888888;border:#c7c7c7 1px solid;border-radius: 2px;padding: 3px 3px;height: 20px">
                        备份时间
                      </div>
                      <span style="margin-left: 5px">
                        {{ formatTime(scope.row.backupTime) }}
                      </span>
                    </div>
                  </div>
                  <div class="gapDiv"></div>
                  <div style="font-size: 15px;display: flex;align-items: center">
                    <div style="color: #868585;font-size: 15px;display: flex;align-items: center">
                      <div
                          style="display:flex;justify-content: center;align-items: center;color:#888888;border:#c7c7c7 1px solid;border-radius: 2px;padding: 3px 3px;height: 20px">
                        创建时间
                      </div>
                      <span style="margin-left: 5px">
                        {{ scope.row.createTime }}
                      </span>
                    </div>
                  </div>

                </div>

                <div style="align-items:center;margin-left: 10px">
                  <div style="font-size: 15px;display: flex;align-items: center">
                    <div style="color: #868585;font-size: 15px;display: flex;align-items: center">
                      <div
                          style="display:flex;justify-content: center;align-items: center;color:#888888;border:#c7c7c7 1px solid;border-radius: 2px;padding: 3px 3px;height: 20px">
                        已备份数
                      </div>
                      <span style="margin-left: 5px">
                        {{ scope.row.finishFileNum }}
                      </span>
                    </div>
                  </div>
                  <div class="gapDiv"></div>
                  <div style="color: #868585;font-size: 15px;display: flex;align-items: center">
                    <div
                        style="display:flex;justify-content: center;align-items: center;color:#888888;border:#c7c7c7 1px solid;border-radius: 2px;padding: 3px 3px;height: 20px">
                      总文件数
                    </div>
                    <span style="margin-left: 5px">
                        {{ scope.row.totalFileNum }}
                    </span>
                  </div>
                </div>

                <div style="align-items:center;margin-left: 10px">
                  <div style="font-size: 15px;display: flex;align-items: center">
                    <div style="color: #868585;font-size: 15px;display: flex;align-items: center">
                      <div
                          style="display:flex;justify-content: center;align-items: center;color:#888888;border:#c7c7c7 1px solid;border-radius: 2px;padding: 3px 3px;height: 20px">
                        已备份大小
                      </div>
                      <span style="margin-left: 5px">
                        {{ formatBytes(scope.row.finishByteNum) }}
                      </span>
                    </div>
                  </div>
                  <div class="gapDiv"></div>
                  <div style="color: #868585;font-size: 15px;display: flex;align-items: center">
                    <div
                        style="display:flex;justify-content: center;align-items: center;color:#888888;border:#c7c7c7 1px solid;border-radius: 2px;padding: 3px 3px;height: 20px">
                      总文件大小
                    </div>
                    <span style="margin-left: 5px">
                        {{ formatBytes(scope.row.totalByteNum) }}
                    </span>
                  </div>
                </div>

              </div>
            </template>
          </el-table-column>
          <el-table-column prop="backupSourceRoot" label="备份进度" width="250" align="center" fixed="right">
            <template #default="scope">
              <div style="display:flex;flex-direction: column;justify-content:center;margin-left: 10px">
                <div style="font-size: 15px;display: flex;align-items: center">
                  <div style="color: #868585;font-size: 15px;display: flex;align-items: center">
                    <div
                        style="display:flex;justify-content: center;align-items: center;color:#888888;border:#c7c7c7 1px solid;border-radius: 2px;padding: 3px 3px;height: 20px">
                      数量进度
                    </div>
                    <div style="width: 140px;margin-left: 5px">
                      <!-- 进度条 -->
                      <el-progress
                          :percentage="parseFloat(scope.row.backupNumProgress)"
                          :stroke-width="10"
                          :status="scope.row.backupStatus==2?'success':null"
                          striped
                          :striped-flow="scope.row.backupStatus==1?true:false"
                          :duration="10"
                      />
                    </div>

                  </div>
                </div>
                <div style="height: 8px"></div>
                <div style="font-size: 15px;display: flex;align-items: center">
                  <div style="color: #868585;font-size: 15px;display: flex;align-items: center">
                    <div
                        style="display:flex;justify-content: center;align-items: center;color:#888888;border:#c7c7c7 1px solid;border-radius: 2px;padding: 3px 3px;height: 20px">
                      大小进度
                    </div>
                    <div style="width: 140px;margin-left: 5px">
                      <!-- 进度条 -->
                      <el-progress
                          :percentage="parseFloat(scope.row.backupSizeProgress)"
                          :stroke-width="10"
                          :status="scope.row.backupStatus==2?'success':null"
                          striped
                          :striped-flow="scope.row.backupStatus==1?true:false"
                          :duration="10"
                      />
                    </div>

                  </div>
                </div>
              </div>
            </template>
          </el-table-column>
          <el-table-column prop="backupSourceRoot" label="操作" width="80" align="center" fixed="right">
            <template #default="scope">
              <el-button type="danger" plain size="small" v-if="scope.row.backupStatus===1"
                         @click="stopTaskById(scope.row.id)">
                暂 停
              </el-button>
              <el-button type="danger" plain size="small" v-else disabled @click="stopTaskById(scope.row.id)">
                暂 停
              </el-button>
            </template>
          </el-table-column>
        </el-table>
        <template #footer>
          <span class="dialog-footer">
            <el-button @click="backupTaskListDialogVisible = false">关 闭</el-button>
          </span>
        </template>
      </el-dialog>
      <!-- 系统参数设置 -->
      <el-dialog
          v-model="sysParamDialogVisible"
          title=""
          width="85%"
      >
        <SysParam @closeSysParamDialog="this.sysParamDialogVisible=false"></SysParam>
      </el-dialog>
    </div>
    <AppFooter style="margin-top: 10px"></AppFooter>
  </div>
</template>

<script>

import BackupSourceTable from "../components/BackupSourceTable.vue";
import BackupTargetTable from "../components/BackupTargetTable.vue";
import BackupTaskTable from "../components/BackupTaskTable.vue";
import BackupFileTable from "../components/BackupFileTable.vue";
import BackupFileHistoryTable from "../components/BackupFileHistoryTable.vue";
import backupTaskApi from "../api/backupTaskApi.js";
import AppFooter from "../components/AppFooter.vue";
import mouseEffectsUtil from "../utils/mouseEffectsUtil.js";
import FileMessageTree from "../components/FileMessageTree.vue";
import {ElMessage, ElNotification} from "element-plus";
import SysParam from "../components/SysParam.vue";
import constant from "../constant.js";
import timeDisplayUtil from "../utils/timeDisplayUtil.js";
import storageUtil from "../utils/storageUtil.js";

export default {
  components: {
    FileMessageTree,
    BackupSourceTable,
    BackupTargetTable,
    BackupTaskTable,
    BackupFileTable,
    BackupFileHistoryTable,
    AppFooter,
    SysParam
  },
  data() {
    return {
      // 数据源
      selectBackupSource: {},
      fileMessageSourceId: '',
      // 备份任务列表
      backupTaskList: [],
      backupTaskListDialogVisible: false,
      // 系统参数
      sysParamDialogVisible: false,
      // 页面切换
      activeIndex: '2',
      // websocket
      socket: undefined,
      lockReconnect: false,
      heartbeatTime: 1000,
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
     * 查看指定数据源的文件结构
     */
    fileMessageSourceChange() {
      console.log("fileMessageSourceChange");
      this.fileMessageSourceId = this.$refs.backupSourceTable.fileMessageSourceId;
      this.activeIndex = '10';
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
        let socketUrl = "ws://" + constant.DOMAIN + ":8899/websocket/Admin";
        // 开启一个websocket服务
        _this.socket = new WebSocket(socketUrl);
        //打开日志事件
        _this.socket.onopen = function () {
          console.log("websocket已打开");
        };
        //浏览器端收消息，获得从服务端发送过来的文本消息
        _this.socket.onmessage = function (msg) {
          // 对收到的json数据进行解析
          // let data = JSON.parse(JSON.stringify(msg));
          let dataMap = JSON.parse(msg.data);
          console.log("收到备份进度消息：" + JSON.stringify(dataMap));
          if (dataMap.backupTask) {
            let task = dataMap.backupTask;
            let isExit = false;
            for (let i = 0; i < _this.backupTaskList.length; i++) {
              if (_this.backupTaskList[i].id === task.id) {
                task.backupProgress = parseFloat(task.backupProgress);
                _this.backupTaskList[i] = task;
                isExit = true;
              }
            }
            if (isExit === false) {
              task.backupProgress = parseFloat(task.backupProgress);
              _this.backupTaskList.push(task);
            }
          }
          if (dataMap.content && dataMap.content === '任务备份完成') {
            ElNotification({
              title: "任务备份成功通知",
              type: 'success',
              message: dataMap.content,
            })
          } else if (dataMap.content && dataMap.content === '任务备份失败') {
            ElNotification({
              title: "任务备份失败通知",
              type: 'error',
              message: dataMap.message,
            })
          }
          // console.log("收到备份任务进度消息：");
        };
        //关闭事件
        _this.socket.onclose = function () {
          console.log("websocket已关闭");
        };
        //发生了错误事件
        _this.socket.onerror = function () {
          console.log("websocket发生错误");
        }
      }
    },

    /**
     * 切换所激活主题
     */
    handleSelectActive(activeIndex) {
      // console.log("handleSelectActive:" + JSON.stringify(activeIndex));
      if (activeIndex === '1') {
        // console.log("查看任务列表");
        // for (let i = 0; i < this.backupTaskList.length; i++) {
        //   console.log(JSON.stringify(this.backupTaskList[i]));
        // }
        this.backupTaskListDialogVisible = true;
      } else if (activeIndex === '6') {
        this.sysParamDialogVisible = true;
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

    /**
     * 格式化时间
     * @param time
     */
    formatTime(time) {
      return timeDisplayUtil.formatTime(time)
    },

    /**
     * 重新连接
     */
    reconnect() {
      // console.log("重连");
      // 防止重复连接
      if (this.lockReconnect == true) {
        return;
      }
      // 锁定，防止重复连接
      this.lockReconnect = true;
      this.initWebSocket();
      // 连接完成，设置为false
      this.lockReconnect = false;
    },

    /**
     * 心跳，监测websocket是否锻炼
     */
    headbeat() {
      // console.log("websocket心跳");
      let that = this;
      setTimeout(function () {
        if (that.socket !== undefined && that.socket.readyState === 1) {
          // 调用启动下一轮的心跳
          that.headbeat();
        } else {
          // websocket还没有连接成功，重连
          that.reconnect();
          that.headbeat();
        }
      }, that.heartbeatTime);
    },

    truncateString(str, num) {
      if (str.length <= num) {
        return str;
      } else {
        return str.slice(0, num) + "...";
      }
    },

    /**
     * 复制文件路径
     * @param text
     */
    copyFilePath(text) {
      let eInput = document.createElement('input')
      eInput.value = text
      document.body.appendChild(eInput)
      eInput.select()
      let copyText = document.execCommand('Copy')
      eInput.style.display = 'none'
      if (copyText) {
        ElMessage.success('路径复制成功!')
      }
    },

    stopTaskById(taskId) {
      backupTaskApi.stopTaskById(taskId).then(res => {
        ElMessage({
          message: "备份任务暂停完成",
          type: 'success',
          duration: 2 * 1000
        })
      })
    },

    formatBytes(byteNum) {
      return storageUtil.formatBytes(byteNum);
    }

  },
  beforeCreate() {
  }
  ,

  created() {
    this.headbeat();
    this.listProcessingTask();
  }
  ,

  beforeMount() {

  }
  ,

  mounted() {
    mouseEffectsUtil.drawStar();
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
  height: 100vh;
  background-color: #abc6f8;
  background-image: radial-gradient(closest-side, rgb(255, 255, 255), rgba(235, 105, 78, 0)), radial-gradient(closest-side, rgb(250, 203, 203), rgba(243, 11, 164, 0)), radial-gradient(closest-side, rgb(237, 252, 202), rgba(254, 234, 131, 0)), radial-gradient(closest-side, rgb(197, 248, 241), rgba(170, 142, 245, 0)), radial-gradient(closest-side, rgb(206, 200, 243), rgba(248, 192, 147, 0));
  background-size: 130vmax 130vmax, 80vmax 80vmax, 90vmax 90vmax, 110vmax 110vmax, 90vmax 90vmax;
  background-position: -80vmax -80vmax, 60vmax -30vmax, 10vmax 10vmax, -30vmax -10vmax, 50vmax 50vmax;
  background-repeat: no-repeat;
  /* 背景颜色4秒循环一次 */
  animation: 4s movement linear infinite;
  font-family: Arial, Helvetica, sans-serif, Times New Roman;

  .menuClass {
    border-radius: 5px;
    height: 60px;

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

  .gapDiv {
    height: 5px;
  }

  .bodyDiv {
    margin-top: 10px;
    display: flex;
    height: calc(100vh - 20px - 50px - 50px);

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

      //添加垂直滚动条
      overflow-y: auto;
      overflow-x: auto;
    }

    /* 定义滚动条的轨道和滑块样式 */
    .table2Div:hover::-webkit-scrollbar-track {
      background-color: #f1f1f1;
    }

    .table2Div:hover::-webkit-scrollbar-thumb {
      background-color: #D2D2D2;
      border-radius: 5px;
    }

    .table2Div:hover::-webkit-scrollbar-button {
      background-color: #D2D2D2;
      height: 5px;
    }

    /* 隐藏滚动条 */
    .table2Div::-webkit-scrollbar {
      // 隐藏滚动条宽度
      // width: 0 !important;
      // 隐藏滚动条高度
      // height: 0 !important;
      // 隐藏滚动条背景
      background-color: transparent;
      width: 10px;
      height: 5px;
    }

    /* 鼠标进来的时候显示滚动条 */
    .table2Div:hover::-webkit-scrollbar {
      // background-color: #f1f1f1;
      width: 10px;
      height: 5px;
    }
  }
}
</style>

