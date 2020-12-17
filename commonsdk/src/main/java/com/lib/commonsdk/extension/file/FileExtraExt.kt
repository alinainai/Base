package com.lib.commonsdk.extension.file

import com.lib.commonsdk.extension.runInTryCatch
import com.lib.commonsdk.extension.sumByLong
import java.io.File
import java.io.IOException

/**
 * 保证一个文件夹已经被创建。如果创建失败抛异常。
 */
fun File.ensureFolder() = apply {
    if (!exists() && !mkdirs()) throw IOException("Failed to create dir $name")
}

/**
 * 保证一个文件已经存在，便于做后续操作。
 */
fun File.createIfAbsent() = apply {
    parentFile.ensureFolder()
    if (!exists() && !createNewFile()) throw IOException("Failed to create file $name")
}

fun Array<File>.firstMp3OpusFileOrNull() = firstOrNull { it.isFile && (it.extension == "mp3" || it.extension == "opus") }

fun Array<File>.filterMp3OpusFileOrNull() = filter { it.isFile && (it.extension == "mp3" || it.extension == "opus") }

fun Array<File>.filterBookAudioOrNull() = filter { it.isFile && (it.extension == "mp3" || it.extension == "opus" || it.extension == "aac" || it.extension == "lin") }

fun File.safeListFiles(): Array<File> {
    var files = arrayOf<File>()
    runInTryCatch {
        val list = listFiles()
        if (list?.isNotEmpty() == true) {
            files = list
        }
    }
    return files
}

fun File.safeList(): Array<String> {
    return if (exists()) list()?:arrayOf() else arrayOf()
}

private val imageExtensions = listOf(
        "bmp",
        "jpg",
        "jpeg",
        "png"
)

fun File.isImage() = isFile && imageExtensions.contains(extension)

/**
 * 获取文件夹中所有文件大小
 * 如果是文件直接返回文件大小
 *
 * !!! 注意 !!!:
 *     发现这种方式计算文件大小，会产生大量内存占用，
 *     (究其原因是wail()函数每次都会创建一个Sequence<File>)
 *     所以不要放在循环中使用，会造成大量GC
 */
fun File.getFileSize() = walk().sumByLong(File::length)

/**
 * 删除文件夹内最久没有改动的一个子文件
 */
fun File.deleteOldestChildFile(): Boolean = getOldestChildFile()?.safeDelete() ?: false

fun File.getOldestChildFile() = listFiles()?.apply { sortBy(File::lastModified) }?.firstOrNull()

/**
 * 清空目录到允许的大小，单位：字节
 * 注意：只会寻找当前目录下的文件或文件夹，不会再寻找子目录中的文件；
 *      删除时以当前目录下的文件和文件夹为单位删除。
 */
fun File.keepFolderSize(allowSize: Long) {
    if (!isDirectory) {
        return
    }
    var deleteSize = getFileSize() - allowSize
    if (deleteSize <= 0) {
        return
    }
    listFiles()?.apply { sortBy(File::lastModified) }?.forEach {
        if (deleteSize <= 0) {
            return
        }
        deleteSize -= it.getFileSize()
        it.safeDelete()
    }
}

/**
 * 清空目录下的文件／文件夹到指定数量
 */
fun File.keepChildFileCount(allowCount: Int) {
    if (!isDirectory || allowCount <= 0) {
        return
    }
    val files = listFiles()?.apply { sortBy(File::lastModified) }
    val totalCount = files?.count() ?: 0
    if (totalCount <= allowCount) {
        return
    }
    (0..totalCount - allowCount).forEach {
        files?.get(it)?.safeDelete()
    }
}

/**
 * 获取最久未使用的指定数量的子文件/文件夹
 */
fun File.getOldestChildFilesOrNull(allowCount: Int): MutableList<File>? {
    if (!isDirectory || allowCount <= 0) {
        return null
    }
    val files = safeListFiles().apply { sortBy(File::lastModified) }
    val totalCount = files.count()
    if (totalCount <= allowCount) {
        return null
    }
    val oldestFiles = mutableListOf<File>()
    (0..totalCount - allowCount).forEach {
        files[it].let { currentFile ->
            oldestFiles.add(currentFile)
        }
    }
    return oldestFiles
}

fun File.copyToIfExists(target: File, overwrite: Boolean = false) {
    if (exists()) {
        copyTo(target, overwrite)
    }
}