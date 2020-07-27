@file:Suppress("unused")

package com.lib.commonsdk.kotlin.extension.enordecrypt

import android.util.Base64
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.security.*
import java.security.spec.AlgorithmParameterSpec
import java.security.spec.InvalidKeySpecException
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec
import java.util.*
import javax.crypto.*
import javax.crypto.spec.DESKeySpec
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

/**
 * ================================================
 * desc:加密解密相关
 * encryptMD2, encryptMD2ToString                        : MD2 加密
 * encryptMD5, encryptMD5ToString                        : MD5 加密
 * encryptMD5File, encryptMD5File2String                 : MD5 加密文件
 * encryptSHA1, encryptSHA1ToString                      : SHA1 加密
 * encryptSHA224, encryptSHA224ToString                  : SHA224 加密
 * encryptSHA256, encryptSHA256ToString                  : SHA256 加密
 * encryptSHA384, encryptSHA384ToString                  : SHA384 加密
 * encryptSHA512, encryptSHA512ToString                  : SHA512 加密
 * encryptHmacMD5, encryptHmacMD5ToString                : HmacMD5 加密
 * encryptHmacSHA1, encryptHmacSHA1ToString              : HmacSHA1 加密
 * encryptHmacSHA224, encryptHmacSHA224ToString          : HmacSHA224 加密
 * encryptHmacSHA256, encryptHmacSHA256ToString          : HmacSHA256 加密
 * encryptHmacSHA384, encryptHmacSHA384ToString          : HmacSHA384 加密
 * encryptHmacSHA512, encryptHmacSHA512ToString          : HmacSHA512 加密
 * encryptDES, encryptDES2HexString, encryptDES2Base64   : DES 加密
 * decryptDES, decryptHexStringDES, decryptBase64DES     : DES 解密
 * encrypt3DES, encrypt3DES2HexString, encrypt3DES2Base64: 3DES 加密
 * decrypt3DES, decryptHexString3DES, decryptBase64_3DES : 3DES 解密
 * encryptAES, encryptAES2HexString, encryptAES2Base64   : AES 加密
 * decryptAES, decryptHexStringAES, decryptBase64AES     : AES 解密
 * encryptRSA, encryptRSA2HexString, encryptRSA2Base64   : RSA 加密
 * decryptRSA, decryptHexStringRSA, decryptBase64RSA     : RSA 解密
 * ================================================
 */
/**
 * Return the hex string of MD2 encryption.
 *
 * @param data The data.
 * @return the hex string of MD2 encryption
 */
fun encryptMD2ToString(data: String?): String {
    return if (data.isNullOrBlank()) "" else encryptMD2ToString(data.toByteArray())
}

/**
 * Return the hex string of MD2 encryption.
 *
 * @param data The data.
 * @return the hex string of MD2 encryption
 */
fun encryptMD2ToString(data: ByteArray?): String {
    return bytes2HexString(encryptMD2(data))
}

/**
 * Return the bytes of MD2 encryption.
 *
 * @param data The data.
 * @return the bytes of MD2 encryption
 */
fun encryptMD2(data: ByteArray?): ByteArray? {
    return hashTemplate(data, "MD2")
}

/**
 * Return the hex string of MD5 encryption.
 *
 * @param data The data.
 * @return the hex string of MD5 encryption
 */
fun encryptMD5ToString(data: String?): String {
    return if (data.isNullOrBlank()) "" else encryptMD5ToString(data.toByteArray())
}

/**
 * Return the hex string of MD5 encryption.
 *
 * @param data The data.
 * @param salt The salt.
 * @return the hex string of MD5 encryption
 */
fun encryptMD5ToString(data: String?, salt: String?): String {
    if (data == null && salt == null) return ""
    if (salt == null) return bytes2HexString(encryptMD5(data!!.toByteArray()))
    return if (data == null) bytes2HexString(encryptMD5(salt.toByteArray())) else bytes2HexString(encryptMD5((data + salt).toByteArray()))
}

/**
 * Return the hex string of MD5 encryption.
 *
 * @param data The data.
 * @return the hex string of MD5 encryption
 */
fun encryptMD5ToString(data: ByteArray?): String {
    return bytes2HexString(encryptMD5(data))
}

/**
 * Return the hex string of MD5 encryption.
 *
 * @param data The data.
 * @param salt The salt.
 * @return the hex string of MD5 encryption
 */
fun encryptMD5ToString(data: ByteArray?, salt: ByteArray?): String {
    if (data == null && salt == null) return ""
    if (salt == null) return bytes2HexString(encryptMD5(data))
    if (data == null) return bytes2HexString(encryptMD5(salt))
    val dataSalt = ByteArray(data.size + salt.size)
    System.arraycopy(data, 0, dataSalt, 0, data.size)
    System.arraycopy(salt, 0, dataSalt, data.size, salt.size)
    return bytes2HexString(encryptMD5(dataSalt))
}

/**
 * Return the bytes of MD5 encryption.
 *
 * @param data The data.
 * @return the bytes of MD5 encryption
 */
fun encryptMD5(data: ByteArray?): ByteArray? {
    return hashTemplate(data, "MD5")
}

