import request from '../utils/axiosRequest.js'

const apiName = '/sysPram'

export default {

    /**
     * 根据参数名称获取参数值
     * @param paramName
     * @returns {*}
     */
    getByParamName(paramName) {
        return request({
            url: `${apiName}/getByParamName/${paramName}`,
            method: "get"
        })
    },

    /**
     * 对指定的数据源删除无效数据
     * @returns {*}
     */
    updateIgnore(ignoreParam) {
        return request({
            url: `${apiName}/updateIgnore`,
            method: "post",
            data: ignoreParam
        })
    }

}