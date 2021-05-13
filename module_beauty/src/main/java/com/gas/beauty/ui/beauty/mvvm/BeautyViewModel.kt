package com.gas.beauty.ui.beauty.mvvm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.gas.beauty.bean.BeautyBean
import com.lib.commonsdk.mvvm.BaseViewModel
import kotlinx.coroutines.launch

class BeautyViewModel :BaseViewModel(){

    private val beautyModel = BeautyDataBiz()
    val beautiesLiveData = MutableLiveData<List<BeautyBean>>()

    fun getBeauties(){
        viewModelScope.launch {
            val list = try {
                beautyModel.getBeauties()
            }catch (e:Exception){
                null
            }
            if(list!=null && list.error){
                beautiesLiveData.postValue(list.results)
            }
        }

    }

}