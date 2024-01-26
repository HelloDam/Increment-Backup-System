<template>
    <div>
        <div class="tableTitle">
            <div>
                数据源目录管理
            </div>
            <div>
<!--                <CirclePlus style="width: 1.3em; height: 1.3em; margin-right: 8px;color:#0368c5;"-->
<!--                            @click="addBackupTaskDialog()"/>-->
                <Delete style="width: 1.3em; height: 1.3em; margin-right: 8px;color:#f54248"
                        @click="removeByBackupTaskIds"/>
<!--                <Edit style="width: 1.3em; height: 1.3em; margin-right: 8px;color:#069a1e"-->
<!--                      @click="updateBackupTaskDialog()"/>-->
                <Search style="width: 1.3em; height: 1.3em; margin-right: 8px;color:#8a4f03"
                        @click="searchBackupTaskDialogVisible=true"/>
            </div>
        </div>
        <div class="table">
            <el-table :data="taskList" @selection-change="handleBackupTaskSelectionChange" @select="changeTask" border>
                <el-table-column type="selection" width="55"/>
                <el-table-column prop="id" label="编号" width="100" resizable :show-overflow-tooltip="true"/>
                <el-table-column prop="backupSourceRoot" label="备份数据源根目录" width="200" resizable
                                 :show-overflow-tooltip="true"/>
                <el-table-column prop="backupTargetRoot" label="备份目标根目录" width="200" resizable
                                 :show-overflow-tooltip="true"/>
                <el-table-column prop="backupProgress" label="备份进度" width="200"  resizable:show-overflow-tooltip="true">
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
                <el-table-column prop="backupStatus" label="状态" width="100" resizable :show-overflow-tooltip="true">
                    <template #default="scope">
                        <el-tag class="ml-2" v-if="scope.row.backupStatus==0">刚创建</el-tag>
                        <el-tag class="ml-2" type="warning" v-if="scope.row.backupStatus==1">进行中</el-tag>
                        <el-tag class="ml-2" type="success" v-if="scope.row.backupStatus==2">完 成</el-tag>
                        <el-tag class="ml-2" type="danger" v-if="scope.row.backupStatus==3">失 败</el-tag>
                    </template>
                </el-table-column>
                <el-table-column prop="totalFileNum" label="已备份文件数 / 总文件数" width="200" resizable
                                 :show-overflow-tooltip="true">
                    <template #default="scope">
                        <span>{{ scope.row.finishFileNum }} / {{ scope.row.totalFileNum }}</span>
                    </template>
                </el-table-column>ß
                <el-table-column prop="totalByteNum" label="已备份字节数 / 总字节数" width="300" resizable
                                 :show-overflow-tooltip="true">
                    <template #default="scope">
                        <span>{{ scope.row.finishByteNum }} / {{ scope.row.totalByteNum }}</span>
                    </template>
                </el-table-column>
                <el-table-column prop="createTime" label="创建时间" width="180" resizable :show-overflow-tooltip="true"
                                 :formatter="formatDate"/>
                <el-table-column prop="updateTime" label="修改时间" width="180" resizable :show-overflow-tooltip="true"
                                 :formatter="formatDate"/>
            </el-table>
            <div style="padding: 10px">
                <el-pagination background layout="total, sizes, prev, pager, next, jumper"
                               :page-sizes="[10, 20, 30, 40]"
                               :total=backupTaskTotal
                               v-model:current-page="backupTaskCurrent"
                               v-model:page-size="backupTaskSize"
                               small="small"
                               @size-change="handleBackTaskSizeChange"
                               @current-change="handleBackTaskCurrentChange"/>
            </div>
        </div>
        <!-- 添加或修改数据源 -->
        <el-dialog
                v-model="addOrUpdateBackupTaskDialogVisible"
                :title=addOrUpdateBackupTaskTitle
                width="30%"
        >
            <el-form :model="addOrUpdateBackupTaskForm">
                <el-form-item label="数据源根目录" :label-width="110">
                    <el-input v-model="addOrUpdateBackupTaskForm.rootPath" autocomplete="off"/>
                </el-form-item>
                <el-form-item label="基 本 介 绍" :label-width="110">
                    <el-input v-model="addOrUpdateBackupTaskForm.backupName" autocomplete="off"/>
                </el-form-item>
            </el-form>
            <template #footer>
          <span class="dialog-footer">
            <el-button @click="addOrUpdateBackupTaskDialogVisible = false">取 消</el-button>
            <el-button type="primary" @click="addOrUpdateBackupTask">
              确 定
            </el-button>
          </span>
            </template>
        </el-dialog>
        <!-- 查询数据源 -->
        <el-dialog
                v-model="searchBackupTaskDialogVisible"
                title="请输入要查询的字段，不需要查询的可以空着"
                width="30%"
        >
            <el-form :model="searchBackupTaskForm">
                <el-form-item label="数据源根目录" :label-width="110">
                    <el-input v-model="searchBackupTaskForm.rootPath" autocomplete="off"/>
                </el-form-item>
                <el-form-item label="基 本 介 绍" :label-width="110">
                    <el-input v-model="searchBackupTaskForm.backupName" autocomplete="off"/>
                </el-form-item>
            </el-form>
            <template #footer>
          <span class="dialog-footer">
            <el-button @click="searchBackupTaskDialogVisible = false">取 消</el-button>
            <el-button type="primary" @click="listBackupTask(true)">
              查 询
            </el-button>
          </span>
            </template>
        </el-dialog>
    </div>
