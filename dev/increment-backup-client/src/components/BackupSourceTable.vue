<template>
  <div>
    <div class="tableTitle">
      <div>
        数据源目录管理
      </div>
      <div>
        <el-tooltip
            class="box-item"
            effect="dark"
            content="删除无用的数据，如数据源中已经删除的数据，将其从备份目录中删除，并相应删除其备份记录"
            placement="top"
        >
          <Filter style="width: 1.3em; height: 1.3em; margin-right: 8px;color:#9ca605;" @click="clearBySourceId()"/>
        </el-tooltip>
        <el-tooltip
            class="box-item"
            effect="dark"
            content="为勾选的数据源执行备份"
            placement="top"
        >
          <Compass style="width: 1.3em; height: 1.3em; margin-right: 8px;color:#039484;"
                   @click="backupBySourceId()"/>
        </el-tooltip>
        <el-tooltip
            class="box-item"
            effect="dark"
            content="增加数据源"
            placement="top"
        >
          <CirclePlus style="width: 1.3em; height: 1.3em; margin-right: 8px;color:#0368c5;"
                      @click="addBackupSourceDialog()"/>
        </el-tooltip>
        <el-tooltip
            class="box-item"
            effect="dark"
            content="删除数据源"
            placement="top"
        >
          <Delete style="width: 1.3em; height: 1.3em; margin-right: 8px;color:#f54248"
                  @click="removeByBackupSourceIds"/>
        </el-tooltip>
        <el-tooltip
            class="box-item"
            effect="dark"
            content="修改数据源"
            placement="top"
        >

          <Edit style="width: 1.3em; height: 1.3em; margin-right: 8px;color:#069a1e"
                @click="updateBackupSourceDialog()"/>
        </el-tooltip>
        <el-tooltip
            class="box-item"
            effect="dark"
            content="条件查询"
            placement="top"
        >
          <Search style="width: 1.3em; height: 1.3em; margin-right: 8px;color:#8a4f03"
                  @click="searchBackupSourceDialogVisible=true"/>
        </el-tooltip>
      </div>
    </div>
    <div class="table">
      <el-table :data="sourceList" @selection-change="handleBackupSourceSelectionChange" @select="changeSource"
                border>
        <el-table-column type="selection" width="55"/>
        <el-table-column prop="id" label="编号" width="100" resizable :show-overflow-tooltip="true"/>
        <el-table-column prop="rootPath" label="根目录路径" width="300" resizable :show-overflow-tooltip="true"/>
        <el-table-column prop="backupName" label="简要介绍" width="200" resizable :show-overflow-tooltip="true"/>
        <el-table-column prop="backupNum" label="备份次数" width="100" resizable :show-overflow-tooltip="true"/>
        <el-table-column prop="backupType" label="备份类型" width="250" resizable :show-overflow-tooltip="true">
          <template #default="scope">
            <el-tag v-if="scope.row.backupType==0" @click="seeFileMessage(scope.row.id)">
              数据全部备份到多个目标目录中
            </el-tag>
            <el-tag type="success" v-if="scope.row.backupType==1" @click="seeFileMessage(scope.row.id)">
              数据分散备份到多个目标目录中
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="createTime" label="创建时间" width="180" resizable :show-overflow-tooltip="true"
                         :formatter="formatDate"/>
        <el-table-column prop="updateTime" label="修改时间" width="180" resizable :show-overflow-tooltip="true"
                         :formatter="formatDate"/>
      </el-table>
      <div style="padding: 10px">
        <el-pagination background layout="total, sizes, prev, pager, next, jumper" :page-sizes="[10, 20, 30, 40]"
                       :total=backupSourceTotal
                       v-model:current-page="backupSourceCurrent"
                       v-model:page-size="backupSourceSize"
                       small="small"
                       @size-change="handleBackupSourceSizeChange"
                       @current-change="handleBackupSourceCurrentChange"/>
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
        <el-form-item label="备 份 类 型" :label-width="110">
          <el-select
              v-model="addOrUpdateBackupSourceForm.backupType"
              placeholder="请选择备份类型"
          >
            <el-option
                v-for="item in backupTypeOptions"
                :key="item.value"
                :label="item.label"
                :value="item.value"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="是 否 压 缩" :label-width="110">
          <el-radio-group v-model="addOrUpdateBackupSourceForm.isCompress" class="ml-4">
            <el-radio v-for="item in isCompressOptions" :label="item.value" size="default">{{ item.label }}</el-radio>
          </el-radio-group>
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
        <el-form-item label="备 份 类 型" :label-width="110">
          <el-select
              v-model="searchBackupSourceForm.backupType"
              class="m-2"
              placeholder="请选择备份类型"
          >
            <el-option
                v-for="item in backupTypeOptions"
                :key="item.value"
                :label="item.label"
                :value="item.value"
            />
          </el-select>
        </el-form-item>
      </el-form>
      <template #footer>
          <span class="dialog-footer">
            <el-button @click="searchBackupSourceDialogVisible = false">取 消</el-button>
            <el-button @click="clearSearch()">清 空</el-button>
            <el-button type="primary" @click="listBackupSource(true)">
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
import backupApi from "../api/backupApi.js";

