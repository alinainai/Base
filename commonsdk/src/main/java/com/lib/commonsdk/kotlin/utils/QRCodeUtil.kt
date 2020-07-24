package com.lib.commonsdk.kotlin.utils

import android.graphics.Bitmap
import android.graphics.Matrix
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.WriterException
import com.google.zxing.qrcode.QRCodeWriter
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel
import java.util.*

object QRCodeUtil {

    /**
     * 生成二维码，默认大小为500*500
     *
     * @param text 需要生成二维码的文字、网址等
     * @return bitmap
     */
    @JvmOverloads
    fun createQRCode(text: String?, size: Int = 500): Bitmap? {
        return try {
            val hints = Hashtable<EncodeHintType, String?>()
            hints[EncodeHintType.CHARACTER_SET] = "utf-8"
            hints[EncodeHintType.MARGIN] = 1.toString()
            val bitMatrix = QRCodeWriter().encode(text,
                    BarcodeFormat.QR_CODE, size, size, hints)
            val pixels = IntArray(size * size)
            for (y in 0 until size) {
                for (x in 0 until size) {
                    if (bitMatrix[x, y]) {
                        pixels[y * size + x] = -0x1000000
                    } else {
                        pixels[y * size + x] = -0x1
                    }
                }
            }
            Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888).apply {
                setPixels(pixels, 0, size, 0, 0, size, size)
            }
        } catch (e: WriterException) {
            e.printStackTrace()
            null
        }
    }

    /**
     * bitmap的颜色代替黑色的二维码
     *
     * @param text
     * @param size
     * @param btm
     * @return
     */
    fun createQRCodeWithLogo2(text: String?, size: Int, btm: Bitmap): Bitmap? {
        var mBitmap = btm
        return try {
            val hints = Hashtable<EncodeHintType, Any?>()
            hints[EncodeHintType.CHARACTER_SET] = "utf-8"
            hints[EncodeHintType.ERROR_CORRECTION] = ErrorCorrectionLevel.H
            hints[EncodeHintType.MARGIN] = 1.toString()
            val bitMatrix = QRCodeWriter().encode(text,
                    BarcodeFormat.QR_CODE, size, size, hints)

            //将logo图片按martix设置的信息缩放
            mBitmap = Bitmap.createScaledBitmap(mBitmap, size, size, false)
            val pixels = IntArray(size * size)
            val color = -0x1
            for (y in 0 until size) {
                for (x in 0 until size) {
                    if (bitMatrix[x, y]) {
                        pixels[y * size + x] = mBitmap.getPixel(x, y)
                    } else {
                        pixels[y * size + x] = color
                    }
                }
            }
            val bitmap = Bitmap.createBitmap(size, size,
                    Bitmap.Config.ARGB_8888)
            bitmap.setPixels(pixels, 0, size, 0, 0, size, size)
            bitmap
        } catch (e: WriterException) {
            e.printStackTrace()
            null
        }
    }

    /**
     * bitmap作为底色
     *
     * @param text
     * @param size
     * @param btm
     * @return
     */
    fun createQRCodeWithLogo3(text: String?, size: Int, btm: Bitmap): Bitmap? {
        return try {
            val hints = Hashtable<EncodeHintType, Any?>()
            hints[EncodeHintType.CHARACTER_SET] = "utf-8"
            hints[EncodeHintType.ERROR_CORRECTION] = ErrorCorrectionLevel.H
            hints[EncodeHintType.MARGIN] = 1.toString()
            val bitMatrix = QRCodeWriter().encode(text,
                    BarcodeFormat.QR_CODE, size, size, hints)

            val pixels = IntArray(size * size)
            //将logo图片按martix设置的信息缩放
            Bitmap.createScaledBitmap(btm, size, size, false)?.let {
                val color = -0x6d8ca
                for (y in 0 until size) {
                    for (x in 0 until size) {
                        if (bitMatrix[x, y]) {
                            pixels[y * size + x] = color
                        } else {
                            pixels[y * size + x] = it.getPixel(x, y) and 0x66ffffff
                        }
                    }
                }
            }
            Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888).apply {
                setPixels(pixels, 0, size, 0, 0, size, size)
            }
        } catch (e: WriterException) {
            e.printStackTrace()
            null
        }
    }

    /**
     * 比方法2的颜色黑一些
     *
     * @param text
     * @param size
     * @param mBitmap
     * @return
     */
    fun createQRCodeWithLogo4(text: String?, size: Int, btm: Bitmap): Bitmap? {
        return try {
            val hints = Hashtable<EncodeHintType, Any?>()
            hints[EncodeHintType.CHARACTER_SET] = "utf-8"
            hints[EncodeHintType.ERROR_CORRECTION] = ErrorCorrectionLevel.H
            hints[EncodeHintType.MARGIN] = 1.toString()
            val bitMatrix = QRCodeWriter().encode(text,
                    BarcodeFormat.QR_CODE, size, size, hints)

            //将logo图片按martix设置的信息缩放
            val bitmap = Bitmap.createScaledBitmap(btm, size, size, false)
            val pixels = IntArray(size * size)
            var flag = true
            for (y in 0 until size) {
                for (x in 0 until size) {
                    if (bitMatrix[x, y]) {
                        if (flag) {
                            flag = false
                            pixels[y * size + x] = -0x1000000
                        } else {
                            pixels[y * size + x] = bitmap.getPixel(x, y)
                            flag = true
                        }
                    } else {
                        pixels[y * size + x] = -0x1
                    }
                }
            }
            Bitmap.createBitmap(size, size,
                    Bitmap.Config.ARGB_8888)?.apply {
                setPixels(pixels, 0, size, 0, 0, size, size)
            }
        } catch (e: WriterException) {
            e.printStackTrace()
            null
        }
    }

    /**
     * 生成带logo的二维码
     * @param text
     * @param size
     * @param mBitmap
     * @return
     */
    fun createQRCodeWithLogo5(text: String?, size: Int, btm: Bitmap): Bitmap? {
        return try {
            val halfWidth = size / 10
            val hints = Hashtable<EncodeHintType, Any?>()
            hints[EncodeHintType.CHARACTER_SET] = "utf-8"
            hints[EncodeHintType.ERROR_CORRECTION] = ErrorCorrectionLevel.H
            hints[EncodeHintType.MARGIN] = 1.toString()
            val bitMatrix = QRCodeWriter().encode(text,
                    BarcodeFormat.QR_CODE, size, size, hints)

            //将logo图片按martix设置的信息缩放
            var bitmap = Bitmap.createScaledBitmap(btm, size, size, false)
            val width = bitMatrix.width //矩阵高度
            val height = bitMatrix.height //矩阵宽度
            val halfW = width / 2
            val halfH = height / 2
            val m = Matrix()
            val sx = 2.toFloat() * halfWidth / bitmap.width
            val sy = (2.toFloat() * halfWidth / bitmap.height)
            m.setScale(sx, sy)
            //设置缩放信息
            //将logo图片按martix设置的信息缩放
            bitmap = Bitmap.createBitmap(bitmap, 0, 0,
                    bitmap.width, bitmap.height, m, false)
            val pixels = IntArray(size * size)
            for (y in 0 until size) {
                for (x in 0 until size) {
                    if (x > halfW - halfWidth && x < halfW + halfWidth && y > halfH - halfWidth && y < halfH + halfWidth) {
                        //该位置用于存放图片信息
                        //记录图片每个像素信息
                        pixels[y * width + x] = bitmap.getPixel(x - halfW
                                + halfWidth, y - halfH + halfWidth)
                    } else {
                        if (bitMatrix[x, y]) {
                            pixels[y * size + x] = -0xc84e62
                        } else {
                            pixels[y * size + x] = -0x1
                        }
                    }
                }
            }
            Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888)?.apply {
                setPixels(pixels, 0, size, 0, 0, size, size)
            }
        } catch (e: WriterException) {
            e.printStackTrace()
            null
        }
    }

    /**
     * 修改三个顶角颜色的，带logo的二维码
     * @param text
     * @param size
     * @param mBitmap
     * @return
     */
    fun createQRCodeWithLogo6(text: String?, size: Int, btm: Bitmap): Bitmap? {
        return try {
            val halfWidth = size / 10
            val hints = Hashtable<EncodeHintType, Any?>()
            hints[EncodeHintType.CHARACTER_SET] = "utf-8"
            /*
             * 设置容错级别，默认为ErrorCorrectionLevel.L
             * 因为中间加入logo所以建议你把容错级别调至H,否则可能会出现识别不了
             */
            hints[EncodeHintType.ERROR_CORRECTION] = ErrorCorrectionLevel.H
            hints[EncodeHintType.MARGIN] = 0.toString()
            val bitMatrix = QRCodeWriter().encode(text,
                    BarcodeFormat.QR_CODE, size, size, hints)

            //将logo图片按martix设置的信息缩放
            var bitmap = Bitmap.createScaledBitmap(btm, size, size, false)
            val width = bitMatrix.width //矩阵高度
            val height = bitMatrix.height //矩阵宽度
            val halfW = width / 2
            val halfH = height / 2
            val m = Matrix()
            val sx = 2.toFloat() * halfWidth / bitmap.width
            val sy = (2.toFloat() * halfWidth
                    / bitmap.height)
            m.setScale(sx, sy)
            //设置缩放信息
            //将logo图片按martix设置的信息缩放
            bitmap = Bitmap.createBitmap(bitmap, 0, 0,
                    bitmap.width, bitmap.height, m, false)
            val pixels = IntArray(size * size)
            for (y in 0 until size) {
                for (x in 0 until size) {
                    if (x > halfW - halfWidth && x < halfW + halfWidth && y > halfH - halfWidth && y < halfH + halfWidth) {
                        //该位置用于存放图片信息
                        //记录图片每个像素信息
                        pixels[y * width + x] = bitmap.getPixel(x - halfW
                                + halfWidth, y - halfH + halfWidth)
                    } else {
                        if (bitMatrix[x, y]) {
                            pixels[y * size + x] = -0xeeeeef
                            if (x < 115 && (y < 115 || y >= size - 115) || y < 115 && x >= size - 115) {
                                pixels[y * size + x] = -0x6d8ca
                            }
                        } else {
                            pixels[y * size + x] = -0x1
                        }
                    }
                }
            }
            Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888)?.apply {
                setPixels(pixels, 0, size, 0, 0, size, size)
            }
        } catch (e: WriterException) {
            e.printStackTrace()
            null
        }
    }
}