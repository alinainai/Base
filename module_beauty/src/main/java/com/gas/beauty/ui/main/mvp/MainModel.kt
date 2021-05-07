package com.gas.beauty.ui.main.mvp

import com.base.lib.di.scope.ActivityScope
import com.base.lib.integration.repository.IRepositoryManager
import com.base.lib.mvp.BaseModel
import com.gas.beauty.bean.GankBaseResponse
import com.gas.beauty.bean.GankItemBean
import com.gas.beauty.http.GankService
import io.reactivex.Observable
import javax.inject.Inject

@ActivityScope
class MainModel @Inject constructor(repositoryManager: IRepositoryManager) : BaseModel(repositoryManager), MainContract.Model {
    override fun getGirlList(num: Int, page: Int): Observable<GankBaseResponse<List<GankItemBean>>> {
        return mRepositoryManager.obtainRetrofitService(GankService::class.java)
                .getGirlList(num, page)
    }
}