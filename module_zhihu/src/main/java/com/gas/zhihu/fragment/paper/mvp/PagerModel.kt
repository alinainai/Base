package com.gas.zhihu.fragment.paper.mvp

import com.base.lib.integration.repository.IRepositoryManager
import com.base.lib.mvp.BaseModel

import com.base.lib.di.scope.FragmentScope;
import com.gas.zhihu.bean.PaperBean
import javax.inject.Inject

import com.gas.zhihu.utils.PagerBeanDbUtils


/**
 * ================================================
 * Description:
 * <p>
 * Created by GasMvpFragment on 05/17/2020 10:01
 * ================================================
 */
@FragmentScope
class PagerModel
@Inject
constructor(repositoryManager: IRepositoryManager) : BaseModel(repositoryManager), PagerContract.Model {



//    fun getPapers(): List<PaperBean>{
//        return PagerBeanDbUtils.queryAllPaperData()
//    }
//
//    fun getExperiences(): List<ExperienceBean>{
//        return ExperienceBeanDbUtils.queryAllExperienceData()
//    }

    override fun onDestroy() {
        super.onDestroy()
    }
}
