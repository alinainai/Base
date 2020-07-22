package com.gas.app.bean

/**
 * 服务器返回值基类
 * Created by Jing on 2016/5/24.
 */
class HttpResult<T> {
    var code //结果码
            = 0
    var msg //原因
            : String? = null
    var results //数据类型
            : T? = null
        private set

    fun setResults(results: T) {
        this.results = results
    }
}