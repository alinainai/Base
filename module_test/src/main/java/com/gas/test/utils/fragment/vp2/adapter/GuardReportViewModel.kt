package com.gas.test.utils.fragment.vp2.adapter

open class AbsGuardReportViewModel(val holderType: Int) {
    open var isRefresh: Boolean = true
}

class GuardReportViewModel : AbsGuardReportViewModel(0) {
    var sn: String = ""
    var message: String = ""
}

class GuardEventViewModel : AbsGuardReportViewModel(1) {
    var isLoadingMore: Boolean = false
}