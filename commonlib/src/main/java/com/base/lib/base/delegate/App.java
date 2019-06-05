package com.base.lib.base.delegate;

import androidx.annotation.NonNull;

import com.base.lib.di.component.AppComponent;

/**
 * ================================================
 * 框架要求框架中的每个 {@link android.app.Application} 都需要实现此类, 以满足规范
 * ================================================
 */
public interface App {
    @NonNull
    AppComponent getAppComponent();
}
