package com.gas.app.ui.fragment.product.di

import com.base.lib.di.component.AppComponent
import com.base.lib.di.scope.FragmentScope
import com.gas.app.ui.fragment.product.ProductFragment
import com.gas.app.ui.fragment.product.mvp.ProductContract
import dagger.BindsInstance
import dagger.Component

/**
 * ================================================
 * Description:
 *
 *
 * Created by GasMvpFragment on 11/30/2019 14:55
 * ================================================
 */
@FragmentScope
@Component(modules = [ProductModule::class], dependencies = [AppComponent::class])
interface ProductComponent {
    fun inject(fragment: ProductFragment)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun view(view: ProductContract.View): Builder
        fun appComponent(appComponent: AppComponent): Builder
        fun build(): ProductComponent
    }
}