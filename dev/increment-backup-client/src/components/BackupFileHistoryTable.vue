<template>
  <div>
    <div class="tableTitle">
      <div>
        文件备份历史管理
      </div>
      <div>
        <!--        <CirclePlus style="width: 1.3em; height: 1.3em; margin-right: 8px;color:#0368c5;"-->
        <!--                    @click="addBackupFileHistoryDialog()"/>-->
        <Delete style="width: 1.3em; height: 1.3em; margin-right: 8px;color:#f54248"
                @click="removeByBackupFileHistoryIds"/>
        <!--        <Edit style="width: 1.3em; height: 1.3em; margin-right: 8px;color:#069a1e"-->
        <!--              @click="updateBackupFileHistoryDialog()"/>-->
        <Search style="width: 1.3em; height: 1.3em; margin-right: 8px;color:#8a4f03"
                @click="searchBackupFileHistoryDialogVisible=true"/>
      </div>
    </div>
    <div class="table">
      <el-table :data="fileHistoryList" @selection-change="handleBackupFileHistorySelectionChange"
                @select="changeFileHistory" border>

        <el-table-column type="selection" width="55"/>
        <el-table-column prop="id" label="编号" width="100" resizable :show-overflow-tooltip="true"/>
        <el-table-column prop="backupFileId" label="对应的备份文件id" width="200" resizable
                         :show-overflow-tooltip="true"/>
        <el-table-column prop="backupSourceFilePath" label="源文件目录" width="200" resizable
                         :show-overflow-tooltip="true"/>
        <el-table-column prop="backupTargetFilePath" label="文件目标目录" width="200" resizable
                         :show-overflow-tooltip="true"/>
        <el-table-column prop="md5" label="md5" width="200" resizable
                         :show-overflow-tooltip="true"/>
        <el-table-column prop="backupTaskId" label="所属任务ID" width="200" resizable
                         :show-overflow-tooltip="true"/>
        <el-table-column prop="modifyTime" label="文件修改时间" width="180" resizable
                         :show-overflow-tooltip="true"
                         :formatter="formatDate"/>
        <el-table-column prop="backupStartTime" label="备份开始时间" width="180" resizable
                         :show-overflow-tooltip="true"
                         :formatter="formatDate"/>
        <el-table-column prop="backupEndTime" label="备份结束时间" width="180" resizable
                         :show-overflow-tooltip="true"
                         :formatter="formatDate"/>
      </el-table>
      <div style="padding: 10px">
        <el-pagination background layout="total, sizes, prev, pager, next, jumper"
                       :page-sizes="[10, 20, 30, 40]"
                       :total=backupFileHistoryTotal
                       v-model:current-page="backupFileHistoryCurrent"
                       v-model:page-size="backupFileHistorySize"
                       small="small"
                       @size-change="handleBackFileHistorySizeChange"
                       @current-change="handleBackFileHistoryCurrentChange"/>
      </div>
    </div>
    <!-- 添加或修改数据源 -->
    <el-dialog
        v-model="addOrUpdateBackupFileHistoryDialogVisible"
        :title=addOrUpdateBackupFileHistoryTitle
        width="30%"
    >
      <el-form :model="addOrUpdateBackupFileHistoryForm">
        <el-form-item label="数据源根目录" :label-width="110">
          <el-input v-model="addOrUpdateBackupFileHistoryForm.rootPath" autocomplete="off"/>
        </el-form-item>
        <el-form-item label="基 本 介 绍" :label-width="110">
          <el-input v-model="addOrUpdateBackupFileHistoryForm.backupName" autocomplete="off"/>
        </el-form-item>
      </el-form>
      <template #footer>
          <span class="dialog-footer">
            <el-button @click="addOrUpdateBackupFileHistoryDialogVisible = false">取 消</el-button>
            <el-button type="primary" @click="addOrUpdateBackupFileHistory">
              确 定
            </el-button>
          </span>
      </template>
    </el-dialog>
    <!-- 查询数据源 -->
    <el-dialog
        v-model="searchBackupFileHistoryDialogVisible"
        title="请输入要查询的字段，不需要查询的可以空着"
        width="30%"
    >
      <el-form :model="searchBackupFileHistoryForm">
        <el-form-item label="备份文件ID" :label-width="110">
          <el-input v-model="searchBackupFileHistoryForm.backupFileId" autocomplete="off"/>
        </el-form-item>
        <el-form-item label="备份目标目录ID" :label-width="110">
          <el-input v-model="searchBackupFileHistoryForm.backupTargetRootId" autocomplete="off"/>
        </el-form-item>
        <el-form-item label="备份任务ID" :label-width="110">
          <el-input v-model="searchBackupFileHistoryForm.backupTaskId" autocomplete="off"/>
        </el-form-item>
        <el-form-item label="源文件目录" :label-width="110">
          <el-input v-model="searchBackupFileHistoryForm.backupSourceFilePath" autocomplete="off"/>
        </el-form-item>
        <el-form-item label="文件目标目录" :label-width="110">
          <el-input v-model="searchBackupFileHistoryForm.backupTargetFilePath" autocomplete="off"/>
        </el-form-item>
      </el-form>
      <template #footer>
          <span class="dialog-footer">
            <el-button @click="searchBackupFileHistoryDialogVisible = false">取 消</el-button>
            <el-button @click="clearSearch()">清 空</el-button>
            <el-button type="primary" @click="listBackupFileHistory(true)">
              查 询
            </el-button>
          </span>
      </template>
    </el-dialog>
  </div>