const addOrUpdateBackupSourceForm = {
  backupType: 0,
  isCompress: 0
}

export default {
  components: {},
  data() {
    return {
      sourceList: [],
      // 增加或修改数据源
      addOrUpdateBackupSourceDialogVisible: false,
      addOrUpdateBackupSourceTitle: '',
      addOrUpdateBackupSourceForm: {},
      backupTypeOptions: [
        {
          value: 0,
          label: '数据源的数据全部备份到每个目标目录中',
        },
        {
          value: 1,
          label: '数据源的数据分散备份到多个目标目录中',
        }
      ],
      isCompressOptions: [
        {
          value: 0,
          label: '否',
        },
        {
          value: 1,
          label: '是',
        }
      ],
      // 查询数据源
      searchBackupSourceDialogVisible: false,
      searchBackupSourceForm: {},
      backupSourceCurrent: 1,
      backupSourceSize: 10,
      backupSourceTotal: 0,
      // 选择的数据源id数组
      selectBackupSourceIdArr: [],

      selectBackupSource: '',
      fileMessageSourceId: '',
    };
  },
  computed: {},
  watch: {},
  methods: {
    /**
     * 分页查询数据源
     */
    listBackupSource(isSearch) {
      this.searchBackupSourceForm.current = this.backupSourceCurrent;
      this.searchBackupSourceForm.size = this.backupSourceSize;
      backupSourceApi.list(this.searchBackupSourceForm).then(res => {
        // console.log(JSON.stringify(res))
        this.sourceList = res.data.records;
        this.backupSourceTotal = res.data.total;
        if (isSearch == true) {
          this.searchBackupSourceDialogVisible = false;
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
      this.searchBackupSourceDialogVisible = false;
      this.searchBackupSourceForm = {};
      this.listBackupSource(true);
    },
    addBackupSourceDialog() {
      this.addOrUpdateBackupSourceTitle = "增加数据源";
      this.addOrUpdateBackupSourceDialogVisible = true;
      this.addOrUpdateBackupSourceForm = {};
      this.addOrUpdateBackupSourceForm = addOrUpdateBackupSourceForm;
    },
    updateBackupSourceDialog() {
      if (this.selectBackupSourceIdArr.length == 1) {
        this.addOrUpdateBackupSourceTitle = "修改数据源";
        this.addOrUpdateBackupSourceDialogVisible = true;
        backupSourceApi.getById(this.selectBackupSourceIdArr[0]).then(res => {
          // console.log("根据id查询数据源：" + JSON.stringify(res));
          this.addOrUpdateBackupSourceForm = res.data;
        })
      } else if (this.selectBackupSourceIdArr.length > 1) {
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
    addOrUpdateBackupSource() {
      if (this.addOrUpdateBackupSourceForm.id) {
        backupSourceApi.update(this.addOrUpdateBackupSourceForm).then(res => {
              this.addOrUpdateBackupSourceDialogVisible = false;
              this.listBackupSource(false);
              // 情况表单
              this.addOrUpdateBackupSourceForm = {};
              ElMessage({
                message: "修改成功",
                type: 'success',
                duration: 2 * 1000
              })
            }
        )
      } else {
        // console.log("this.addOrUpdateBackupSourceForm:" + JSON.stringify(this.addOrUpdateBackupSourceForm))
        backupSourceApi.save(this.addOrUpdateBackupSourceForm).then(res => {
              this.addOrUpdateBackupSourceDialogVisible = false;
              this.listBackupSource(false);
              // 情况表单
              this.addOrUpdateBackupSourceForm = {};

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
    handleBackupSourceSelectionChange(val) {
      this.selectBackupSourceIdArr = [];
      val.forEach((item) => {
        this.selectBackupSourceIdArr.push(item.id);
      })
      // console.log("选中ID数组：" + JSON.stringify(this.selectBackupSourceIdArr));
    },
    /**
     * 删除选中的数据
     */
    removeByBackupSourceIds() {
      backupSourceApi.removeByIds(this.selectBackupSourceIdArr).then(res => {
        this.listBackupSource(false);
        this.changeSource([]);
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
    handleBackupSourceSizeChange(val) {
      this.backupSourceSize = val;
      this.listBackupSource(false);
    },
    /**
     * 分页页数改变
     */
    handleBackupSourceCurrentChange(val) {
      this.backupSourceCurrent = val;
      this.listBackupSource(false);
    },

    /**
     * 点击表格的一行触发
     * @param val 所点击行的数据
     */
    changeSource(val) {
      // console.log("changeSource,val:" + JSON.stringify(val));
      this.selectBackupSource = val;
      this.$emit("changeSourceChange", this.selectBackupSource);
    },

    /**
     * 格式化日期和时间
     */
    formatDate(row, column, cellValue, index) {
      let date = new Date(cellValue);
      return date.toLocaleDateString() + " " + date.toLocaleTimeString();
    },

    /**
     * 对指定的数据源进行备份
     */
    backupBySourceId() {
      if (this.selectBackupSourceIdArr.length > 0) {
        for (let i = 0; i < this.selectBackupSourceIdArr.length; i++) {
          backupApi.backupBySourceId(this.selectBackupSourceIdArr[i]).then(res => {
            ElMessage({
              message: "数据源：" + this.selectBackupSourceIdArr[i] + " 开始备份",
              type: 'success',
              duration: 2 * 1000
            })
          })
        }
      } else {
        ElMessage({
          message: "没有选中数据源，无法备份",
          type: 'error',
          duration: 2 * 1000
        })
      }
    },

    /**
     * 对指定的数据源删除无效数据
     */
    clearBySourceId() {
      if (this.selectBackupSourceIdArr.length > 0) {
        for (let i = 0; i < this.selectBackupSourceIdArr.length; i++) {
          backupApi.clearBySourceId(this.selectBackupSourceIdArr[i]).then(res => {
            ElMessage({
              message: "数据源：" + this.selectBackupSourceIdArr[i] + " 开始清理",
              type: 'success',
              duration: 2 * 1000
            })
          })
        }
      } else {
        ElMessage({
          message: "没有选中数据源，无法备份",
          type: 'error',
          duration: 2 * 1000
        })
      }
    },
    /**
     * 查看sourceId对应的文件结构
     * @param sourceId
     */
    seeFileMessage(sourceId) {
      console.log("seeFileMessage：" + sourceId);
      this.fileMessageSourceId = sourceId;
      this.$emit("fileMessageSourceChange", this.fileMessageSourceId);
    }
  },

  beforeCreate() {
  },

  created() {
    this.listBackupSource(false);
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

