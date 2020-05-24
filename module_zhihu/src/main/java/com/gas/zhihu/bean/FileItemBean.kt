package com.gas.zhihu.bean

import com.lib.commonsdk.utils.FileUtils
import com.lib.commonsdk.utils.TimeUtils
import java.io.File
import java.util.*

data class FileItemBean(val fileName: String,
                        val filePath: String,
                        val fileSize: Long,
                        val time: String,
                        var isSelect: Boolean) {
    companion object{

        fun createFromFile(file:File):FileItemBean{
            return FileItemBean(file.name,
                    file.path,
                    file.length(),
                    TimeUtils.getDateAndTime(FileUtils.getFileLastModified(file)),
                    false)
        }

    }
}

fun FileItemBean.sizeFormat(): String? {

    return when {
        this.fileSize < 0 -> {
            "shouldn't be less than zero!"
        }
        this.fileSize < 1024 -> {
            String.format(Locale.getDefault(), "%.3fB", this.fileSize.toDouble())
        }
        this.fileSize < 1048576 -> {
            String.format(Locale.getDefault(), "%.3fKB", this.fileSize.toDouble() / 1024)
        }
        this.fileSize < 1073741824 -> {
            String.format(Locale.getDefault(), "%.3fMB", this.fileSize.toDouble() / 1048576)
        }
        else -> {
            String.format(Locale.getDefault(), "%.3fGB", this.fileSize.toDouble() / 1073741824)
        }
    }
}