<template>
  <div>
    <div class="tableTitle">
      <div>
        数据备份目标目录管理
      </div>
      <div>
        <el-tooltip
            class="box-item"
            effect="dark"
            content="增加备份目标目录"
            placement="top"
        >
          <CirclePlus style="width: 1.3em; height: 1.3em; margin-right: 8px;color:#0368c5;"
                      @click="addBackupTargetDialog()"/>
        </el-tooltip>
        <el-tooltip
            class="box-item"
            effect="dark"
            content="删除备份目标目录"
            placement="top"
        >
          <Delete style="width: 1.3em; height: 1.3em; margin-right: 8px;color:#f54248"
                  @click="removeByBackupTargetIds"/>
        </el-tooltip>
        <el-tooltip
            class="box-item"
            effect="dark"
            content="修改备份目标目录"
            placement="top"
        >
          <Edit style="width: 1.3em; height: 1.3em; margin-right: 8px;color:#069a1e"
                @click="updateBackupTargetDialog()"/>
        </el-tooltip>
        <el-tooltip
            class="box-item"
            effect="dark"
            content="条件查询"
            placement="top"
        >
          <Search style="width: 1.3em; height: 1.3em; margin-right: 8px;color:#8a4f03"
                  @click="searchBackupTargetDialogVisible=true"/>
        </el-tooltip>
      </div>
    </div>
    <div class="table">
      <el-table :data="backupTargetList" @selection-change="handleBackupTargetSelectionChange" border="true">
        <el-table-column type="selection" width="55"/>
        <el-table-column prop="id" label="编号" width="100" resizable="true" :show-overflow-tooltip="true"/>
        <el-table-column prop="backupSourceId" label="数据源ID" width="200" resizable="true"
                         :show-overflow-tooltip="true"/>
        <el-table-column prop="targetRootPath" label="目标目录路径" width="300" resizable="true"
                         :show-overflow-tooltip="true"/>
        <el-table-column prop="createTime" label="创建时间" width="180" resizable="true" :show-overflow-tooltip="true"
                         :formatter="formatDate"/>
        <el-table-column prop="updateTime" label="修改时间" width="180" resizable="true" :show-overflow-tooltip="true"
                         :formatter="formatDate"/>
      </el-table>
      <div style="padding: 10px">
        <el-pagination background layout="total, sizes, prev, pager, next, jumper" :page-sizes="[10, 20, 30, 40]"
                       :total=backupTargetTotal
                       v-model:current-page="backupTargetCurrent"
                       v-model:page-size="backupTargetSize"
                       small="small"
                       @size-change="handleBackupTargetSizeChange"
                       @current-change="handleBackupTargetCurrentChange"/>
      </div>
    </div>
    <!-- 添加或修改备份目标目录 -->
    <el-dialog
        v-model="addOrUpdateBackupTargetDialogVisible"
        :title=addOrUpdateBackupTargetTitle
        width="30%"
    >
      <el-form :model="addOrUpdateBackupTargetForm">
        <el-form-item label="数据源根目录" :label-width="110">
          <el-input v-model="addOrUpdateBackupTargetForm.rootPath" autocomplete="off" disabled/>
        </el-form-item>
        <el-form-item label="目标目录路径" :label-width="110">
          <el-input v-model="addOrUpdateBackupTargetForm.targetRootPath" autocomplete="off"/>
        </el-form-item>
      </el-form>
      <template #footer>
          <span class="dialog-footer">
            <el-button @click="addOrUpdateBackupTargetDialogVisible = false">取 消</el-button>
            <el-button type="primary" @click="addOrUpdateBackupTarget">
              确 定
            </el-button>
          </span>
      </template>
    </el-dialog>
    <!-- 查询备份目标目录 -->
    <el-dialog
        v-model="searchBackupTargetDialogVisible"
        title="请输入要查询的字段，不需要查询的可以空着"
        width="30%"
    >
      <el-form :model="searchBackupTargetForm">
        <el-form-item label="目标目录路径" :label-width="110">
          <el-input v-model="searchBackupTargetForm.targetRootPath" autocomplete="off"/>
        </el-form-item>
      </el-form>
      <template #footer>
          <span class="dialog-footer">
            <el-button @click="searchBackupTargetDialogVisible = false">取 消</el-button>
            <el-button type="primary" @click="listBackupTarget(true)">
              查 询
            </el-button>
          </span>
      </template>
    </el-dialog>
  </div>
</template>

<script>

import backupTargetApi from "../api/backupTargetApi.js";
import {ElMessage} from "element-plus";