</template>

<script>

import backupTaskApi from "../api/backupTaskApi.js";
import backupTargetApi from "../api/backupTargetApi.js";
import {ElMessage} from "element-plus";
import backupApi from "../api/backupApi.js";

export default {
    components: {},
    data() {
        return {
            taskList: [],
            // 增加或修改数据源
            addOrUpdateBackupTaskDialogVisible: false,
            addOrUpdateBackupTaskTitle: '',
            addOrUpdateBackupTaskForm: {},
            // 查询数据源
            searchBackupTaskDialogVisible: false,
            searchBackupTaskForm: {},
            backupTaskCurrent: 1,
            backupTaskSize: 10,
            backupTaskTotal: 0,
            // 选择的数据源id数组
            selectBackupTaskIdArr: [],

            selectBackupTask: '',
        };
    },
    computed: {},
    watch: {},
    methods: {
        /**
         * 分页查询数据源
         */
        listBackupTask(isSearch) {
            this.searchBackupTaskForm.current = this.backupTaskCurrent;
            this.searchBackupTaskForm.size = this.backupTaskSize;
            backupTaskApi.list(this.searchBackupTaskForm).then(res => {
                // console.log(JSON.stringify(res))
                this.taskList = res.data.records;
                this.backupTaskTotal = res.data.total;
                if (isSearch == true) {
                    this.searchBackupTaskDialogVisible = false;
                    this.searchBackupTaskForm = [];
                    ElMessage({
                        message: "查询成功",
                        type: 'success',
                        duration: 5 * 1000
                    })
                }
            })
        },
        addBackupTaskDialog() {
            this.addOrUpdateBackupTaskTitle = "增加数据源";
            this.addOrUpdateBackupTaskDialogVisible = true;
            this.addOrUpdateBackupTaskForm = {};
        },
        updateBackupTaskDialog() {
            if (this.selectBackupTaskIdArr.length == 1) {
                this.addOrUpdateBackupTaskTitle = "修改数据源";
                this.addOrUpdateBackupTaskDialogVisible = true;
                backupTaskApi.getById(this.selectBackupTaskIdArr[0]).then(res => {
                    // console.log("根据id查询数据源：" + JSON.stringify(res));
                    this.addOrUpdateBackupTaskForm = res.data;
                })
            } else if (this.selectBackupTaskIdArr.length > 1) {
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
        addOrUpdateBackupTask() {
            if (this.addOrUpdateBackupTaskForm.id) {
                backupTaskApi.update(this.addOrUpdateBackupTaskForm).then(res => {
                        this.addOrUpdateBackupTaskDialogVisible = false;
                        this.listBackupTask(false);
                        // 情况表单
                        this.addOrUpdateBackupTaskForm = {};
                        ElMessage({
                            message: "修改成功",
                            type: 'success',
                            duration: 5 * 1000
                        })
                    }
                )
            } else {
                // console.log("this.addOrUpdateBackupTaskForm:" + JSON.stringify(this.addOrUpdateBackupTaskForm))
                backupTaskApi.save(this.addOrUpdateBackupTaskForm).then(res => {
                        this.addOrUpdateBackupTaskDialogVisible = false;
                        this.listBackupTask(false);
                        // 情况表单
                        this.addOrUpdateBackupTaskForm = {};

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
        handleBackupTaskSelectionChange(val) {
            this.selectBackupTaskIdArr = [];
            val.forEach((item) => {
                this.selectBackupTaskIdArr.push(item.id);
            })
            // console.log("选中ID数组：" + JSON.stringify(this.selectBackupTaskIdArr));
        },
        /**
         * 删除选中的数据
         */
        removeByBackupTaskIds() {
            backupTaskApi.removeByIds(this.selectBackupTaskIdArr).then(res => {
                this.listBackupTask(false);
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
        handleBackTaskSizeChange(val) {
            this.backupTaskSize = val;
            this.listBackupTask(false);
        },
        /**
         * 分页页数改变
         */
        handleBackTaskCurrentChange(val) {
            this.backupTaskCurrent = val;
            this.listBackupTask(false);
        },

        /**
         * 点击表格的一行触发
         * @param val 所点击行的数据
         */
        changeTask(val) {
            this.selectBackupTask = val;
            this.$emit("changeTaskChange", this.selectBackupTask);
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
        this.listBackupTask(false);
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

