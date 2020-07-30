package com.lib.commonsdk.kotlin

import com.lib.commonsdk.kotlin.extension.file.pathToFile
import com.lib.commonsdk.kotlin.extension.file.safeDelete

object Test {
    @JvmStatic
    fun main(args: Array<String>) {
        val path = "D:\\person_data\\test.txt"
        val path1 = "D:\\person_data\\test1.txt"
        val pathFile2 = "D:\\person_data\\files2"
        val pathFile = "D:\\person_data\\files"
        println("path是否存在" + path.pathToFile().exists())
        println("path1是否存在" + path1.pathToFile().exists())
        println("是否存在" + pathFile2.pathToFile().exists())
        println("是否存在" + pathFile.pathToFile().exists())
        println("是否删除" + path1.pathToFile().safeDelete())
    }
}