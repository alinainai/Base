package com.lib.commonsdk.kotlin

import com.lib.commonsdk.kotlin.extension.file.*

object Test {
    @JvmStatic
    fun main(args: Array<String>) {
        //存在的文件
        val path = "D:\\person_data\\test.txt"
        val pathFile = "D:\\person_data\\files"
        //不存在的文件
        val path1 = "D:\\person_data\\test1.txt"
        val pathFile3 = "D:\\person_data\\files3"

        val pathFile2 = "D:\\person_data\\files2"

//
//        println("path是否存在" + path.pathToFile().exists())
//        println("path ext=" + path.pathToFile().extension)
//        println("path namenoext=" + path.pathToFile().nameWithoutExtension)
//
//        println("pathFile 是否存在" + pathFile.pathToFile().exists())
//        println("pathFile 是否是文件" + pathFile.pathToFile().isFile)
//        println("pathFile ext=" + pathFile.pathToFile().extension)

        println("path1是否存在" + path1.pathToFile().exists())
        println("path1 namenoext=" + path1.pathToFile().nameWithoutExtension)
        println("path1 parent=" + path1.pathToFile().parent)
        println("path1 createiIfAbsentSafety=" + path1.pathToFile().createFileByDeleteOldFile())
//        println("path1 rename=" + path1.pathToFile().rename("text3"))

//        println("pathFile2是否存在" + pathFile2.pathToFile().exists())
//        println("pathFile2 namenoext=" + pathFile2.pathToFile().nameWithoutExtension)
//
//        println("pathFile3是否是文件" + pathFile3.pathToFile().isFile)

    }
}