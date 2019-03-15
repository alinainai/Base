package trunk.doi.base.base.mvp;


/**
 *
 */
public interface IBaseView {
    void onError();
    default void showLoadView(){}
}
