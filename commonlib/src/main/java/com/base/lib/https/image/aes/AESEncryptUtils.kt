package com.base.lib.https.image.aes

import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

object AESEncryptUtils {

    @Throws(Exception::class)
    @JvmStatic
    fun decrypt(content: ByteArray?, key: String): ByteArray {
        val keyBytes = key.toByteArray(charset("utf-8"))
        val buf = ByteArray(16)
        var i = 0
        while (i < keyBytes.size && i < buf.size) {
            buf[i] = keyBytes[i]
            i++
        }
        val keySpec = SecretKeySpec(buf, "AES")
        val ivSpec = IvParameterSpec(keyBytes)
        val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding", "BC")
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec)
        return cipher.doFinal(content)
    }
}