export default {
  components: {},
  props: {
    selectBackupSource: Object,
  },
  data() {
    return {
      backupTargetList: [],
      // 增加或修改备份目标目录
      addOrUpdateBackupTargetDialogVisible: false,
      addOrUpdateBackupTargetTitle: '',
      addOrUpdateBackupTargetForm: {},
      // 查询备份目标目录
      searchBackupTargetDialogVisible: false,
      searchBackupTargetForm: {},
      backupTargetCurrent: 1,
      backupTargetSize: 10,
      backupTargetTotal: 0,
      // 选择的备份目标目录id数组
      selectBackupTargetIdArr: [],
    };
  },
  computed: {},
  /**
   * 监听父组件的数据变化
   */
  watch: {
    selectBackupSource: {
      handler(newValue, oldValue) {
        console.log("父组件数据更新")
        this.listBackupTarget(false);
      },
    }
  },
  methods: {
    /**
     * 分页查询备份目标目录
     */
    listBackupTarget(isSearch) {
      if (this.selectBackupSource && this.selectBackupSource.length > 0) {
        this.searchBackupTargetForm.current = this.backupTargetCurrent;
        this.searchBackupTargetForm.size = this.backupTargetSize;
        this.searchBackupTargetForm.backupSourceId = this.selectBackupSource[0].id;
        backupTargetApi.list(this.searchBackupTargetForm).then(res => {
          console.log(JSON.stringify(res))
          this.backupTargetList = res.data.records;
          this.backupTargetTotal = res.data.total;
          if (isSearch === true) {
            this.searchBackupTargetDialogVisible = false;
            this.searchBackupTargetForm = {};
            ElMessage({
              message: "查询成功",
              type: 'success',
              duration: 5 * 1000
            })
          }
        })
      } else {
        this.backupTargetList = [];
      }
    },
    addBackupTargetDialog() {
      if (this.selectBackupSource && this.selectBackupSource.length > 0) {
        console.log("已经勾选数据源")
        console.log("this.selectBackupSource:" + JSON.stringify(this.selectBackupSource))
        this.addOrUpdateBackupTargetTitle = "增加备份目标目录";
        this.addOrUpdateBackupTargetForm = {};
        this.addOrUpdateBackupTargetForm.backupSourceId = this.selectBackupSource[0].id;
        this.addOrUpdateBackupTargetForm.rootPath = this.selectBackupSource[0].rootPath;
        this.addOrUpdateBackupTargetDialogVisible = true;
        console.log("this.addOrUpdateBackupTargetForm:" + JSON.stringify(this.addOrUpdateBackupTargetForm))
      } else {
        ElMessage({
          message: "请先选中数据源，再为其添加目标备份目录",
          type: 'error',
          duration: 5 * 1000
        })
      }
    },
    updateBackupTargetDialog() {
      if (this.selectBackupTargetIdArr.length == 1) {
        this.addOrUpdateBackupTargetTitle = "修改备份目标目录";
        this.addOrUpdateBackupTargetDialogVisible = true;
        backupTargetApi.getById(this.selectBackupTargetIdArr[0]).then(res => {
          console.log("根据id查询备份目标目录：" + JSON.stringify(res));
          this.addOrUpdateBackupTargetForm = res.data;
          this.addOrUpdateBackupTargetForm.rootPath = this.selectBackupSource[0].rootPath;
        })
      } else if (this.selectBackupTargetIdArr.length > 1) {
        ElMessage({
          message: "所选中的备份目标目录数据超过一个，无法修改",
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
     * 增加或修改备份目标目录
     */
    addOrUpdateBackupTarget() {
      if (this.addOrUpdateBackupTargetForm.id) {
        backupTargetApi.update(this.addOrUpdateBackupTargetForm).then(res => {
              this.addOrUpdateBackupTargetDialogVisible = false;
              this.listBackupTarget(false);
              // 情况表单
              this.addOrUpdateBackupTargetForm = {};
              ElMessage({
                message: "修改成功",
                type: 'success',
                duration: 5 * 1000
              })
            }
        )
      } else {
        backupTargetApi.save(this.addOrUpdateBackupTargetForm).then(res => {
              this.addOrUpdateBackupTargetDialogVisible = false;
              this.listBackupTarget(false);
              // 情况表单
              this.addOrUpdateBackupTargetForm = {};

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
     * 选中的备份目标目录发生变化
     * @param val
     */
    handleBackupTargetSelectionChange(val) {
      this.selectBackupTargetIdArr = [];
      val.forEach((item) => {
        this.selectBackupTargetIdArr.push(item.id);
      })
      console.log("选中ID数组：" + JSON.stringify(this.selectBackupTargetIdArr));
    },
    /**
     * 删除选中的数据
     */
    removeByBackupTargetIds() {
      backupTargetApi.removeByIds(this.selectBackupTargetIdArr).then(res => {
        this.listBackupTarget(false);
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
    handleBackupTargetSizeChange(val) {
      this.backupTargetSize = val;
      this.listBackupTarget(false);
    },
    /**
     * 分页页数改变
     */
    handleBackupTargetCurrentChange(val) {
      this.backupTargetCurrent = val;
      this.listBackupTarget(false);
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

