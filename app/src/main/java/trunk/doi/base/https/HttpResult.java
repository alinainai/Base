package trunk.doi.base.https;

/**
 * 服务器返回值基类
 * Created by Jing on 2016/5/24.
 */
public class HttpResult<T> {




    private int code;//结果码
    private String msg;//原因
    private T results;//数据类型


    public HttpResult() {
    }

    public HttpResult(int code, String msg, T results) {
        this.code = code;
        this.msg = msg;
        this.results = results;
    }

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

    public void setResults  (T data) {
        this.results = data;
    }

    @Override
    public String toString() {
        return "HttpResult{" +
                ", code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", data=" + results.toString() +
                '}';
    }
}