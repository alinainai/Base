package com.gas.test.utils

import com.lib.commonsdk.extension.app.debug

class RegionBusinessImp:RegionBusiness {

    private val num = IntArray(80*1000)

    override fun log() {
        debug("region baby")
    }
}