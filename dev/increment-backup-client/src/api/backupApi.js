import request from '../utils/axiosRequest.js'

const apiName = '/backup'

export default {

    /**
     * 对指定的数据源进行备份
     * @param id
     * @returns {*}
     */
    backupBySourceId(id) {
        return request({
            url: `${apiName}/backupBySourceId/${id}`,
            method: "get"
        })
    },

}