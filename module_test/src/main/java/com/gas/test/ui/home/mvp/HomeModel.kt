package com.gas.test.ui.home.mvp

import com.base.lib.di.scope.ActivityScope
import com.base.lib.integration.repository.IRepositoryManager
import com.base.lib.mvp.BaseModel

import javax.inject.Inject

@ActivityScope
class HomeModel @Inject
constructor(repositoryManager: IRepositoryManager) : BaseModel(repositoryManager), HomeContract.Model
