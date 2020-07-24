package com.lib.commonsdk.kotlin.extension.file

import com.lib.commonsdk.kotlin.extension.number.toHex
import com.lib.commonsdk.kotlin.extension.app.runInTryCatch
import com.lib.commonsdk.kotlin.extension.sumByLong
import com.lib.commonsdk.kotlin.extension.io.toFile
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.IOException
import java.security.MessageDigest
import java.util.zip.ZipException
import java.util.zip.ZipFile
import kotlin.concurrent.withLock

/**
 * 对现有的Java类库里边的类做的扩展
 */
// ------------------------------

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

/**
<<<<<<< HEAD
<<<<<<< HEAD
 * 德国版本为避免权利纠纷，不再使用mp3格式的音频文件，德国版本统一使用opus
 * 国过内版本不变，仍然使用mp3，所以在此处增加opus格式音频的校验
 */
fun Array<File>.firstMp3OpusFileOrNull() = firstOrNull { it.isFile && (it.extension == "mp3" || it.extension == "opus") }

/**
 * 德国版本为避免权利纠纷，不再使用mp3格式的音频文件，德国版本统一使用opus
 * 国过内版本不变，仍然使用mp3，所以在此处增加opus格式音频的校验
 */
fun Array<File>.filterMp3OpusFileOrNull() = filter { it.isFile && (it.extension == "mp3" || it.extension == "opus") }

/**
 * 德国版本为避免权利纠纷，不再使用mp3格式的音频文件，德国版本统一使用opus
 * 国过内版本不变，仍然使用mp3，所以在此处增加opus格式音频的校验，即绘本可能又4种格式音频
 */
fun Array<File>.filterBookAudioOrNull() = filter { it.isFile && (it.extension == "mp3" || it.extension == "opus" || it.extension == "aac" || it.extension == "lin") }

/**
 * 返回一个安全的文件夹列表
=======
 * 返回一个安全的自文件夹列表
>>>>>>> Revert "Merge branch 'feature-ailiyun' into 'feature-ailiyun'"
=======
 * 返回一个安全的文件夹列表
>>>>>>> Revert "Merge branch 'revert-6ea804a4' into 'feature-ailiyun'"
 */
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
    return if (exists()) list() else arrayOf()
}

private val imageExtensions = listOf(
        "bmp",
        "jpg",
        "jpeg",
        "png"
)

fun File.isImage() = isFile && imageExtensions.contains(extension)

fun File.safeDelete(): Boolean {
    try {
        if (isDirectory) {
            var result = true
            listFiles()?.forEach { result = result && it.safeDelete() }
            // 最后，需要删除目录本身
            return result && delete()
        } else {
            return delete()
        }
    } catch (e: SecurityException) {
        e.printStackTrace()
        return false
    }
}

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

val lock = java.util.concurrent.locks.ReentrantLock()

// 计算md5值使用的buffer
// 经过测试，每次创建buffer会耗费30ms左右的时间
// 把buffer共用，可以提升计算多个文件md5值的速度
private val md5Buffer = ByteArray(1024 * 1024)
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

/**
 * 解压缩一个文件
 * @param zipFile 压缩文件
 * @param folderPath 解压缩的目标目录
 * @throws IOException 当解压缩过程出错时抛出
 */
@Throws(ZipException::class, IOException::class)
fun File.unzipFile(folderPath: String) {
    File(folderPath).ensureFolder()
    val zf = ZipFile(this)
    val entries = zf.entries()
    while (entries.hasMoreElements()) {
        val entry = entries.nextElement()
        if (entry.isDirectory) {
            continue
        }
        zf.getInputStream(entry).toFile(File(folderPath + File.separator + entry.name))
    }
}

/**
 * 解压文件时，如果文件解压失败，会删除异常的文件，但仍然向外抛出异常
 * @param zipFile 压缩文件
 * @param folderPath 解压缩的目标目录
 * @throws IOException 当解压缩过程出错时抛出
 */
@Throws(ZipException::class, IOException::class)
fun File.unzipAndSafeDelete(folderPath: String) {
    try {
        unzipFile(folderPath)
    } catch (t: Throwable) {
        throw t
    } finally {
        safeDelete()
    }
}

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