/**
 * Return the hex string of file's MD5 encryption.
 *
 * @param filePath The path of file.
 * @return the hex string of file's MD5 encryption
 */
fun encryptMD5File2String(filePath: String?): String {
    val file = if (isSpace(filePath)) null else File(filePath)
    return encryptMD5File2String(file)
}

/**
 * Return the bytes of file's MD5 encryption.
 *
 * @param filePath The path of file.
 * @return the bytes of file's MD5 encryption
 */
fun encryptMD5File(filePath: String): ByteArray? {
    val file = if (isSpace(filePath)) null else File(filePath)
    return encryptMD5File(file)
}

/**
 * Return the hex string of file's MD5 encryption.
 *
 * @param file The file.
 * @return the hex string of file's MD5 encryption
 */
fun encryptMD5File2String(file: File?): String {
    return bytes2HexString(encryptMD5File(file))
}

/**
 * Return the bytes of file's MD5 encryption.
 *
 * @param file The file.
 * @return the bytes of file's MD5 encryption
 */
fun encryptMD5File(file: File?): ByteArray? {
    if (file == null) return null
    var fis: FileInputStream? = null
    val digestInputStream: DigestInputStream
    return try {
        fis = FileInputStream(file)
        var md = MessageDigest.getInstance("MD5")
        digestInputStream = DigestInputStream(fis, md)
        val buffer = ByteArray(256 * 1024)
        while (true) {
            if (digestInputStream.read(buffer) <= 0) break
        }
        md = digestInputStream.messageDigest
        md.digest()
    } catch (e: NoSuchAlgorithmException) {
        e.printStackTrace()
        null
    } catch (e: IOException) {
        e.printStackTrace()
        null
    } finally {
        try {
            fis?.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}

/**
 * Return the hex string of SHA1 encryption.
 *
 * @param data The data.
 * @return the hex string of SHA1 encryption
 */
fun encryptSHA1ToString(data: String?): String {
    return if (data.isNullOrBlank()) "" else encryptSHA1ToString(data.toByteArray())
}

/**
 * Return the hex string of SHA1 encryption.
 *
 * @param data The data.
 * @return the hex string of SHA1 encryption
 */
fun encryptSHA1ToString(data: ByteArray?): String {
    return bytes2HexString(encryptSHA1(data))
}

/**
 * Return the bytes of SHA1 encryption.
 *
 * @param data The data.
 * @return the bytes of SHA1 encryption
 */
fun encryptSHA1(data: ByteArray?): ByteArray? {
    return hashTemplate(data, "SHA-1")
}

/**
 * Return the hex string of SHA224 encryption.
 *
 * @param data The data.
 * @return the hex string of SHA224 encryption
 */
fun encryptSHA224ToString(data: String?): String {
    return if (data.isNullOrBlank()) "" else encryptSHA224ToString(data.toByteArray())
}

/**
 * Return the hex string of SHA224 encryption.
 *
 * @param data The data.
 * @return the hex string of SHA224 encryption
 */
fun encryptSHA224ToString(data: ByteArray?): String {
    return bytes2HexString(encryptSHA224(data))
}

/**
 * Return the bytes of SHA224 encryption.
 *
 * @param data The data.
 * @return the bytes of SHA224 encryption
 */
fun encryptSHA224(data: ByteArray?): ByteArray? {
    return hashTemplate(data, "SHA224")
}

/**
 * Return the hex string of SHA256 encryption.
 *
 * @param data The data.
 * @return the hex string of SHA256 encryption
 */
fun encryptSHA256ToString(data: String?): String {
    return if (data.isNullOrBlank()) "" else encryptSHA256ToString(data.toByteArray())
}

/**
 * Return the hex string of SHA256 encryption.
 *
 * @param data The data.
 * @return the hex string of SHA256 encryption
 */
fun encryptSHA256ToString(data: ByteArray?): String {
    return bytes2HexString(encryptSHA256(data))
}

/**
 * Return the bytes of SHA256 encryption.
 *
 * @param data The data.
 * @return the bytes of SHA256 encryption
 */
fun encryptSHA256(data: ByteArray?): ByteArray? {
    return hashTemplate(data, "SHA-256")
}

/**
 * Return the hex string of SHA384 encryption.
 *
 * @param data The data.
 * @return the hex string of SHA384 encryption
 */
fun encryptSHA384ToString(data: String?): String {
    return if (data.isNullOrBlank()) "" else encryptSHA384ToString(data.toByteArray())
}

/**
 * Return the hex string of SHA384 encryption.
 *
 * @param data The data.
 * @return the hex string of SHA384 encryption
 */
fun encryptSHA384ToString(data: ByteArray?): String {
    return bytes2HexString(encryptSHA384(data))
}

/**
 * Return the bytes of SHA384 encryption.
 *
 * @param data The data.
 * @return the bytes of SHA384 encryption
 */
fun encryptSHA384(data: ByteArray?): ByteArray? {
    return hashTemplate(data, "SHA-384")
}

/**
 * Return the hex string of SHA512 encryption.
 *
 * @param data The data.
 * @return the hex string of SHA512 encryption
 */
fun encryptSHA512ToString(data: String?): String {
    return if (data.isNullOrBlank()) "" else encryptSHA512ToString(data.toByteArray())
}

/**
 * Return the hex string of SHA512 encryption.
 *
 * @param data The data.
 * @return the hex string of SHA512 encryption
 */
fun encryptSHA512ToString(data: ByteArray?): String {
    return bytes2HexString(encryptSHA512(data))
}

/**
 * Return the bytes of SHA512 encryption.
 *
 * @param data The data.
 * @return the bytes of SHA512 encryption
 */
fun encryptSHA512(data: ByteArray?): ByteArray? {
    return hashTemplate(data, "SHA-512")
}

/**
 * Return the bytes of hash encryption.
 *
 * @param data      The data.
 * @param algorithm The name of hash encryption.
 * @return the bytes of hash encryption
 */
private fun hashTemplate(data: ByteArray?, algorithm: String): ByteArray? {
    return if (data == null || data.isEmpty()) null else try {
        val md = MessageDigest.getInstance(algorithm)
        md.update(data)
        md.digest()
    } catch (e: NoSuchAlgorithmException) {
        e.printStackTrace()
        null
    }
}
///////////////////////////////////////////////////////////////////////////
// hmac encryption
///////////////////////////////////////////////////////////////////////////
/**
 * Return the hex string of HmacMD5 encryption.
 *
 * @param data The data.
 * @param key  The key.
 * @return the hex string of HmacMD5 encryption
 */
fun encryptHmacMD5ToString(data: String?, key: String?): String {
    return if (data == null || data.isEmpty() || key == null || key.isEmpty()) "" else encryptHmacMD5ToString(data.toByteArray(), key.toByteArray())
}

/**
 * Return the hex string of HmacMD5 encryption.
 *
 * @param data The data.
 * @param key  The key.
 * @return the hex string of HmacMD5 encryption
 */
fun encryptHmacMD5ToString(data: ByteArray?, key: ByteArray?): String {
    return bytes2HexString(encryptHmacMD5(data, key))
}

/**
 * Return the bytes of HmacMD5 encryption.
 *
 * @param data The data.
 * @param key  The key.
 * @return the bytes of HmacMD5 encryption
 */
fun encryptHmacMD5(data: ByteArray?, key: ByteArray?): ByteArray? {
    return hmacTemplate(data, key, "HmacMD5")
}

/**
 * Return the hex string of HmacSHA1 encryption.
 *
 * @param data The data.
 * @param key  The key.
 * @return the hex string of HmacSHA1 encryption
 */
fun encryptHmacSHA1ToString(data: String?, key: String?): String {
    return if (data == null || data.isEmpty() || key == null || key.isEmpty()) "" else encryptHmacSHA1ToString(data.toByteArray(), key.toByteArray())
}

/**
 * Return the hex string of HmacSHA1 encryption.
 *
 * @param data The data.
 * @param key  The key.
 * @return the hex string of HmacSHA1 encryption
 */
fun encryptHmacSHA1ToString(data: ByteArray?, key: ByteArray?): String {
    return bytes2HexString(encryptHmacSHA1(data, key))
}

/**
 * Return the bytes of HmacSHA1 encryption.
 *
 * @param data The data.
 * @param key  The key.
 * @return the bytes of HmacSHA1 encryption
 */
fun encryptHmacSHA1(data: ByteArray?, key: ByteArray?): ByteArray? {
    return hmacTemplate(data, key, "HmacSHA1")
}

/**
 * Return the hex string of HmacSHA224 encryption.
 *
 * @param data The data.
 * @param key  The key.
 * @return the hex string of HmacSHA224 encryption
 */
fun encryptHmacSHA224ToString(data: String?, key: String?): String {
    return if (data == null || data.isEmpty() || key == null || key.isEmpty()) "" else encryptHmacSHA224ToString(data.toByteArray(), key.toByteArray())
}

/**
 * Return the hex string of HmacSHA224 encryption.
 *
 * @param data The data.
 * @param key  The key.
 * @return the hex string of HmacSHA224 encryption
 */
fun encryptHmacSHA224ToString(data: ByteArray?, key: ByteArray?): String {
    return bytes2HexString(encryptHmacSHA224(data, key))
}

/**
 * Return the bytes of HmacSHA224 encryption.
 *
 * @param data The data.
 * @param key  The key.
 * @return the bytes of HmacSHA224 encryption
 */
fun encryptHmacSHA224(data: ByteArray?, key: ByteArray?): ByteArray? {
    return hmacTemplate(data, key, "HmacSHA224")
}

/**
 * Return the hex string of HmacSHA256 encryption.
 *
 * @param data The data.
 * @param key  The key.
 * @return the hex string of HmacSHA256 encryption
 */
fun encryptHmacSHA256ToString(data: String?, key: String?): String {
    return if (data.isNullOrBlank() || key.isNullOrBlank()) "" else encryptHmacSHA256ToString(data.toByteArray(), key.toByteArray())
}

/**
 * Return the hex string of HmacSHA256 encryption.
 *
 * @param data The data.
 * @param key  The key.
 * @return the hex string of HmacSHA256 encryption
 */
fun encryptHmacSHA256ToString(data: ByteArray?, key: ByteArray?): String {
    return bytes2HexString(encryptHmacSHA256(data, key))
}

/**
 * Return the bytes of HmacSHA256 encryption.
 *
 * @param data The data.
 * @param key  The key.
 * @return the bytes of HmacSHA256 encryption
 */
fun encryptHmacSHA256(data: ByteArray?, key: ByteArray?): ByteArray? {
    return hmacTemplate(data, key, "HmacSHA256")
}

/**
 * Return the hex string of HmacSHA384 encryption.
 *
 * @param data The data.
 * @param key  The key.
 * @return the hex string of HmacSHA384 encryption
 */
fun encryptHmacSHA384ToString(data: String?, key: String?): String {
    return if (data.isNullOrBlank() || key.isNullOrBlank()) "" else encryptHmacSHA384ToString(data.toByteArray(), key.toByteArray())
}

/**
 * Return the hex string of HmacSHA384 encryption.
 *
 * @param data The data.
 * @param key  The key.
 * @return the hex string of HmacSHA384 encryption
 */
fun encryptHmacSHA384ToString(data: ByteArray?, key: ByteArray?): String {
    return bytes2HexString(encryptHmacSHA384(data, key))
}

/**
 * Return the bytes of HmacSHA384 encryption.
 *
 * @param data The data.
 * @param key  The key.
 * @return the bytes of HmacSHA384 encryption
 */
fun encryptHmacSHA384(data: ByteArray?, key: ByteArray?): ByteArray? {
    return hmacTemplate(data, key, "HmacSHA384")
}

/**
 * Return the hex string of HmacSHA512 encryption.
 *
 * @param data The data.
 * @param key  The key.
 * @return the hex string of HmacSHA512 encryption
 */
fun encryptHmacSHA512ToString(data: String?, key: String?): String {
    return if (data.isNullOrBlank() || key.isNullOrBlank()) "" else encryptHmacSHA512ToString(data.toByteArray(), key.toByteArray())
}

/**
 * Return the hex string of HmacSHA512 encryption.
 *
 * @param data The data.
 * @param key  The key.
 * @return the hex string of HmacSHA512 encryption
 */
fun encryptHmacSHA512ToString(data: ByteArray?, key: ByteArray?): String {
    return bytes2HexString(encryptHmacSHA512(data, key))
}

/**
 * Return the bytes of HmacSHA512 encryption.
 *
 * @param data The data.
 * @param key  The key.
 * @return the bytes of HmacSHA512 encryption
 */
fun encryptHmacSHA512(data: ByteArray?, key: ByteArray?): ByteArray? {
    return hmacTemplate(data, key, "HmacSHA512")
}

/**
 * Return the bytes of hmac encryption.
 *
 * @param data      The data.
 * @param key       The key.
 * @param algorithm The name of hmac encryption.
 * @return the bytes of hmac encryption
 */
private fun hmacTemplate(data: ByteArray?,
                         key: ByteArray?,
                         algorithm: String): ByteArray? {
    return if (data == null || data.isEmpty() || key == null || key.isEmpty()) null else try {
        val secretKey = SecretKeySpec(key, algorithm)
        val mac = Mac.getInstance(algorithm)
        mac.init(secretKey)
        mac.doFinal(data)
    } catch (e: InvalidKeyException) {
        e.printStackTrace()
        null
    } catch (e: NoSuchAlgorithmException) {
        e.printStackTrace()
        null
    }
}
///////////////////////////////////////////////////////////////////////////
// DES encryption
///////////////////////////////////////////////////////////////////////////
/**
 * Return the Base64-encode bytes of DES encryption.
 *
 * @param data           The data.
 * @param key            The key.
 * @param transformation The name of the transformation, e.g., *DES/CBC/PKCS5Padding*.
 * @param iv             The buffer with the IV. The contents of the
 * buffer are copied to protect against subsequent modification.
 * @return the Base64-encode bytes of DES encryption
 */
fun encryptDES2Base64(data: ByteArray?,
                      key: ByteArray?,
                      transformation: String?,
                      iv: ByteArray?): ByteArray {
    return base64Encode(encryptDES(data, key, transformation, iv))
}

/**
 * Return the hex string of DES encryption.
 *
 * @param data           The data.
 * @param key            The key.
 * @param transformation The name of the transformation, e.g., *DES/CBC/PKCS5Padding*.
 * @param iv             The buffer with the IV. The contents of the
 * buffer are copied to protect against subsequent modification.
 * @return the hex string of DES encryption
 */
fun encryptDES2HexString(data: ByteArray?,
                         key: ByteArray?,
                         transformation: String?,
                         iv: ByteArray?): String {
    return bytes2HexString(encryptDES(data, key, transformation, iv))
}

/**
 * Return the bytes of DES encryption.
 *
 * @param data           The data.
 * @param key            The key.
 * @param transformation The name of the transformation, e.g., *DES/CBC/PKCS5Padding*.
 * @param iv             The buffer with the IV. The contents of the
 * buffer are copied to protect against subsequent modification.
 * @return the bytes of DES encryption
 */
fun encryptDES(data: ByteArray?, key: ByteArray?, transformation: String?, iv: ByteArray?): ByteArray? {
    return symmetricTemplate(data, key, "DES", transformation, iv, true)
}

/**
 * Return the bytes of DES decryption for Base64-encode bytes.
 *
 * @param data           The data.
 * @param key            The key.
 * @param transformation The name of the transformation, e.g., *DES/CBC/PKCS5Padding*.
 * @param iv             The buffer with the IV. The contents of the
 * buffer are copied to protect against subsequent modification.
 * @return the bytes of DES decryption for Base64-encode bytes
 */
fun decryptBase64DES(data: ByteArray,
                     key: ByteArray?,
                     transformation: String?,
                     iv: ByteArray?): ByteArray? {
    return decryptDES(base64Decode(data), key, transformation, iv)
}

/**
 * Return the bytes of DES decryption for hex string.
 *
 * @param data           The data.
 * @param key            The key.
 * @param transformation The name of the transformation, e.g., *DES/CBC/PKCS5Padding*.
 * @param iv             The buffer with the IV. The contents of the
 * buffer are copied to protect against subsequent modification.
 * @return the bytes of DES decryption for hex string
 */
fun decryptHexStringDES(data: String,
                        key: ByteArray?,
                        transformation: String?,
                        iv: ByteArray?): ByteArray? {
    return decryptDES(hexString2Bytes(data), key, transformation, iv)
}

/**
 * Return the bytes of DES decryption.
 *
 * @param data           The data.
 * @param key            The key.
 * @param transformation The name of the transformation, e.g., *DES/CBC/PKCS5Padding*.
 * @param iv             The buffer with the IV. The contents of the
 * buffer are copied to protect against subsequent modification.
 * @return the bytes of DES decryption
 */
fun decryptDES(data: ByteArray?,
               key: ByteArray?,
               transformation: String?,
               iv: ByteArray?): ByteArray? {
    return symmetricTemplate(data, key, "DES", transformation, iv, false)
}
///////////////////////////////////////////////////////////////////////////
// 3DES encryption
///////////////////////////////////////////////////////////////////////////
/**
 * Return the Base64-encode bytes of 3DES encryption.
 *
 * @param data           The data.
 * @param key            The key.
 * @param transformation The name of the transformation, e.g., *DES/CBC/PKCS5Padding*.
 * @param iv             The buffer with the IV. The contents of the
 * buffer are copied to protect against subsequent modification.
 * @return the Base64-encode bytes of 3DES encryption
 */
fun encrypt3DES2Base64(data: ByteArray?,
                       key: ByteArray?,
                       transformation: String?,
                       iv: ByteArray?): ByteArray {
    return base64Encode(encrypt3DES(data, key, transformation, iv))
}

/**
 * Return the hex string of 3DES encryption.
 *
 * @param data           The data.
 * @param key            The key.
 * @param transformation The name of the transformation, e.g., *DES/CBC/PKCS5Padding*.
 * @param iv             The buffer with the IV. The contents of the
 * buffer are copied to protect against subsequent modification.
 * @return the hex string of 3DES encryption
 */
fun encrypt3DES2HexString(data: ByteArray?,
                          key: ByteArray?,
                          transformation: String?,
                          iv: ByteArray?): String {
    return bytes2HexString(encrypt3DES(data, key, transformation, iv))
}

/**
 * Return the bytes of 3DES encryption.
 *
 * @param data           The data.
 * @param key            The key.
 * @param transformation The name of the transformation, e.g., *DES/CBC/PKCS5Padding*.
 * @param iv             The buffer with the IV. The contents of the
 * buffer are copied to protect against subsequent modification.
 * @return the bytes of 3DES encryption
 */
fun encrypt3DES(data: ByteArray?,
                key: ByteArray?,
                transformation: String?,
                iv: ByteArray?): ByteArray? {
    return symmetricTemplate(data, key, "DESede", transformation, iv, true)
}

/**
 * Return the bytes of 3DES decryption for Base64-encode bytes.
 *
 * @param data           The data.
 * @param key            The key.
 * @param transformation The name of the transformation, e.g., *DES/CBC/PKCS5Padding*.
 * @param iv             The buffer with the IV. The contents of the
 * buffer are copied to protect against subsequent modification.
 * @return the bytes of 3DES decryption for Base64-encode bytes
 */
fun decryptBase64_3DES(data: ByteArray,
                       key: ByteArray?,
                       transformation: String?,
                       iv: ByteArray?): ByteArray? {
    return decrypt3DES(base64Decode(data), key, transformation, iv)
}

/**
 * Return the bytes of 3DES decryption for hex string.
 *
 * @param data           The data.
 * @param key            The key.
 * @param transformation The name of the transformation, e.g., *DES/CBC/PKCS5Padding*.
 * @param iv             The buffer with the IV. The contents of the
 * buffer are copied to protect against subsequent modification.
 * @return the bytes of 3DES decryption for hex string
 */
fun decryptHexString3DES(data: String,
                         key: ByteArray?,
                         transformation: String?,
                         iv: ByteArray?): ByteArray? {
    return decrypt3DES(hexString2Bytes(data), key, transformation, iv)
}

/**
 * Return the bytes of 3DES decryption.
 *
 * @param data           The data.
 * @param key            The key.
 * @param transformation The name of the transformation, e.g., *DES/CBC/PKCS5Padding*.
 * @param iv             The buffer with the IV. The contents of the
 * buffer are copied to protect against subsequent modification.
 * @return the bytes of 3DES decryption
 */
fun decrypt3DES(data: ByteArray?,
                key: ByteArray?,
                transformation: String?,
                iv: ByteArray?): ByteArray? {
    return symmetricTemplate(data, key, "DESede", transformation, iv, false)
}
///////////////////////////////////////////////////////////////////////////
// AES encryption
///////////////////////////////////////////////////////////////////////////
/**
 * Return the Base64-encode bytes of AES encryption.
 *
 * @param data           The data.
 * @param key            The key.
 * @param transformation The name of the transformation, e.g., *DES/CBC/PKCS5Padding*.
 * @param iv             The buffer with the IV. The contents of the
 * buffer are copied to protect against subsequent modification.
 * @return the Base64-encode bytes of AES encryption
 */
fun encryptAES2Base64(data: ByteArray?,
                      key: ByteArray?,
                      transformation: String?,
                      iv: ByteArray?): ByteArray {
    return base64Encode(encryptAES(data, key, transformation, iv))
}

/**
 * Return the hex string of AES encryption.
 *
 * @param data           The data.
 * @param key            The key.
 * @param transformation The name of the transformation, e.g., *DES/CBC/PKCS5Padding*.
 * @param iv             The buffer with the IV. The contents of the
 * buffer are copied to protect against subsequent modification.
 * @return the hex string of AES encryption
 */
fun encryptAES2HexString(data: ByteArray?,
                         key: ByteArray?,
                         transformation: String?,
                         iv: ByteArray?): String {
    return bytes2HexString(encryptAES(data, key, transformation, iv))
}

/**
 * Return the bytes of AES encryption.
 *
 * @param data           The data.
 * @param key            The key.
 * @param transformation The name of the transformation, e.g., *DES/CBC/PKCS5Padding*.
 * @param iv             The buffer with the IV. The contents of the
 * buffer are copied to protect against subsequent modification.
 * @return the bytes of AES encryption
 */
fun encryptAES(data: ByteArray?,
               key: ByteArray?,
               transformation: String?,
               iv: ByteArray?): ByteArray? {
    return symmetricTemplate(data, key, "AES", transformation, iv, true)
}

/**
 * Return the bytes of AES decryption for Base64-encode bytes.
 *
 * @param data           The data.
 * @param key            The key.
 * @param transformation The name of the transformation, e.g., *DES/CBC/PKCS5Padding*.
 * @param iv             The buffer with the IV. The contents of the
 * buffer are copied to protect against subsequent modification.
 * @return the bytes of AES decryption for Base64-encode bytes
 */
fun decryptBase64AES(data: ByteArray,
                     key: ByteArray?,
                     transformation: String?,
                     iv: ByteArray?): ByteArray? {
    return decryptAES(base64Decode(data), key, transformation, iv)
}

/**
 * Return the bytes of AES decryption for hex string.
 *
 * @param data           The data.
 * @param key            The key.
 * @param transformation The name of the transformation, e.g., *DES/CBC/PKCS5Padding*.
 * @param iv             The buffer with the IV. The contents of the
 * buffer are copied to protect against subsequent modification.
 * @return the bytes of AES decryption for hex string
 */
fun decryptHexStringAES(data: String,
                        key: ByteArray?,
                        transformation: String?,
                        iv: ByteArray?): ByteArray? {
    return decryptAES(hexString2Bytes(data), key, transformation, iv)
}

/**
 * Return the bytes of AES decryption.
 *
 * @param data           The data.
 * @param key            The key.
 * @param transformation The name of the transformation, e.g., *DES/CBC/PKCS5Padding*.
 * @param iv             The buffer with the IV. The contents of the
 * buffer are copied to protect against subsequent modification.
 * @return the bytes of AES decryption
 */
fun decryptAES(data: ByteArray?,
               key: ByteArray?,
               transformation: String?,
               iv: ByteArray?): ByteArray? {
    return symmetricTemplate(data, key, "AES", transformation, iv, false)
}

/**
 * Return the bytes of symmetric encryption or decryption.
 *
 * @param data           The data.
 * @param key            The key.
 * @param algorithm      The name of algorithm.
 * @param transformation The name of the transformation, e.g., *DES/CBC/PKCS5Padding*.
 * @param isEncrypt      True to encrypt, false otherwise.
 * @return the bytes of symmetric encryption or decryption
 */
private fun symmetricTemplate(data: ByteArray?,
                              key: ByteArray?,
                              algorithm: String,
                              transformation: String?,
                              iv: ByteArray?,
                              isEncrypt: Boolean): ByteArray? {
    return if (data == null || data.isEmpty() || key == null || key.isEmpty()) null else try {
        val secretKey: SecretKey
        secretKey = if ("DES" == algorithm) {
            val desKey = DESKeySpec(key)
            val keyFactory = SecretKeyFactory.getInstance(algorithm)
            keyFactory.generateSecret(desKey)
        } else {
            SecretKeySpec(key, algorithm)
        }
        val cipher = Cipher.getInstance(transformation)
        if (iv == null || iv.isEmpty()) {
            cipher.init(if (isEncrypt) Cipher.ENCRYPT_MODE else Cipher.DECRYPT_MODE, secretKey)
        } else {
            val params: AlgorithmParameterSpec = IvParameterSpec(iv)
            cipher.init(if (isEncrypt) Cipher.ENCRYPT_MODE else Cipher.DECRYPT_MODE, secretKey, params)
        }
        cipher.doFinal(data)
    } catch (e: Throwable) {
        e.printStackTrace()
        null
    }
}
///////////////////////////////////////////////////////////////////////////
// RSA encryption
///////////////////////////////////////////////////////////////////////////
/**
 * Return the Base64-encode bytes of RSA encryption.
 *
 * @param data           The data.
 * @param publicKey      The public key.
 * @param keySize        The size of key, e.g. 1024, 2048...
 * @param transformation The name of the transformation, e.g., *RSA/CBC/PKCS1Padding*.
 * @return the Base64-encode bytes of RSA encryption
 */
fun encryptRSA2Base64(data: ByteArray?,
                      publicKey: ByteArray?,
                      keySize: Int,
                      transformation: String): ByteArray {
    return base64Encode(encryptRSA(data, publicKey, keySize, transformation))
}

/**
 * Return the hex string of RSA encryption.
 *
 * @param data           The data.
 * @param publicKey      The public key.
 * @param keySize        The size of key, e.g. 1024, 2048...
 * @param transformation The name of the transformation, e.g., *RSA/CBC/PKCS1Padding*.
 * @return the hex string of RSA encryption
 */
fun encryptRSA2HexString(data: ByteArray?,
                         publicKey: ByteArray?,
                         keySize: Int,
                         transformation: String): String {
    return bytes2HexString(encryptRSA(data, publicKey, keySize, transformation))
}

/**
 * Return the bytes of RSA encryption.
 *
 * @param data           The data.
 * @param publicKey      The public key.
 * @param keySize        The size of key, e.g. 1024, 2048...
 * @param transformation The name of the transformation, e.g., *RSA/CBC/PKCS1Padding*.
 * @return the bytes of RSA encryption
 */
fun encryptRSA(data: ByteArray?,
               publicKey: ByteArray?,
               keySize: Int,
               transformation: String): ByteArray? {
    return rsaTemplate(data, publicKey, keySize, transformation, true)
}

/**
 * Return the bytes of RSA decryption for Base64-encode bytes.
 *
 * @param data           The data.
 * @param privateKey     The private key.
 * @param keySize        The size of key, e.g. 1024, 2048...
 * @param transformation The name of the transformation, e.g., *RSA/CBC/PKCS1Padding*.
 * @return the bytes of RSA decryption for Base64-encode bytes
 */
fun decryptBase64RSA(data: ByteArray,
                     privateKey: ByteArray?,
                     keySize: Int,
                     transformation: String): ByteArray? {
    return decryptRSA(base64Decode(data), privateKey, keySize, transformation)
}

/**
 * Return the bytes of RSA decryption for hex string.
 *
 * @param data           The data.
 * @param privateKey     The private key.
 * @param keySize        The size of key, e.g. 1024, 2048...
 * @param transformation The name of the transformation, e.g., *RSA/CBC/PKCS1Padding*.
 * @return the bytes of RSA decryption for hex string
 */
fun decryptHexStringRSA(data: String,
                        privateKey: ByteArray?,
                        keySize: Int,
                        transformation: String): ByteArray? {
    return decryptRSA(hexString2Bytes(data), privateKey, keySize, transformation)
}

/**
 * Return the bytes of RSA decryption.
 *
 * @param data           The data.
 * @param privateKey     The private key.
 * @param keySize        The size of key, e.g. 1024, 2048...
 * @param transformation The name of the transformation, e.g., *RSA/CBC/PKCS1Padding*.
 * @return the bytes of RSA decryption
 */
fun decryptRSA(data: ByteArray?,
               privateKey: ByteArray?,
               keySize: Int,
               transformation: String): ByteArray? {
    return rsaTemplate(data, privateKey, keySize, transformation, false)
}

/**
 * Return the bytes of RSA encryption or decryption.
 *
 * @param data           The data.
 * @param key            The key.
 * @param keySize        The size of key, e.g. 1024, 2048...
 * @param transformation The name of the transformation, e.g., *DES/CBC/PKCS1Padding*.
 * @param isEncrypt      True to encrypt, false otherwise.
 * @return the bytes of RSA encryption or decryption
 */
private fun rsaTemplate(data: ByteArray?,
                        key: ByteArray?,
                        keySize: Int,
                        transformation: String,
                        isEncrypt: Boolean): ByteArray? {
    if (data == null || data.size == 0 || key == null || key.size == 0) {
        return null
    }
    try {
        val rsaKey: Key?
        rsaKey = if (isEncrypt) {
            val keySpec = X509EncodedKeySpec(key)
            KeyFactory.getInstance("RSA").generatePublic(keySpec)
        } else {
            val keySpec = PKCS8EncodedKeySpec(key)
            KeyFactory.getInstance("RSA").generatePrivate(keySpec)
        }
        if (rsaKey == null) return null
        val cipher = Cipher.getInstance(transformation)
        cipher.init(if (isEncrypt) Cipher.ENCRYPT_MODE else Cipher.DECRYPT_MODE, rsaKey)
        val len = data.size
        var maxLen = keySize / 8
        if (isEncrypt) {
            val lowerTrans = transformation.toLowerCase()
            if (lowerTrans.endsWith("pkcs1padding")) {
                maxLen -= 11
            }
        }
        val count = len / maxLen
        return if (count > 0) {
            var ret = ByteArray(0)
            var buff = ByteArray(maxLen)
            var index = 0
            for (i in 0 until count) {
                System.arraycopy(data, index, buff, 0, maxLen)
                ret = joins(ret, cipher.doFinal(buff))
                index += maxLen
            }
            if (index != len) {
                val restLen = len - index
                buff = ByteArray(restLen)
                System.arraycopy(data, index, buff, 0, restLen)
                ret = joins(ret, cipher.doFinal(buff))
            }
            ret
        } else {
            cipher.doFinal(data)
        }
    } catch (e: NoSuchAlgorithmException) {
        e.printStackTrace()
    } catch (e: NoSuchPaddingException) {
        e.printStackTrace()
    } catch (e: InvalidKeyException) {
        e.printStackTrace()
    } catch (e: BadPaddingException) {
        e.printStackTrace()
    } catch (e: IllegalBlockSizeException) {
        e.printStackTrace()
    } catch (e: InvalidKeySpecException) {
        e.printStackTrace()
    }
    return null
}

/**
 * Return the bytes of RC4 encryption/decryption.
 *
 * @param data The data.
 * @param key  The key.
 */
fun rc4(data: ByteArray?, key: ByteArray?): ByteArray? {
    if (data == null || data.isEmpty() || key == null) return null
    require(!(key.isEmpty() || key.size > 256)) { "key must be between 1 and 256 bytes" }
    val iS = ByteArray(256)
    val iK = ByteArray(256)
    val keyLen = key.size
    for (i in 0..255) {
        iS[i] = i.toByte()
        iK[i] = key[i % keyLen]
    }
    var j = 0
    var tmp: Byte
    for (i in 0..255) {
        j = j + iS[i] + iK[i] and 0xFF
        tmp = iS[j]
        iS[j] = iS[i]
        iS[i] = tmp
    }
    val ret = ByteArray(data.size)
    var i = 0
    var k: Int
    var t: Int
    for (counter in data.indices) {
        i = i + 1 and 0xFF
        j = j + iS[i] and 0xFF
        tmp = iS[j]
        iS[j] = iS[i]
        iS[i] = tmp
        t = iS[i] + iS[j] and 0xFF
        k = iS[t].toInt()
        ret[counter] = (data[counter].toInt() xor k).toByte()
    }
    return ret
}

///////////////////////////////////////////////////////////////////////////
// other utils methods
///////////////////////////////////////////////////////////////////////////
private fun joins(prefix: ByteArray, suffix: ByteArray): ByteArray {
    val ret = ByteArray(prefix.size + suffix.size)
    System.arraycopy(prefix, 0, ret, 0, prefix.size)
    System.arraycopy(suffix, 0, ret, prefix.size, suffix.size)
    return ret
}

private val HEX_DIGITS = charArrayOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F')
private fun bytes2HexString(bytes: ByteArray?): String {
    if (bytes == null) return ""
    val len = bytes.size
    if (len <= 0) return ""
    val ret = CharArray(len shl 1)
    var i = 0
    var j = 0
    while (i < len) {
        ret[j++] = HEX_DIGITS[bytes[i].toInt() shr 4 and 0x0f]
        ret[j++] = HEX_DIGITS[bytes[i].toInt() and 0x0f]
        i++
    }
    return String(ret)
}

private fun hexString2Bytes(hexString: String): ByteArray? {
    var hex = hexString
    if (isSpace(hex)) return null
    var len = hexString.length
    if (len % 2 != 0) {
        hex = "0$hex"
        len += 1
    }
    val hexBytes = hex.toUpperCase(Locale.getDefault()).toCharArray()
    val ret = ByteArray(len shr 1)
    var i = 0
    while (i < len) {
        ret[i shr 1] = (hex2Dec(hexBytes[i]) shl 4 or hex2Dec(hexBytes[i + 1])).toByte()
        i += 2
    }
    return ret
}

private fun hex2Dec(hexChar: Char): Int {
    return when (hexChar) {
        in '0'..'9' -> {
            hexChar - '0'
        }
        in 'A'..'F' -> {
            hexChar - 'A' + 10
        }
        else -> {
            throw IllegalArgumentException()
        }
    }
}

fun base64Decode(input: ByteArray): ByteArray {
    return Base64.decode(input, Base64.NO_WRAP)
}

private fun isSpace(s: String?): Boolean {
    if (s == null) return true
    var i = 0
    val len = s.length
    while (i < len) {
        if (!Character.isWhitespace(s[i])) {
            return false
        }
        ++i
    }
    return true
}
