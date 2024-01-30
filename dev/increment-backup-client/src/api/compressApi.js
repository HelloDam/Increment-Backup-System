import request from '../utils/axiosRequest.js'

const apiName = '/compress'

export default {

    /**
     * 根据ID对指定的fileMessage进行解压操作，如果是目录，则递归解压其所有子文件
     * @param id
     * @returns {*}
     */
    unCompress(id) {
        return request({
            url: `${apiName}/unCompress/${id}`,
            method: "get"
        })
    },

}