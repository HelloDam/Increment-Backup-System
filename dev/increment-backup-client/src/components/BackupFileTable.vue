<template>
  <div>
    <div class="tableTitle">
      <div>
        数据源目录管理
      </div>
      <div>
        <!--        <CirclePlus style="width: 1.3em; height: 1.3em; margin-right: 8px;color:#0368c5;"-->
        <!--                    @click="addBackupFileDialog()"/>-->
        <Delete style="width: 1.3em; height: 1.3em; margin-right: 8px;color:#f54248"
                @click="removeByBackupFileIds"/>
        <!--        <Edit style="width: 1.3em; height: 1.3em; margin-right: 8px;color:#069a1e"-->
        <!--              @click="updateBackupFileDialog()"/>-->
        <Search style="width: 1.3em; height: 1.3em; margin-right: 8px;color:#8a4f03"
                @click="searchBackupFileDialogVisible=true"/>
      </div>
    </div>
    <div class="table">
      <el-table :data="fileList" @selection-change="handleBackupFileSelectionChange" @select="changeFile" border>
        <el-table-column type="selection" width="55"/>

        <el-table-column prop="id" label="编号" width="200" :show-overflow-tooltip="true"/>
        <el-table-column prop="backupSourceId" label="数据源ID" resizable width="200"
                         :show-overflow-tooltip="true"/>
        <el-table-column prop="backupTargetId" label="备份目标目录ID" resizable width="200"
                         :show-overflow-tooltip="true"/>
        <el-table-column prop="filePath" label="文件路径" resizable width="auto"
                         :show-overflow-tooltip="true"/>
        <el-table-column prop="backupNum" label="备份次数" resizable width="100"
                         :show-overflow-tooltip="true"/>
        <el-table-column prop="lastBackupTime" label="上次备份时间" width="180" :show-overflow-tooltip="true"
                         :formatter="formatDate"/>
      </el-table>
      <div style="padding: 10px">
        <el-pagination background layout="total, sizes, prev, pager, next, jumper"
                       :page-sizes="[10, 20, 30, 40]"
                       :total=backupFileTotal
                       v-model:current-page="backupFileCurrent"
                       v-model:page-size="backupFileSize"
                       small="small"
                       @size-change="handleBackFileSizeChange"
                       @current-change="handleBackFileCurrentChange"/>
      </div>
    </div>
    <!-- 添加或修改数据源 -->
    <el-dialog
        v-model="addOrUpdateBackupFileDialogVisible"
        :title=addOrUpdateBackupFileTitle
        width="30%"
    >
      <el-form :model="addOrUpdateBackupFileForm">
        <el-form-item label="数据源根目录" :label-width="110">
          <el-input v-model="addOrUpdateBackupFileForm.rootPath" autocomplete="off"/>
        </el-form-item>
        <el-form-item label="基 本 介 绍" :label-width="110">
          <el-input v-model="addOrUpdateBackupFileForm.backupName" autocomplete="off"/>
        </el-form-item>
      </el-form>
      <template #footer>
          <span class="dialog-footer">
            <el-button @click="addOrUpdateBackupFileDialogVisible = false">取 消</el-button>
            <el-button type="primary" @click="addOrUpdateBackupFile">
              确 定
            </el-button>
          </span>
      </template>
    </el-dialog>
    <!-- 查询数据源 -->
    <el-dialog
        v-model="searchBackupFileDialogVisible"
        title="请输入要查询的字段，不需要查询的可以空着"
        width="30%"
    >
      <el-form :model="searchBackupFileForm">
        <el-form-item label="数据源根目录" :label-width="110">
          <el-input v-model="searchBackupFileForm.rootPath" autocomplete="off"/>
        </el-form-item>
        <el-form-item label="基 本 介 绍" :label-width="110">
          <el-input v-model="searchBackupFileForm.backupName" autocomplete="off"/>
        </el-form-item>
      </el-form>
      <template #footer>
          <span class="dialog-footer">
            <el-button @click="searchBackupFileDialogVisible = false">取 消</el-button>
            <el-button type="primary" @click="listBackupFile(true)">
              查 询
            </el-button>
          </span>
      </template>
    </el-dialog>
  </div>
</template>

<script>

import backupFileApi from "../api/backupFileApi.js";
import backupTargetApi from "../api/backupTargetApi.js";
import {ElMessage} from "element-plus";
import backupApi from "../api/backupApi.js";

