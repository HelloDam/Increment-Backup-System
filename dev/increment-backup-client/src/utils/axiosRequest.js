import axios from 'axios'
import {ElMessage} from "element-plus";

//获取配置文件 .env.development 的数据
const BASE_URL = "http://localhost:8899";
const REQUEST_TIMEOUT = 100;

// create an axios instance
const service = axios.create({
    baseURL: BASE_URL, // url = base url + request url
    // withCredentials: true, // send cookies when cross-domain requests
    timeout: REQUEST_TIMEOUT // request timeout
})

// 请求拦截器
service.interceptors.request.use(
    config => {
        return config
    },
    error => {
        console.log(error)
        return Promise.reject(error)
    }
)

// 相应拦截器
service.interceptors.response.use(
    response => {
        const res = response.data
        // alert("res：" + JSON.stringify(res))

        if (res.code !== '0') {
            ElMessage({
                message: res.message || '请求出错了',
                type: 'error',
                duration: 5 * 1000
            })

            return Promise.reject(new Error(res.message || '请求出错了'))
        } else {
            // return res
            return Promise.resolve(res)
        }
    },
    error => {
        // alert("error：" + JSON.stringify(error))
        // console.log('err' + error)
        ElMessage({
            message: error.message,
            type: 'error',
            duration: 5 * 1000
        })
        return Promise.reject(error)
    }
)

export default service
export {BASE_URL}
