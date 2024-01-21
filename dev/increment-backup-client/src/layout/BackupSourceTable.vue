<template>
  <div>
    <div class="tableTitle">
      <div>
        数据源目录
      </div>
      <div>
        <CirclePlus style="width: 1.3em; height: 1.3em; margin-right: 8px;color:#0368c5;"
                    @click="addBackupSourceDialog()"/>
        <Delete style="width: 1.3em; height: 1.3em; margin-right: 8px;color:#f54248"
                @click="removeByBackupSourceIds"/>
        <Edit style="width: 1.3em; height: 1.3em; margin-right: 8px;color:#069a1e"
              @click="updateBackupSourceDialog()"/>
        <Search style="width: 1.3em; height: 1.3em; margin-right: 8px;color:#8a4f03"
                @click="searchBackupSourceDialogVisible=true"/>
      </div>
    </div>
    <div class="table">
      <el-table :data="sourceList" @selection-change="handleBackupSourceSelectionChange">
        <el-table-column type="selection" width="55"/>
        <el-table-column prop="id" label="编号" width="100" :show-overflow-tooltip="true"/>
        <el-table-column prop="rootPath" label="根目录路径" width="300" :show-overflow-tooltip="true"/>
        <el-table-column prop="backupName" label="简要介绍" width="200" :show-overflow-tooltip="true"/>
        <el-table-column prop="createTime" label="创建时间" width="180" :show-overflow-tooltip="true"
                         :formatter="formatDate"/>
        <el-table-column prop="updateTime" label="修改时间" width="180" :show-overflow-tooltip="true"
                         :formatter="formatDate"/>
      </el-table>
      <div style="padding: 10px">
        <el-pagination background layout="total, sizes, prev, pager, next, jumper" :page-sizes="[10, 20, 30, 40]"
                       :total=backSourceTotal
                       v-model:current-page="backupSourceCurrent"
                       v-model:page-size="backupSourceSize"
                       small="small"
                       @size-change="handleBackSourceSizeChange"
                       @current-change="handleBackSourceCurrentChange"/>
      </div>
    </div>
    <!-- 添加或修改数据源 -->
    <el-dialog
        v-model="addOrUpdateBackupSourceDialogVisible"
        :title=addOrUpdateBackupSourceTitle
        width="30%"
    >
      <el-form :model="addOrUpdateBackupSourceForm">
        <el-form-item label="数据源根目录" :label-width="110">
          <el-input v-model="addOrUpdateBackupSourceForm.rootPath" autocomplete="off"/>
        </el-form-item>
        <el-form-item label="基 本 介 绍" :label-width="110">
          <el-input v-model="addOrUpdateBackupSourceForm.backupName" autocomplete="off"/>
        </el-form-item>
      </el-form>
      <template #footer>
          <span class="dialog-footer">
            <el-button @click="addOrUpdateBackupSourceDialogVisible = false">取 消</el-button>
            <el-button type="primary" @click="addOrUpdateBackupSource">
              确 定
            </el-button>
          </span>
      </template>
    </el-dialog>
    <!-- 查询数据源 -->
    <el-dialog
        v-model="searchBackupSourceDialogVisible"
        title="请输入要查询的字段，不需要查询的可以空着"
        width="30%"
    >
      <el-form :model="searchBackupSourceForm">
        <el-form-item label="数据源根目录" :label-width="110">
          <el-input v-model="searchBackupSourceForm.rootPath" autocomplete="off"/>
        </el-form-item>
        <el-form-item label="基 本 介 绍" :label-width="110">
          <el-input v-model="searchBackupSourceForm.backupName" autocomplete="off"/>
        </el-form-item>
      </el-form>
      <template #footer>
          <span class="dialog-footer">
            <el-button @click="searchBackupSourceDialogVisible = false">取 消</el-button>
            <el-button type="primary" @click="listBackSource(true)">
              查 询
            </el-button>
          </span>
      </template>
    </el-dialog>
  </div>
</template>

<script>

import backupSourceApi from "../api/backupSourceApi.js";
import {ElMessage} from "element-plus";

export default {
  components: {},
  data() {
    return {
      sourceList: [],
      // 增加或修改数据源
      addOrUpdateBackupSourceDialogVisible: false,
      addOrUpdateBackupSourceTitle: '',
      addOrUpdateBackupSourceForm: {},
      // 查询数据源
      searchBackupSourceDialogVisible: false,
      searchBackupSourceForm: {},
      backupSourceCurrent: 1,
      backupSourceSize: 10,
      backSourceTotal: 0,
      // 选择的数据源id数组
      selectBackupSourceIdArr: [],
    };
  },
  computed: {},
  watch: {},
  methods: {
    /**
     * 分页查询数据源
     */
    listBackSource(isSearch) {
      this.searchBackupSourceForm.current = this.backupSourceCurrent;
      this.searchBackupSourceForm.size = this.backupSourceSize;
      backupSourceApi.list(this.searchBackupSourceForm).then(res => {
        console.log(JSON.stringify(res))
        this.sourceList = res.data.records;
        this.backSourceTotal = res.data.total;
        if (isSearch == true) {
          this.searchBackupSourceDialogVisible = false;
          this.searchBackupSourceForm = [];
          ElMessage({
            message: "查询成功",
            type: 'success',
            duration: 5 * 1000
          })
        }
      })
    },
    addBackupSourceDialog() {
      this.addOrUpdateBackupSourceTitle = "增加数据源";
      this.addOrUpdateBackupSourceDialogVisible = true;
      this.addOrUpdateBackupSourceForm = [];
    },
    updateBackupSourceDialog() {
      if (this.selectBackupSourceIdArr.length == 1) {
        this.addOrUpdateBackupSourceTitle = "修改数据源";
        this.addOrUpdateBackupSourceDialogVisible = true;
        backupSourceApi.getById(this.selectBackupSourceIdArr[0]).then(res => {
          console.log("根据id查询数据源：" + JSON.stringify(res));
          this.addOrUpdateBackupSourceForm = res.data;
        })
      } else if (this.selectBackupSourceIdArr.length > 1) {
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
    addOrUpdateBackupSource() {
      if (this.addOrUpdateBackupSourceForm.id) {
        backupSourceApi.update(this.addOrUpdateBackupSourceForm).then(res => {
              this.addOrUpdateBackupSourceDialogVisible = false;
              this.listBackSource(false);
              // 情况表单
              this.addOrUpdateBackupSourceForm = {};
              ElMessage({
                message: "修改成功",
                type: 'success',
                duration: 5 * 1000
              })
            }
        )
      } else {
        backupSourceApi.save(this.addOrUpdateBackupSourceForm).then(res => {
              this.addOrUpdateBackupSourceDialogVisible = false;
              this.listBackSource(false);
              // 情况表单
              this.addOrUpdateBackupSourceForm = {};

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
    handleBackupSourceSelectionChange(val) {
      this.selectBackupSourceIdArr = [];
      val.forEach((item) => {
        this.selectBackupSourceIdArr.push(item.id);
      })
      console.log("选中ID数组：" + JSON.stringify(this.selectBackupSourceIdArr));
    },
    /**
     * 删除选中的数据
     */
    removeByBackupSourceIds() {
      backupSourceApi.removeByIds(this.selectBackupSourceIdArr).then(res => {
        this.listBackSource(false);
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
    handleBackSourceSizeChange(val) {
      this.backupSourceSize = val;
      this.listBackSource(false);
    },
    /**
     * 分页页数改变
     */
    handleBackSourceCurrentChange(val) {
      this.backupSourceCurrent = val;
      this.listBackSource(false);
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
    this.listBackSource(false);
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

