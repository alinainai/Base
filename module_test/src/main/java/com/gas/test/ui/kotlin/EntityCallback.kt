package com.gas.test.ui.kotlin

interface EntityCallback<T> {

    fun onSuccess(entity: T)

    fun onFailure(message: String?)

}