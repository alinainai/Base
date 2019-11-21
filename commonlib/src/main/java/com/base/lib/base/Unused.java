package com.base.lib.base;

import javax.inject.Inject;

/**
 *
 * google/dagger#955 https://github.com/google/dagger/issues/955
 *
 * 当一个中间类（BaseLazyLoadFragment）不需要inject操作的时候，dagger不会为它在base module中生成BaseLazyLoadFragment_MembersInjector，
 * 当其他依赖的子模块使用到BaseLazyLoadFragment时，没找到MembersInjector类，就在每个模块中自行生成了，最终在打包APP时，由于重复生成的MembersInjector类，会导致打包失败
 * 解决方案就如merge的代码一样，手动添加一个无用的inject字段，来触发dagger在编译时为base module生成对应的MembersInjector类，这样每个引用的模块中就不会重复生成
 *
 * 对于Dagger而言，如果中间类（BaseLazyLoadFragment）不需要inject操作，dagger不会知道它的存在，也就不会生成MembersInjector类，除非有上下文的引用
 * https://github.com/JessYanCoding/MVPArms/pull/341
 * Created by yexiaokang on 2019/11/12.
 */
public class Unused {

    @Inject
    public Unused() {

    }
}
