<template>
  <div>
    <div class="tableTitle">
      <div>
        数据源 {{ fileMessageSourceId }} 对应文件结构
      </div>
      <div>
        <el-button size="small" style="border-radius: 5px" @click="expandTree()">
          <Expand style="width: 1.3em; height: 1.3em; margin-right: 8px;color:#0368c5;"/>
          展开树
        </el-button>
        <el-button size="small" style="border-radius: 5px" @click="foldTree()">
          <Fold style="width: 1.3em; height: 1.3em; margin-right: 8px;color:#8a4f03;"/>
          收起树
        </el-button>
      </div>
    </div>
    <div class="table">
      <el-tree :data="fileMessageTreeData" :props="defaultProps" @node-click="handleNodeClick" node-key="id" ref="tree"
               style="padding: 20px 0px;" highlight-current>
        <template #default="{ node, data }">
          <div style="width: 100%;display: flex;align-content: center; justify-content: space-between">
            <div style="display: flex;align-content: center;">
              <Folder style="width: 1.3em; height: 1.3em; margin-right: 8px;color:#0d7df8;"
                      v-if="data.fileType===0&&data.isContainFile===0"/>
              <FolderChecked style="width: 1.3em; height: 1.3em; margin-right: 8px;color:#0d7df8;"
                             v-if="data.fileType===0&&data.isContainFile===1"/>
              <Document style="width: 1.3em; height: 1.3em; margin-right: 8px;color:#059b4b;" v-if="data.fileType===1"/>
              <el-tag effect="dark" size="small" style="font-weight: bold" v-if="data.fileType===0">
                {{ node.label }}
              </el-tag>
              <el-tag type="success" effect="dark" size="small" style="font-weight: bold" v-if="data.fileType===1">
                {{ node.label }}
              </el-tag>
              <el-tag size="small" style="margin-left: 10px" v-if="data.fileType===1">
                {{ data.targetFilePath }}
              </el-tag>
              <!--文件后缀-->
              <el-tag type="info" size="small" style="margin-left: 10px" v-if="data.fileType===1">
                {{ data.fileSuffix }}
              </el-tag>

              <!--源文件大小-->
              <el-tag type="danger" size="small" style="margin-left: 10px"
                      v-if="data.fileType===1 && data.fileLength>1024*1024*1024">
                源文件大小：{{ Number(data.fileLength / (1024 * 1024 * 1024)).toFixed(2) }} gb
              </el-tag>
              <el-tag type="warning" size="small" style="margin-left: 10px"
                      v-else-if="data.fileType===1 && data.fileLength>1024*1024">
                源文件大小：{{ Number(data.fileLength / (1024 * 1024)).toFixed(2) }} mb
              </el-tag>
              <el-tag type="success" size="small" style="margin-left: 10px"
                      v-else-if="data.fileType===1&&data.fileLength>1024">
                源文件大小：{{ Number(data.fileLength / (1024)).toFixed(2) }} kb
              </el-tag>
              <el-tag size="small" style="margin-left: 10px" v-else-if="data.fileType===1&&data.fileLength>=0">
                源文件大小：{{ data.fileLength }} byte
              </el-tag>

              <!--压缩文件大小-->
              <el-tag type="danger" size="small" style="margin-left: 10px"
                      v-if="data.fileType===1&&data.isCompress===1 && data.fileLength>1024*1024*1024">
                压缩后大小：{{ Number(data.fileLengthAfterCompress / (1024 * 1024 * 1024)).toFixed(2) }} gb
              </el-tag>
              <el-tag type="warning" size="small" style="margin-left: 10px"
                      v-else-if="data.fileType===1&&data.isCompress===1 && data.fileLength>1024*1024">
                压缩后大小：{{ Number(data.fileLengthAfterCompress / (1024 * 1024)).toFixed(2) }} mb
              </el-tag>
              <el-tag type="success" size="small" style="margin-left: 10px"
                      v-else-if="data.fileType===1&&data.isCompress===1&&data.fileLength>1024">
                压缩后大小：{{ Number(data.fileLengthAfterCompress / (1024)).toFixed(2) }} kb
              </el-tag>
              <el-tag size="small" style="margin-left: 10px"
                      v-else-if="data.fileType===1&&data.isCompress===1&&data.fileLength>=0">
                压缩后大小：{{ data.fileLengthAfterCompress }} byte
              </el-tag>
            </div>

            <div style="float: right;margin-right: 30px">
              <el-button type="primary" plain @click="compress(data)" size="small" v-if="data.isCompress===1">解压
              </el-button>
            </div>
          </div>
        </template>
      </el-tree>
    </div>
  </div>
</template>

<script>

import fileMessageApi from "../api/fileMessageApi.js";
import compressApi from "../api/compressApi.js";
import {Expand} from "@element-plus/icons-vue";
import {ElMessage} from "element-plus";

export default {
  components: {Expand},
  props: {
    fileMessageSourceId: '',
  },
  data() {
    return {
      fileMessageTreeData: [],
      defaultProps: {
        children: 'children',
        label: 'fileName',
        fileType: 'fileType',
        fileLength: 'fileLength',
        isContainFile: 'isContainFile',
      }
    };
  },
  computed: {},
  watch: {},
  methods: {

    /**
     * 格式化日期和时间
     */
    formatDate(row, column, cellValue, index) {
      let date = new Date(cellValue);
      return date.toLocaleDateString() + " " + date.toLocaleTimeString();
    },

    listFileMessageChildren() {
      fileMessageApi.listChildrenBySourceIdAndFatherId(this.fileMessageSourceId, 0).then(res => {
        this.fileMessageTreeData = res.data;
        // console.log("this.fileMessageTreeData:" + JSON.stringify(this.fileMessageTreeData));
      })
    },

    /**
     * 点击树的节点
     */
    handleNodeClick(node) {
      console.log("node:" + JSON.stringify(node));
      fileMessageApi.listChildrenBySourceIdAndFatherId(this.fileMessageSourceId, node.id).then(res => {
        node.children = res.data;
        this.$refs.tree.store.nodesMap[node.id].expanded = true
        // console.log("this.fileMessageTreeData:" + JSON.stringify(this.fileMessageTreeData));
      })
    },

    /**
     * 解压文件
     */
    compress(fileMessage) {
      compressApi.unCompress(fileMessage.id).then(res => {
        ElMessage({
          message: "解压成功，解压后的文件和解压前的文件处于同一目录",
          type: 'success',
          duration: 2 * 1000
        })
      })
    },

    /**
     * 展开树
     */
    expandTree() {
      for (let key in this.$refs.tree.store.nodesMap) {
        this.$refs.tree.store.nodesMap[key].expanded = true
      }
    },
    // 收起按钮
    foldTree() {
      for (let key in this.$refs.tree.store.nodesMap) {
        this.$refs.tree.store.nodesMap[key].expanded = false
      }
    }

  },
  beforeCreate() {
  },

  created() {
    this.listFileMessageChildren();
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

