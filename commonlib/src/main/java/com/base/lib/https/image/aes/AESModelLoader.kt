package com.base.lib.https.image.aes

import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader
import com.bumptech.glide.load.Options
import com.bumptech.glide.load.model.ModelLoader
import com.bumptech.glide.load.model.ModelLoader.LoadData
import com.bumptech.glide.load.model.ModelLoaderFactory
import com.bumptech.glide.load.model.MultiModelLoaderFactory
import com.bumptech.glide.signature.ObjectKey
import okhttp3.Call
import okhttp3.OkHttpClient
import java.io.InputStream

class AESModelLoader(private val client: Call.Factory) : ModelLoader<ImgBean, InputStream> {

    override fun handles(url: ImgBean): Boolean {
        return true
    }

    override fun buildLoadData(model: ImgBean, width: Int, height: Int, options: Options): LoadData<InputStream> {
        return LoadData(ObjectKey(model.hashCode()) , AESDataFetcher(client, model))
    }

    class Factory @JvmOverloads constructor(private val client: Call.Factory = internalClient!!) : ModelLoaderFactory<ImgBean, InputStream> {
        override fun build(multiFactory: MultiModelLoaderFactory): ModelLoader<ImgBean, InputStream> {
            return AESModelLoader(client)
        }

        override fun teardown() {
            // Do nothing, this instance doesn't own the client.
        }

        companion object {
            @Volatile
            private var internalClient: OkHttpClient? = null
                get() {
                    if (field == null) {
                        synchronized(OkHttpUrlLoader.Factory::class.java) {
                            if (field == null) {
                                field = OkHttpClient()
                            }
                        }
                    }
                    return field
                }
        }
    }
}