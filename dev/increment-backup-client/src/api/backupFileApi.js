import request from '../utils/axiosRequest.js'

const apiName = '/file'

export default {

    /**
     * 条件分页查询
     * @param data
     * @returns {*}
     */
    list(data) {
        return request({
            url: `${apiName}/list`,
            method: "post",
            data: data
        })
    },

    /**
     * 查询正在处理的任务
     * @returns {*}
     */
    listProcessingFile(){
        return request({
            url: `${apiName}/listProcessingFile`,
            method: "get"
        })
    },

    /**
     * 根据ID数组批量删除
     * @param ids
     * @returns {*}
     */
    removeByIds(ids) {
        return request({
            url: `${apiName}/removeByIds`,
            method: "post",
            data: ids
        })
    },

    /**
     * 根据ID从数据库中获取数据
     * @param id
     * @returns {*}
     */
    getById(id) {
        return request({
            url: `${apiName}/getById/${id}`,
            method: "get"
        })
    },

    /**
     * 增加数据
     * @param data
     * @returns {*}
     */
    save(data) {
        console.log("保存数据源：" + JSON.stringify(data));
        return request({
            url: `${apiName}/save`,
            method: "post",
            data: data
        })
    },

    /**
     * 修改数据
     * @param data
     * @returns {*}
     */
    update(data) {
        return request({
            url: `${apiName}/update`,
            method: "post",
            data: data
        })
    },

    /**
     * 根据数据源ID和父ID查询出所有 fileMessage 数据
     * @param sourceId
     * @param fatherId
     * @returns {*}
     */
    listChildrenBySourceIdAndFatherId(sourceId, fatherId) {
        let requestParam = {
            sourceId: sourceId,
            fatherId: fatherId
        }
        return request({
            url: `${apiName}/listChildrenBySourceIdAndFatherId`,
            method: "get",
            params: requestParam
        })
    },

}