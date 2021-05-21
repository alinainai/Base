package com.base.lib.https.image.aes

import com.bumptech.glide.load.Key
import com.bumptech.glide.signature.ObjectKey
import java.security.MessageDigest
import java.util.*

data class ImgBean(val url: String?=null,val secretkey: String?=null,val encrypt: String?=null): Key {

    override fun updateDiskCacheKey(messageDigest: MessageDigest) {
        if(url!=null || secretkey!=null){
            messageDigest.digest("$url$secretkey".toByteArray())
        }
    }
}