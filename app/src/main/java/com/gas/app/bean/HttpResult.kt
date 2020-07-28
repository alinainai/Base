package com.gas.app.bean

/**
 * 服务器返回值基类
 */
class HttpResult<T> {
    var code = 0 //结果码
    var msg: String = ""  //原因
    var results: T? = null  //数据类型
        private set

    fun setResults(results: T) {
        this.results = results
    }
}