import request from '../utils/axiosRequest.js'

const apiName = '/source'

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
    }
}