package com.gas.beauty.data

import com.base.lib.integration.repository.IRepositoryManager
import com.base.lib.util.ArmsUtils
import com.lib.commonsdk.kotlin.utils.AppUtils

open class BaseRemoteDataSource {
    protected val retrofitManager : IRepositoryManager = ArmsUtils.obtainAppComponentFromContext(AppUtils.app).repositoryManager()
}