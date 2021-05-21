package com.base.lib.https.image.aes

import android.net.Uri
import android.util.Log
import com.bumptech.glide.Priority
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.HttpException
import com.bumptech.glide.load.data.DataFetcher
import com.bumptech.glide.load.model.LazyHeaders
import okhttp3.*
import timber.log.Timber
import java.io.ByteArrayInputStream
import java.io.IOException
import java.io.InputStream

class AESDataFetcher(private val client: Call.Factory, private val model: ImgBean) : DataFetcher<InputStream>, Callback {

    private var stream: InputStream? = null
    private var responseBody: ResponseBody? = null
    private var callback: DataFetcher.DataCallback<in InputStream>? = null

    @Volatile
    private var call: Call? = null

    override fun loadData(priority: Priority, callback: DataFetcher.DataCallback<in InputStream>) {
        Timber.e("model.url=%s", model.url)
        var url = model.url
        url = Uri.encode(url, ALLOWED_URI_CHARS)
        val requestBuilder: Request.Builder = Request.Builder().url(url)
        for ((key, value) in LazyHeaders.Builder().build().headers) {
            requestBuilder.addHeader(key, value)
        }
        val request: Request = requestBuilder.build()
        this.callback = callback
        call = client.newCall(request)
        call!!.enqueue(this)
    }

    override fun onFailure(call: Call, e: IOException) {
        if (Log.isLoggable(TAG, Log.DEBUG)) {
            Timber.e(e, "OkHttp failed to obtain result")
        }
        callback!!.onLoadFailed(e)
    }

    override fun onResponse(call: Call, response: Response) {
        responseBody = response.body
        if (response.isSuccessful) {
            try {
                stream = ByteArrayInputStream(AESEncryptUtils.decrypt(responseBody!!.bytes(), model.secretkey?:""))
            } catch (e: Exception) {
                Timber.e(e,"stream parse fail")
                callback!!.onLoadFailed(e)
            }
            callback!!.onDataReady(stream)
        } else {
            callback!!.onLoadFailed(HttpException(response.message, response.code))
        }
    }

    override fun cleanup() {
        try {
            if (stream != null) {
                stream!!.close()
            }
        } catch (e: IOException) {
            // Ignored
        }
        if (responseBody != null) {
            responseBody!!.close()
        }
        callback = null
    }

    override fun cancel() {
        val local = call
        local?.cancel()
    }

    override fun getDataClass(): Class<InputStream> {
        return InputStream::class.java
    }

    override fun getDataSource(): DataSource {
        return DataSource.REMOTE
    }

    companion object {
        private const val ALLOWED_URI_CHARS = "@#&=*+-_.,:!?()/~'%;$"
        private const val TAG = "AESDataFetcher"
    }

}