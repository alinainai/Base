package com.gas.test.utils

interface RegionBusiness {
    companion object {
        @JvmField
        val ins = RegionBusinessImp()
    }

    fun log();
}