package trunk.doi.base.base.mvp;


import com.trello.rxlifecycle2.LifecycleTransformer;

/**
 *
 */
public interface IBaseView {
    void onError();
    LifecycleTransformer bindLifecycle();
}
