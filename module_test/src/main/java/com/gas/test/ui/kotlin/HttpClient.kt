package com.gas.test.ui.kotlin

import com.google.gson.Gson
import okhttp3.*
import java.io.IOException
import java.lang.reflect.Type

object HttpClient : OkHttpClient() {

    private val gson = Gson()
    fun <T> convert(json: String?, type: Type): T {
        return gson.fromJson(json, type)
    }

    fun <T> get(path: String, type: Type, entityCallback: EntityCallback<T>) {

        val request = Request.Builder()
                .url("https://api.hencoder.com/${path}")
                .build()
        val call: Call = this.newCall(request)

        call.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                entityCallback.onFailure("网络异常")
            }

            override fun onResponse(call: Call, response: Response) {



//                        try {
//                            json=body!!.string()
//                        }catch (e:IOException){
//                            e.printStackTrace()
//                        }


                //条件判断是支持表达式的
                when (response.code) {
                    //kotlin没有收集异常的机制
                    in 200..299 -> entityCallback.onSuccess(convert(response.body!!.string(), type) as T)
                    in 400..499 -> entityCallback.onFailure("客户端错误")
                    in 500..599 -> entityCallback.onFailure("服务器异常")
                    else -> entityCallback.onFailure("未知错误")
                }
            }


        })

    }


}