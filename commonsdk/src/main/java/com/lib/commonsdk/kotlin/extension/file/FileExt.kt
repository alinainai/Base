package com.lib.commonsdk.kotlin.extension.file

import android.net.Uri
import android.os.Build
import com.lib.commonsdk.kotlin.extension.io.closeQuietly
import com.lib.commonsdk.kotlin.extension.number.isUtf8
import com.lib.commonsdk.kotlin.extension.number.toHex
import com.lib.commonsdk.kotlin.utils.AppUtils
import java.io.*
import java.security.MessageDigest
import java.util.*
import kotlin.concurrent.withLock

/**
 * ================================================
 * [pathToFile]                : 文件路径转文件
 * [isExists]              : 判断文件是否存在
 * [rename]                   : 重命名文件
 * [isDir]                   : 判断是否是目录
 * [isFile]                    : 判断是否是文件
 * [createDirIfAbsent]         : 判断目录是否存在，不存在则判断是否创建成功
 * [createFileIfAbsent]        : 判断文件是否存在，不存在则判断是否创建成功
 * [createFileByDeleteOldFile] : 判断文件是否存在，存在则在创建之前删除
 * [copy]                      : 复制文件或目录
 * [move]                      : 移动文件或目录
 * [safeDelete]                    : 删除文件或目录
 * [deleteAllInDir]            : 删除目录下所有内容
 * [deleteFilesInDir]          : 删除目录下所有文件
 * [deleteFilesInDirWithFilter]: 删除目录下所有过滤的文件
 * [listFilesInDirWithFilter]  : 获取目录下所有过滤的文件
 * [getFileCharsetSimple]      : 简单获取文件编码格式
 * ================================================
 */
private val LINE_SEP = System.getProperty("line.separator")

fun String.pathToFile() = File(this)

fun File.isDir() = exists() && isDirectory

val File.nameNoExt: String
    get() {
        name.lastIndexOf('.').takeIf { it != -1 }?.let {
            return name.substring(0, it)
        }
        return name
    }

fun String.isFile(): Boolean {
    return pathToFile().isFile
}

fun String.isExists(): Boolean {
    return if (Build.VERSION.SDK_INT < 29) {
        pathToFile().exists()
    } else {
        try {
            val uri = Uri.parse(this)
            val cr = AppUtils.app.contentResolver
            val afd = cr.openAssetFileDescriptor(uri, "r") ?: return false
            afd.closeQuietly()
        } catch (e: FileNotFoundException) {
            return false
        }
        true
    }
}

fun File.rename(newName: String): Boolean {
    if (exists() && !newName.isNotBlank()) {
        if (newName == name) return true
        val newFile = File("${parent}${File.separator}${newName}")
        return (!newFile.exists() && renameTo(newFile))
    }
    return false
}

fun File.createDirIfAbsent() = if (exists()) isDirectory else mkdirs()

