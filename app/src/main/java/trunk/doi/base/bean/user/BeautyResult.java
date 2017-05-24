package trunk.doi.base.bean.user;

/**
 * 作者：Mr.Lee on 2016-11-9 15:40
 * 邮箱：569932357@qq.com
 */

public class BeautyResult<T> {

    private boolean error;
    private  T results;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public T getResults() {
        return results;
    }

    public void setResults(T results) {
        this.results = results;
    }
}
