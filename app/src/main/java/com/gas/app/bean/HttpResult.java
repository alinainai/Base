package com.gas.app.bean;

/**
 * 服务器返回值基类
 * Created by Jing on 2016/5/24.
 */
public class HttpResult<T> {


    private int code;//结果码
    private String msg;//原因
    private T results;//数据类型


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getResults() {
        return results;
    }

    public void setResults(T results) {
        this.results = results;
    }
}