fun File.createFileIfAbsent(): Boolean {
    if (exists()) return isFile
    parentFile?.takeIf { createDirIfAbsent() }?.let {
        try {
            createNewFile()
            return true
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
    return false
}

fun File.createFileByDeleteOldFile(): Boolean {
    if (exists() && !delete()) return false
    return createFileIfAbsent()
}

fun copy(srcPath: String, destPath: String): Boolean {
    return copy(srcPath.pathToFile(), destPath.pathToFile())
}

fun copy(src: File, dest: File): Boolean {
    return if (src.isDirectory) {
        copyDir(src, dest)
    } else copyFile(src, dest)
}

private fun copyDir(srcDir: File, destDir: File): Boolean {
    return copyOrMoveDir(srcDir, destDir, false)
}

private fun copyFile(srcFile: File, destFile: File): Boolean {
    return copyOrMoveFile(srcFile, destFile, false)
}

fun move(srcPath: String, destPath: String): Boolean {
    return move(srcPath.pathToFile(), destPath.pathToFile())
}

fun move(src: File, dest: File): Boolean {
    return if (src.isDirectory) moveDir(src, dest) else moveFile(src, dest)
}

fun moveDir(srcDir: File, destDir: File): Boolean {
    return copyOrMoveDir(srcDir, destDir, true)
}

fun moveFile(srcFile: File, destFile: File): Boolean {
    return copyOrMoveFile(srcFile, destFile, true)
}

private fun copyOrMoveDir(srcDir: File, destDir: File, isMove: Boolean): Boolean {
    val srcPath = "${srcDir.path}${File.separator}"
    val destPath = "${destDir.path}${File.separator}"
    if (destPath.contains(srcPath)) return false
    if (!srcDir.exists() || !srcDir.isDirectory) return false
    if (!destDir.createDirIfAbsent()) return false
    srcDir.listFiles()?.forEach { file ->
        val oneDestFile = File("${destPath}${file.name}")
        if (file.isFile) {
            if (!copyOrMoveFile(file, oneDestFile, isMove)) return false
        } else if (file.isDirectory) {
            if (!copyOrMoveDir(file, oneDestFile, isMove)) return false
        }
    }
    return !isMove || srcDir.safeDelete()
}

private fun copyOrMoveFile(srcFile: File, destFile: File, isMove: Boolean): Boolean {
    if (srcFile != destFile && srcFile.exists() && srcFile.isFile) {
        if (destFile.exists()) {
            if (!destFile.delete()) {
                return false
            }
        }
        destFile.parentFile?.takeIf { it.createDirIfAbsent() }?.let {
            try {
                return (srcFile.inputStream().writeToFile(destFile) && !(isMove && !srcFile.safeDelete()))
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            }
        }
    }
    return false
}

fun File.safeDelete(): Boolean {
    return try {
        if (isDirectory) {
            var result = true
            listFiles()?.forEach { result = result && it.safeDelete() }
            // 最后，需要删除目录本身
            result && delete()
        } else {
            delete()
        }
    } catch (e: SecurityException) {
        e.printStackTrace()
        false
    }
}

fun File.deleteAllInDir(): Boolean {
    return deleteFilesInDirWithFilter(FileFilter { true })
}

fun File.deleteFilesInDir(): Boolean {
    return deleteFilesInDirWithFilter(FileFilter { pathname -> pathname.isFile })
}

fun File.deleteFilesInDirWithFilter(filter: FileFilter): Boolean {
    if (!exists()) return true
    if (!isDirectory) return false
    listFiles()?.forEach { file ->
        if (filter.accept(file)) {
            if (file.isFile) {
                if (!file.delete()) return false
            } else if (file.isDirectory) {
                if (!file.safeDelete()) return false
            }
        }
    }
    return true
}

fun File.listFilesInDir(isRecursive: Boolean, comparator: Comparator<File>? = null): List<File> {
    return listFilesInDirWithFilter(FileFilter { true }, isRecursive, comparator)
}

fun File.listFilesInDirWithFilter(filter: FileFilter = FileFilter { true }, isRecursive: Boolean = false, comparator: Comparator<File>? = null): List<File> {
    val files = listFilesInDirWithFilterInner(this, filter, isRecursive)
    if (comparator != null) {
        Collections.sort(files, comparator)
    }
    return files
}

private fun listFilesInDirWithFilterInner(dir: File, filter: FileFilter, isRecursive: Boolean): List<File> {
    val list: MutableList<File> = ArrayList()
    if (!dir.isDir()) return list
    dir.listFiles()?.forEach { file ->
        if (filter.accept(file)) {
            list.add(file)
        }
        if (isRecursive && file.isDirectory) {
            list.addAll(listFilesInDirWithFilterInner(file, filter, true))
        }
    }
    return list
}

fun File.getFileCharsetSimple(): String {
    if (this.isUtf8()) return "UTF-8"
    var p = 0
    inputStream().buffered().use { buffer ->
        p = (buffer.read() shl 8) + buffer.read()
    }
    return when (p) {
        0xfffe -> "Unicode"
        0xfeff -> "UTF-16BE"
        else -> "GBK"
    }
}

fun File.isUtf8(): Boolean {
    inputStream().buffered().use { buffer ->
        val bytes = ByteArray(24)
        buffer.read(bytes).takeIf { it != -1 }?.let {
            val readArr = ByteArray(it)
            System.arraycopy(bytes, 0, readArr, 0, it)
            return readArr.isUtf8() == 100
        }
    }
    return false
}

/**
 * 计算md5值使用的buffer
 * 经过测试，每次创建buffer会耗费30ms左右的时间
 * 把buffer共用，可以提升计算多个文件md5值的速度
 */
private val md5Buffer = ByteArray(1024 * 1024)

val lock = java.util.concurrent.locks.ReentrantLock()

fun File.md5(): String {
    // md5对象是全局共享的，所以说如果同时有两个以上的md5在计算的时候，
    // 会出现问题。所以说这个地方加了一个锁，防止重复进入。
    lock.withLock {
        val MD5 = MessageDigest.getInstance("MD5")
        var fileInputStream: FileInputStream? = null
        try {
            fileInputStream = FileInputStream(this)
            var length: Int
            while (fileInputStream.available() > 0) {
                length = fileInputStream.read(md5Buffer)
                MD5.update(md5Buffer, 0, length)
            }
            return MD5.digest().toHex()
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
            return ""
        } catch (e: IOException) {
            e.printStackTrace()
            return ""
        } catch (e: Throwable) {
            e.printStackTrace()
            return ""
        } finally {
            try {
                fileInputStream?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
}

fun InputStream.writeToFile(file: File): Boolean {
    var os: OutputStream? = null
    return try {
        os = BufferedOutputStream(FileOutputStream(file))
        val data = ByteArray(8192)
        var len: Int
        while (this.read(data, 0, 8192).also { len = it } != -1) {
            os.write(data, 0, len)
        }
        true
    } catch (e: IOException) {
        e.printStackTrace()
        false
    } finally {
        try {
            this.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        try {
            os?.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}



