package com.lib.commonsdk.kotlin.extension.file

import com.lib.commonsdk.kotlin.extension.app.debug
import com.lib.commonsdk.kotlin.extension.io.toFile
import com.lib.commonsdk.kotlin.extension.isSpace
import java.io.*
import java.util.*
import java.util.zip.ZipEntry
import java.util.zip.ZipException
import java.util.zip.ZipFile
import java.util.zip.ZipOutputStream

/**
 * ================================================
 * zipFiles          : 批量压缩文件
 * zipFile           : 压缩文件
 * unzipFile         : 解压文件
 * unzipFileByKeyword: 解压带有关键字的文件
 * getFilesPath      : 获取压缩文件中的文件路径链表
 * getComments       : 获取压缩文件中的注释链表*
 * ================================================
 */
private const val BUFFER_LEN = 8192



/**
 * Zip the files.
 *
 * @param srcFilePaths The paths of source files.
 * @param zipFilePath  The path of ZIP file.
 * @param comment      The comment.
 * @return `true`: success<br></br>`false`: fail
 * @throws IOException if an I/O error has occurred
 */
@Throws(IOException::class)
fun zipFiles(srcFilePaths: Collection<String>, zipFilePath: String, comment: String?=null): Boolean {
    var zos: ZipOutputStream? = null
    return try {
        zos = ZipOutputStream(FileOutputStream(zipFilePath))
        for (srcFile in srcFilePaths) {
            if (!zipFile(srcFile.pathToFile(), "", zos, comment)) return false
        }
        true
    } finally {
        zos?.let {
            it.finish()
            it.close()
        }
    }
}

/**
 * Zip the files.
 *
 * @param srcFiles The source of files.
 * @param zipFile  The ZIP file.
 * @return `true`: success<br></br>`false`: fail
 * @throws IOException if an I/O error has occurred
 */
@JvmOverloads
@Throws(IOException::class)
fun zipFiles(srcFiles: Collection<File?>, zipFile: File, comment: String? = null): Boolean {
    var zos: ZipOutputStream? = null
    return try {
        zos = ZipOutputStream(FileOutputStream(zipFile))
        for (srcFile in srcFiles) {
            if (!zipFile(srcFile, "", zos, comment)) return false
        }
        true
    } finally {
        if (zos != null) {
            zos.finish()
            zos.close()
        }
    }
}

/**
 * Zip the file.
 *
 * @param srcFilePath The path of source file.
 * @param zipFilePath The path of ZIP file.
 * @param comment     The comment.
 * @return `true`: success<br></br>`false`: fail
 * @throws IOException if an I/O error has occurred
 */
@Throws(IOException::class)
fun zipFile(srcFilePath: String, zipFilePath: String, comment: String?=null): Boolean {
    return zipFile(srcFilePath.pathToFile() ,zipFilePath.pathToFile(), comment)
}

/**
 * Zip the file.
 *
 * @param srcFile The source of file.
 * @param zipFile The ZIP file.
 * @return `true`: success<br></br>`false`: fail
 * @throws IOException if an I/O error has occurred
 */
@JvmOverloads
@Throws(IOException::class)
fun zipFile(srcFile: File?, zipFile: File?, comment: String? = null): Boolean {
    if (srcFile == null || zipFile == null) return false
    var zos: ZipOutputStream? = null
    return try {
        zos = ZipOutputStream(FileOutputStream(zipFile))
        zipFile(srcFile, "", zos, comment)
    } finally {
        zos?.close()
    }
}

@Throws(IOException::class)
private fun zipFile(srcFile: File?, rootPath: String, zos: ZipOutputStream, comment: String?): Boolean {
    val rootPathNx = rootPath + (if (rootPath.isSpace()) "" else File.separator) + srcFile!!.name
    if (srcFile.isDirectory) {
        val fileList = srcFile.listFiles()
        if (fileList == null || fileList.isEmpty()) {
            val entry = ZipEntry("$rootPathNx/")
            entry.comment = comment
            zos.putNextEntry(entry)
            zos.closeEntry()
        } else {
            for (file in fileList) {
                if (!zipFile(file, rootPathNx, zos, comment)) return false
            }
        }
    } else {
        var input: InputStream? = null
        try {
            input = BufferedInputStream(FileInputStream(srcFile))
            val entry = ZipEntry(rootPath)
            entry.comment = comment
            zos.putNextEntry(entry)
            val buffer = ByteArray(BUFFER_LEN)
            var len: Int
            while (input.read(buffer, 0, BUFFER_LEN).also { len = it } != -1) {
                zos.write(buffer, 0, len)
            }
            zos.closeEntry()
        } finally {
            input?.close()
        }
    }
    return true
}

/**
 * Unzip the file by keyword.
 *
 * @param zipFilePath The path of ZIP file.
 * @param destDirPath The path of destination directory.
 * @param keyword     The keyboard.
 * @return the unzipped files
 * @throws IOException if unzip unsuccessfully
 */
