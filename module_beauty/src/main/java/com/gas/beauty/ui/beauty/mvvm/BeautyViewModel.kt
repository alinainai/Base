package com.gas.beauty.ui.beauty.mvvm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.gas.beauty.bean.BeautyBean
import com.lib.commonsdk.mvvm.BaseViewModel
import kotlinx.coroutines.launch

class BeautyViewModel : BaseViewModel() {

    private val beautyModel = BeautyDataBiz()
    private val _beautiesLiveData = MutableLiveData<List<BeautyBean>>()
    val beautiesLiveData: LiveData<List<BeautyBean>>
        get() = _beautiesLiveData

    fun getBeauties() {
        viewModelScope.launch {
            val list = try {
                beautyModel.getBeauties()
            } catch (e: Exception) {
                null
            }
            if (list != null && !list.error && list.results != null) {
                _beautiesLiveData.postValue(list.results!!)
            }
        }

    }

}