</template>

<script>

import backupFileHistoryApi from "../api/backupFileHistoryApi.js";
import backupTargetApi from "../api/backupTargetApi.js";
import {ElMessage} from "element-plus";
import backupApi from "../api/backupApi.js";

export default {
  components: {},
  data() {
    return {
      fileHistoryList: [],
      // 增加或修改数据源
      addOrUpdateBackupFileHistoryDialogVisible: false,
      addOrUpdateBackupFileHistoryTitle: '',
      addOrUpdateBackupFileHistoryForm: {},
      // 查询数据源
      searchBackupFileHistoryDialogVisible: false,
      searchBackupFileHistoryForm: {},
      backupFileHistoryCurrent: 1,
      backupFileHistorySize: 10,
      backupFileHistoryTotal: 0,
      // 选择的数据源id数组
      selectBackupFileHistoryIdArr: [],

      selectBackupFileHistory: '',
    };
  },
  computed: {},
  watch: {},
  methods: {
    /**
     * 分页查询数据源
     */
    listBackupFileHistory(isSearch) {
      this.searchBackupFileHistoryForm.current = this.backupFileHistoryCurrent;
      this.searchBackupFileHistoryForm.size = this.backupFileHistorySize;
      // console.log("searchBackupFileHistoryForm:" + JSON.stringify(this.searchBackupFileHistoryForm));
      backupFileHistoryApi.list(this.searchBackupFileHistoryForm).then(res => {
        // console.log(JSON.stringify(res))
        this.fileHistoryList = res.data.records;
        this.backupFileHistoryTotal = res.data.total;
        if (isSearch == true) {
          this.searchBackupFileHistoryDialogVisible = false;
          ElMessage({
            message: "查询成功",
            type: 'success',
            duration: 2 * 1000
          })
        }
      })
    },
    /**
     * 清空查询条件
     */
    clearSearch() {
      this.searchBackupFileHistoryDialogVisible = false;
      this.searchBackupFileHistoryForm = {};
      this.listBackupFileHistory(true);
    },
    addBackupFileHistoryDialog() {
      this.addOrUpdateBackupFileHistoryTitle = "增加数据源";
      this.addOrUpdateBackupFileHistoryDialogVisible = true;
      this.addOrUpdateBackupFileHistoryForm = {};
    },
    updateBackupFileHistoryDialog() {
      if (this.selectBackupFileHistoryIdArr.length == 1) {
        this.addOrUpdateBackupFileHistoryTitle = "修改数据源";
        this.addOrUpdateBackupFileHistoryDialogVisible = true;
        backupFileHistoryApi.getById(this.selectBackupFileHistoryIdArr[0]).then(res => {
          // console.log("根据id查询数据源：" + JSON.stringify(res));
          this.addOrUpdateBackupFileHistoryForm = res.data;
        })
      } else if (this.selectBackupFileHistoryIdArr.length > 1) {
        ElMessage({
          message: "所选中的数据源数据超过一个，无法修改",
          type: 'error',
          duration: 2 * 1000
        })
      } else {
        ElMessage({
          message: "没有选中数据，无法修改",
          type: 'error',
          duration: 2 * 1000
        })
      }
    },
    /**
     * 增加或修改数据源
     */
    addOrUpdateBackupFileHistory() {
      if (this.addOrUpdateBackupFileHistoryForm.id) {
        backupFileHistoryApi.update(this.addOrUpdateBackupFileHistoryForm).then(res => {
              this.addOrUpdateBackupFileHistoryDialogVisible = false;
              this.listBackupFileHistory(false);
              // 情况表单
              this.addOrUpdateBackupFileHistoryForm = {};
              ElMessage({
                message: "修改成功",
                type: 'success',
                duration: 2 * 1000
              })
            }
        )
      } else {
        // console.log("this.addOrUpdateBackupFileHistoryForm:" + JSON.stringify(this.addOrUpdateBackupFileHistoryForm))
        backupFileHistoryApi.save(this.addOrUpdateBackupFileHistoryForm).then(res => {
              this.addOrUpdateBackupFileHistoryDialogVisible = false;
              this.listBackupFileHistory(false);
              // 情况表单
              this.addOrUpdateBackupFileHistoryForm = {};

              ElMessage({
                message: "添加成功",
                type: 'success',
                duration: 2 * 1000
              })
            }
        )
      }
    },
    /**
     * 选中的数据源发生变化
     * @param val
     */
    handleBackupFileHistorySelectionChange(val) {
      this.selectBackupFileHistoryIdArr = [];
      val.forEach((item) => {
        this.selectBackupFileHistoryIdArr.push(item.id);
      })
      // console.log("选中ID数组：" + JSON.stringify(this.selectBackupFileHistoryIdArr));
    },
    /**
     * 删除选中的数据
     */
    removeByBackupFileHistoryIds() {
      backupFileHistoryApi.removeByIds(this.selectBackupFileHistoryIdArr).then(res => {
        this.listBackupFileHistory(false);
        ElMessage({
          message: "删除成功",
          type: 'success',
          duration: 2 * 1000
        })
      })
    },

    /**
     * 分页大小改变
     */
    handleBackFileHistorySizeChange(val) {
      // console.log("分页大小改变，val:" + val);
      this.backupFileHistorySize = val;
      this.listBackupFileHistory(false);
    },
    /**
     * 分页页数改变
     */
    handleBackFileHistoryCurrentChange(val) {
      this.backupFileHistoryCurrent = val;
      this.listBackupFileHistory(false);
    },

    /**
     * 点击表格的一行触发
     * @param val 所点击行的数据
     */
    changeFileHistory(val) {
      this.selectBackupFileHistory = val;
      this.$emit("changeFileHistoryChange", this.selectBackupFileHistory);
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
  },

  created() {
    this.listBackupFileHistory(false);
  },

  beforeMount() {

  },

  mounted() {

  },

  beforeUpdate() {

  },

  updated() {

  },

  beforeDestroy() {

  },
  destroyed() {

  },

  activated() {

  },
}


</script>

<style scoped lang="scss">
.tableTitle {
  background: rgba(192, 217, 245, 0.8);
  font-size: 18px;
  font-weight: bold;
  margin-bottom: 5px;
  padding: 8px;

  display: flex;
  justify-content: space-between;
}

.table {
  background: rgba(255, 255, 255, 1);
}
</style>

