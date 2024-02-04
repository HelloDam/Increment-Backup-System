<template>
  <div>
    <el-row :gutter="20">
      <el-col :span="12">
        <div class="tableTitle">
          <div>
            忽略文件设置
          </div>
          <div style="display: flex;justify-content: center">
            <el-input v-model="ignoreFileInput" placeholder="输入忽略文件名" size="small"
                      style="margin-right: 10px"/>
            <CirclePlus style="width: 1.3em; height: 1.3em; margin-right: 8px;color:#0368c5;"
                        @click="addIgnoreFile()"/>
            <Delete style="width: 1.3em; height: 1.3em; margin-right: 8px;color:#f54248"
                    @click="deleteIgnoreFile()"/>
          </div>
        </div>
        <el-table :data="ignoreFileList" border @selection-change="handleIgnoreFileSelectionChange">
          <el-table-column type="selection" width="55"  align="center"/>
          <el-table-column prop="value" label="文件名" :show-overflow-tooltip="true"/>
        </el-table>
      </el-col>
      <el-col :span="12">
        <div class="tableTitle">
          <div>
            忽略目录设置
          </div>
          <div style="display: flex;justify-content: center">
            <el-input v-model="ignoreDirectoryInput" placeholder="输入忽略目录名" size="small"
                      style="margin-right: 10px"/>
            <CirclePlus style="width: 1.3em; height: 1.3em; margin-right: 8px;color:#0368c5;"
                        @click="addIgnoreDirectory()"/>
            <Delete style="width: 1.3em; height: 1.3em; margin-right: 8px;color:#f54248"
                    @click="deleteIgnoreDirectory()"/>
          </div>
        </div>
        <el-table :data="ignoreDirectoryList" border @selection-change="handleIgnoreDirectorySelectionChange">
          <el-table-column type="selection" width="55" align="center"/>
          <el-table-column prop="value" label="目录名" :show-overflow-tooltip="true"/>
        </el-table>
      </el-col>
    </el-row>
    <div class="footer">
      <el-button @click="closeSysParam()">取 消</el-button>
      <el-button @click="saveSysParam()" type="primary">保 存</el-button>
    </div>
  </div>
</template>

<script>
import sysParamApi from "../api/sysParamApi.js";
import {ElMessage} from "element-plus";

export default {
  components: {},
  data() {
    return {
      // 系统参数修改
      ignoreFileList: [],
      ignoreDirectoryList: [],
      ignoreFileInput: '',
      ignoreDirectoryInput: '',
      selectIgnoreFileList: [],
      selectIgnoreDirectoryList: [],
    };
  },
  computed: {},
  watch: {},
  methods: {
    /**
     * 添加忽略文件名
     */
    addIgnoreFile() {
      console.log("添加忽略文件名");
      if ("" === this.ignoreFileInput) {
        ElMessage({
          message: "文件名不能为空",
          type: 'error',
          duration: 2 * 1000
        })
        return;
      }
      for (let i = 0; i < this.ignoreFileList.length; i++) {
        if (this.ignoreFileInput === this.ignoreFileList[i].value) {
          ElMessage({
            message: "文件名已经存在",
            type: 'error',
            duration: 2 * 1000
          })
          return;
        }
      }
      this.ignoreFileList.push({id: this.ignoreFileList.length, value: this.ignoreFileInput});
      this.ignoreFileInput = '';
    },
    handleIgnoreFileSelectionChange(val) {
      this.selectIgnoreFileList = val;
      // console.log("val:" + JSON.stringify(val))
    },
    deleteIgnoreFile() {
      this.ignoreFileList = this.ignoreFileList.filter(item => !this.selectIgnoreFileList.includes(item));
    },
    /**
     * 添加忽略目录名
     */
    addIgnoreDirectory() {
      console.log("添加忽略文件名");
      if ("" === this.ignoreDirectoryInput) {
        ElMessage({
          message: "文件名不能为空",
          type: 'error',
          duration: 2 * 1000
        })
        return;
      }
      for (let i = 0; i < this.ignoreDirectoryList.length; i++) {
        if (this.ignoreDirectoryInput === this.ignoreDirectoryList[i].value) {
          ElMessage({
            message: "文件名已经存在",
            type: 'error',
            duration: 2 * 1000
          })
          return;
        }
      }
      this.ignoreDirectoryList.push({id: this.ignoreDirectoryList.length, value: this.ignoreDirectoryInput});
      this.ignoreDirectoryInput = '';
    },
    handleIgnoreDirectorySelectionChange(val) {
      this.selectIgnoreDirectoryList = val;
      // console.log("val:" + JSON.stringify(val))
    },
    deleteIgnoreDirectory() {
      this.ignoreDirectoryList = this.ignoreDirectoryList.filter(item => !this.selectIgnoreDirectoryList.includes(item));
    },
    saveSysParam() {
      let ignoreFileParam = {
        paramName: "ignore_file_name",
        ignoreParamList: this.ignoreFileList
      };
      sysParamApi.updateIgnore(ignoreFileParam).then(res => {
        let ignoreDirectoryParam = {
          paramName: "ignore_directory_name",
          ignoreParamList: this.ignoreDirectoryList
        };
        sysParamApi.updateIgnore(ignoreDirectoryParam).then(res1 => {
          ElMessage({
            message: "保存成功",
            type: 'success',
            duration: 2 * 1000
          })
        })
      })
      this.$emit("closeSysParamDialog");
    },
    closeSysParam() {
      this.$emit("closeSysParamDialog");
    },
  },
  beforeCreate() {
  },

  created() {
    sysParamApi.getByParamName("ignore_file_name").then(res => {
      this.ignoreFileList = res.data;
      console.log("this.ignoreFileList:" + JSON.stringify(this.ignoreFileList));
    })
    sysParamApi.getByParamName("ignore_directory_name").then(res => {
      this.ignoreDirectoryList = res.data;
    })
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
  color: #1a1a1a;

  display: flex;
  justify-content: space-between;
}

.footer {
  //background: #069ad2;
  display: flex;
  justify-content: flex-end;
  align-items: center;
}
</style>

