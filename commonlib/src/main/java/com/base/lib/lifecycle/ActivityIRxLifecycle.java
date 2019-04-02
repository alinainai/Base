package com.base.lib.lifecycle;

import android.app.Activity;

import com.base.lib.rx.RxBindManager;
import com.trello.rxlifecycle2.RxLifecycle;
import com.trello.rxlifecycle2.android.ActivityEvent;

/**
 * ================================================
 * 让 {@link Activity} 实现此接口,即可正常使用 {@link RxLifecycle}
 * <p>
 * 参考 JessYan MvpArms 实现 使用 rxlifecycle2 对 RxJava2进行生命周期解绑
 * 具体方式参考 {@link RxBindManager}
 * ================================================
 */
public interface ActivityIRxLifecycle extends IRxLifecycle<ActivityEvent> {
}
