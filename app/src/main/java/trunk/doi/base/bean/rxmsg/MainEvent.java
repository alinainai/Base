package trunk.doi.base.bean.rxmsg;

/**
 * 作者：Mr.Lee on 2017-1-5 10:11
 * 邮箱：569932357@qq.com
 */

public class MainEvent {


    public MainEvent(){}
    public MainEvent(int id, String name) {
        this.id = id;
        this.name = name;
    }

    private int id;
    private String name;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