@Throws(IOException::class)
fun unzipFileByKeyword(zipFilePath: String, destDirPath: String, keyword: String?=null): List<File>? {
    return unzipFileByKeyword(zipFilePath.pathToFile(), destDirPath.pathToFile(), keyword)
}

/**
 * Unzip the file by keyword.
 *
 * @param zipFile The ZIP file.
 * @param destDir The destination directory.
 * @param keyword The keyboard.
 * @return the unzipped files
 * @throws IOException if unzip unsuccessfully
 */
@Throws(IOException::class)
fun unzipFileByKeyword(zipFile: File?, destDir: File?, keyword: String?=null): List<File>? {
    if (zipFile == null || destDir == null) return null
    val files: MutableList<File> = ArrayList()
    val zip = ZipFile(zipFile)
    val entries: Enumeration<*> = zip.entries()
    zip.use { zipClose ->
        if (keyword.isNullOrEmpty()||keyword.isSpace()) {
            while (entries.hasMoreElements()) {
                val entry = entries.nextElement() as ZipEntry
                val entryName = entry.name.replace("\\", "/")
                if (entryName.contains("../")) {
                    debug("ZipUtils", "entryName: $entryName is dangerous!")
                    continue
                }
                if (!unzipChildFile(destDir, files, zipClose, entry, entryName)) return files
            }
        } else {
            while (entries.hasMoreElements()) {
                val entry = entries.nextElement() as ZipEntry
                val entryName = entry.name.replace("\\", "/")
                if (entryName.contains("../")) {
                    debug("ZipUtils", "entryName: $entryName is dangerous!")
                    continue
                }
                if (entryName.contains(keyword)) {
                    if (!unzipChildFile(destDir, files, zipClose, entry, entryName)) return files
                }
            }
        }
    }
    return files
}

@Throws(IOException::class)
private fun unzipChildFile(destDir: File, files: MutableList<File>, zip: ZipFile, entry: ZipEntry, name: String): Boolean {
    val file = File(destDir, name)
    files.add(file)
    if (entry.isDirectory) {
        return file.createDirIfAbsent()
    } else {
        if (file.createFileIfAbsent()) return false
        var input: InputStream? = null
        var out: OutputStream? = null
        try {
            input = BufferedInputStream(zip.getInputStream(entry))
            out = BufferedOutputStream(FileOutputStream(file))
            val buffer = ByteArray(BUFFER_LEN)
            var len: Int
            while (input.read(buffer).also { len = it } != -1) {
                out.write(buffer, 0, len)
            }
        } finally {
            input?.close()
            out?.close()
        }
    }
    return true
}

/**
 * Return the files' path in ZIP file.
 *
 * @param zipFilePath The path of ZIP file.
 * @return the files' path in ZIP file
 * @throws IOException if an I/O error has occurred
 */
@Throws(IOException::class)
fun getFilesPath(zipFilePath: String): List<String>? {
    return getFilesPath(zipFilePath.pathToFile())
}

/**
 * Return the files' path in ZIP file.
 *
 * @param zipFile The ZIP file.
 * @return the files' path in ZIP file
 * @throws IOException if an I/O error has occurred
 */
@Throws(IOException::class)
fun getFilesPath(zipFile: File?): List<String>? {
    if (zipFile == null) return null
    val paths: MutableList<String> = ArrayList()
    val zip = ZipFile(zipFile)
    val entries: Enumeration<*> = zip.entries()
    while (entries.hasMoreElements()) {
        val entryName = (entries.nextElement() as ZipEntry).name.replace("\\", "/")
        if (entryName.contains("../")) {
            debug("ZipUtils", "entryName: $entryName is dangerous!")
            paths.add(entryName)
        } else {
            paths.add(entryName)
        }
    }
    zip.close()
    return paths
}

/**
 * Return the files' comment in ZIP file.
 *
 * @param zipFilePath The path of ZIP file.
 * @return the files' comment in ZIP file
 * @throws IOException if an I/O error has occurred
 */
@Throws(IOException::class)
fun getComments(zipFilePath: String): List<String>? {
    return getComments(zipFilePath.pathToFile())
}

/**
 * Return the files' comment in ZIP file.
 *
 * @param zipFile The ZIP file.
 * @return the files' comment in ZIP file
 * @throws IOException if an I/O error has occurred
 */
@Throws(IOException::class)
fun getComments(zipFile: File?): List<String>? {
    if (zipFile == null) return null
    val comments: MutableList<String> = ArrayList()
    val zip = ZipFile(zipFile)
    val entries: Enumeration<*> = zip.entries()
    while (entries.hasMoreElements()) {
        val entry = entries.nextElement() as ZipEntry
        comments.add(entry.comment)
    }
    zip.close()
    return comments
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