export default {
  components: {},
  data() {
    return {
      fileList: [],
      // 增加或修改数据源
      addOrUpdateBackupFileDialogVisible: false,
      addOrUpdateBackupFileTitle: '',
      addOrUpdateBackupFileForm: {},
      // 查询数据源
      searchBackupFileDialogVisible: false,
      searchBackupFileForm: {},
      backupFileCurrent: 1,
      backupFileSize: 10,
      backupFileTotal: 0,
      // 选择的数据源id数组
      selectBackupFileIdArr: [],

      selectBackupFile: '',
    };
  },
  computed: {},
  watch: {},
  methods: {
    /**
     * 分页查询数据源
     */
    listBackupFile(isSearch) {
      this.searchBackupFileForm.current = this.backupFileCurrent;
      this.searchBackupFileForm.size = this.backupFileSize;
      backupFileApi.list(this.searchBackupFileForm).then(res => {
        // console.log(JSON.stringify(res))
        this.fileList = res.data.records;
        this.backupFileTotal = res.data.total;
        if (isSearch === true) {
          this.searchBackupFileDialogVisible = false;
          this.searchBackupFileForm = {};
          ElMessage({
            message: "查询成功",
            type: 'success',
            duration: 5 * 1000
          })
        }
      })
    },
    addBackupFileDialog() {
      this.addOrUpdateBackupFileTitle = "增加数据源";
      this.addOrUpdateBackupFileDialogVisible = true;
      this.addOrUpdateBackupFileForm = {};
    },
    updateBackupFileDialog() {
      if (this.selectBackupFileIdArr.length == 1) {
        this.addOrUpdateBackupFileTitle = "修改数据源";
        this.addOrUpdateBackupFileDialogVisible = true;
        backupFileApi.getById(this.selectBackupFileIdArr[0]).then(res => {
          // console.log("根据id查询数据源：" + JSON.stringify(res));
          this.addOrUpdateBackupFileForm = res.data;
        })
      } else if (this.selectBackupFileIdArr.length > 1) {
        ElMessage({
          message: "所选中的数据源数据超过一个，无法修改",
          type: 'error',
          duration: 5 * 1000
        })
      } else {
        ElMessage({
          message: "没有选中数据，无法修改",
          type: 'error',
          duration: 5 * 1000
        })
      }
    },
    /**
     * 增加或修改数据源
     */
    addOrUpdateBackupFile() {
      if (this.addOrUpdateBackupFileForm.id) {
        backupFileApi.update(this.addOrUpdateBackupFileForm).then(res => {
              this.addOrUpdateBackupFileDialogVisible = false;
              this.listBackupFile(false);
              // 情况表单
              this.addOrUpdateBackupFileForm = {};
              ElMessage({
                message: "修改成功",
                type: 'success',
                duration: 5 * 1000
              })
            }
        )
      } else {
        // console.log("this.addOrUpdateBackupFileForm:" + JSON.stringify(this.addOrUpdateBackupFileForm))
        backupFileApi.save(this.addOrUpdateBackupFileForm).then(res => {
              this.addOrUpdateBackupFileDialogVisible = false;
              this.listBackupFile(false);
              // 情况表单
              this.addOrUpdateBackupFileForm = {};

              ElMessage({
                message: "添加成功",
                type: 'success',
                duration: 5 * 1000
              })
            }
        )
      }
    },
    /**
     * 选中的数据源发生变化
     * @param val
     */
    handleBackupFileSelectionChange(val) {
      this.selectBackupFileIdArr = [];
      val.forEach((item) => {
        this.selectBackupFileIdArr.push(item.id);
      })
      // console.log("选中ID数组：" + JSON.stringify(this.selectBackupFileIdArr));
    },
    /**
     * 删除选中的数据
     */
    removeByBackupFileIds() {
      backupFileApi.removeByIds(this.selectBackupFileIdArr).then(res => {
        this.listBackupFile(false);
        ElMessage({
          message: "删除成功",
          type: 'success',
          duration: 5 * 1000
        })
      })
    },

    /**
     * 分页大小改变
     */
    handleBackFileSizeChange(val) {
      this.backupFileSize = val;
      this.listBackupFile(false);
    },
    /**
     * 分页页数改变
     */
    handleBackFileCurrentChange(val) {
      this.backupFileCurrent = val;
      this.listBackupFile(false);
    },

    /**
     * 点击表格的一行触发
     * @param val 所点击行的数据
     */
    changeFile(val) {
      this.selectBackupFile = val;
      this.$emit("changeFileChange", this.selectBackupFile);
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
    this.listBackupFile(false);
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

