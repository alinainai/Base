package com.gas.zhihu.bean

data class PaperShowBean(var paperKey: String = "",
        var filePath: String = "",
                         var fileName: String = "",
                         var mapName: String = "",
                         var voltageLevel: String = "",
                         var mapKey: String = "") {


}

fun PaperShowBean.paperToShow(bean: PaperBean, mapBean: MapBean?) {
    paperKey = bean.id.toString()
    fileName = bean.fileName
    filePath = bean.pathName
    mapName = mapBean?.mapName?:""
    mapKey = bean.mapKey
    voltageLevel = bean.voltageLevel.toString()
}