package com.gas.app.learn.seal

sealed class NetworkState {

    object Loading : NetworkState()
    object Success : NetworkState()

    data class Error(
            val code: Int? = null,
            val heading: Int? = null,
            val message: Int? = null
    ) : NetworkState()

    var optionCallback: ((pos: Int) -> Unit)? = null
    var optionCallbackNoParam: (() -> Unit)? = null

}