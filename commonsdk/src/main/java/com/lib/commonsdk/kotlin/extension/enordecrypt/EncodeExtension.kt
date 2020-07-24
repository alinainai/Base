package com.lib.commonsdk.kotlin.extension.enordecrypt

import android.os.Build
import android.text.Html
import android.util.Base64
import java.io.UnsupportedEncodingException
import java.net.URLDecoder
import java.net.URLEncoder

/**
 * ================================================
 * desc:
 * urlEncode          : URL 编码
 * urlDecode          : URL 解码
 * base64Encode       : Base64 编码
 * base64Encode2String: Base64 编码
 * base64Decode       : Base64 解码
 * htmlEncode         : Html 编码
 * htmlDecode         : Html 解码
 * ================================================
 */
/**
 * Return the urlencoded string.
 *
 * @param input       The input.
 * @param charsetName The name of charset.
 * @return the urlencoded string
 */
/**
 * Return the urlencoded string.
 *
 * @param input The input.
 * @return the urlencoded string
 */
@JvmOverloads
fun urlEncode(input: String?, charsetName: String? = "UTF-8"): String {
    return if (input.isNullOrBlank()) "" else try {
        URLEncoder.encode(input, charsetName)
    } catch (e: UnsupportedEncodingException) {
        throw AssertionError(e)
    }
}
/**
 * Return the string of decode urlencoded string.
 *
 * @param input       The input.
 * @param charsetName The name of charset.
 * @return the string of decode urlencoded string
 */
/**
 * Return the string of decode urlencoded string.
 *
 * @param input The input.
 * @return the string of decode urlencoded string
 */
@JvmOverloads
fun urlDecode(input: String?, charsetName: String? = "UTF-8"): String {
    return if (input.isNullOrBlank()) "" else try {
        val safeInput = input.replace("%(?![0-9a-fA-F]{2})".toRegex(), "%25").replace("\\+".toRegex(), "%2B")
        URLDecoder.decode(safeInput, charsetName)
    } catch (e: UnsupportedEncodingException) {
        throw AssertionError(e)
    }
}

/**
 * Return Base64-encode bytes.
 *
 * @param input The input.
 * @return Base64-encode bytes
 */
fun base64Encode(input: String): ByteArray {
    return base64Encode(input.toByteArray())
}

/**
 * Return Base64-encode bytes.
 *
 * @param input The input.
 * @return Base64-encode bytes
 */
fun base64Encode(input: ByteArray?): ByteArray {
    return if (input == null || input.isEmpty()) ByteArray(0) else Base64.encode(input, Base64.NO_WRAP)
}

/**
 * Return Base64-encode string.
 *
 * @param input The input.
 * @return Base64-encode string
 */
fun base64Encode2String(input: ByteArray?): String {
    return if (input == null || input.isEmpty()) "" else Base64.encodeToString(input, Base64.NO_WRAP)
}

/**
 * Return the bytes of decode Base64-encode string.
 *
 * @param input The input.
 * @return the string of decode Base64-encode string
 */
fun base64Decode(input: String?): ByteArray {
    return if (input == null || input.isEmpty()) ByteArray(0) else Base64.decode(input, Base64.NO_WRAP)
}

/**
 * Return the bytes of decode Base64-encode bytes.
 *
 * @param input The input.
 * @return the bytes of decode Base64-encode bytes
 */
fun base64Decode(input: ByteArray?): ByteArray {
    return if (input == null || input.isEmpty()) ByteArray(0) else Base64.decode(input, Base64.NO_WRAP)
}

/**
 * Return html-encode string.
 *
 * @param input The input.
 * @return html-encode string
 */
fun htmlEncode(input: CharSequence?): String {
    if (input == null || input.isEmpty()) return ""
    val sb = StringBuilder()
    var c: Char
    var i = 0
    val len = input.length
    while (i < len) {
        c = input[i]
        when (c) {
            '<' -> sb.append("&lt;") //$NON-NLS-1$
            '>' -> sb.append("&gt;") //$NON-NLS-1$
            '&' -> sb.append("&amp;") //$NON-NLS-1$
            '\'' ->                     //http://www.w3.org/TR/xhtml1
                // The named character reference &apos; (the apostrophe, U+0027) was
                // introduced in XML 1.0 but does not appear in HTML. Authors should
                // therefore use &#39; instead of &apos; to work as expected in HTML 4
                // user agents.
                sb.append("&#39;") //$NON-NLS-1$
            '"' -> sb.append("&quot;") //$NON-NLS-1$
            else -> sb.append(c)
        }
        i++
    }
    return sb.toString()
}

/**
 * Return the string of decode html-encode string.
 *
 * @param input The input.
 * @return the string of decode html-encode string
 */
fun htmlDecode(input: String?): CharSequence {
    if (input.isNullOrBlank()) return ""
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Html.fromHtml(input, Html.FROM_HTML_MODE_LEGACY)
    } else {
        Html.fromHtml(input)
    }
}

/**
 * Return the binary encoded string padded with one space
 *
 * @param input The input.
 * @return binary string
 */
fun binEncode(input: String): String {
    val stringBuilder = StringBuilder()
    for (i in input.toCharArray()) {
        stringBuilder.append(Integer.toBinaryString(i.toInt()))
        stringBuilder.append(' ')
    }
    return stringBuilder.toString()
}

/**
 * Return UTF-8 String from binary
 *
 * @param input binary string
 * @return UTF-8 String
 */
fun binDecode(input: String): String {
    val splitted = input.split(" ".toRegex()).toTypedArray()
    val sb = StringBuilder()
    for (i in splitted) {
        sb.append(i.replace(" ", "").toInt(2).toChar())
    }
    return sb.toString()
}
