package com.gas.app.bean

import android.os.Parcel
import android.os.Parcelable

/**
 *
 */
class GankItemData : Parcelable {
    private var _id: String? = null
    var createdAt: String? = null
    var desc: String? = null
    var images: Array<String>? = null
    var source: String? = null
    var type: String? = null
    var publishedAt: String? = null
    var url: String? = null
    var isUsed = false
    var who: String? = null
    fun get_id(): String? {
        return _id
    }

    fun set_id(_id: String?) {
        this._id = _id
    }

    constructor() {}

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(_id)
        dest.writeString(createdAt)
        dest.writeString(desc)
        dest.writeStringArray(images)
        dest.writeString(publishedAt)
        dest.writeString(source)
        dest.writeString(type)
        dest.writeString(url)
        dest.writeByte(if (isUsed) 1.toByte() else 0.toByte())
        dest.writeString(who)
    }

    protected constructor(`in`: Parcel) {
        _id = `in`.readString()
        createdAt = `in`.readString()
        desc = `in`.readString()
        images = `in`.createStringArray()
        publishedAt = `in`.readString()
        source = `in`.readString()
        type = `in`.readString()
        url = `in`.readString()
        isUsed = `in`.readByte().toInt() != 0
        who = `in`.readString()
    }

    override fun toString(): String {
        return "GankItemData{" +
                "_id='" + _id + '\'' +
                ", desc='" + desc + '\'' +
                ", publishedAt='" + publishedAt + '\'' +
                ", url='" + url + '\'' +
                ", who='" + who + '\'' +
                '}'
    }

    companion object {
        val CREATOR: Parcelable.Creator<GankItemData> = object : Parcelable.Creator<GankItemData> {
            override fun createFromParcel(source: Parcel): GankItemData? {
                return GankItemData(source)
            }

            override fun newArray(size: Int): Array<GankItemData?> {
                return arrayOfNulls(size)
            }
        }
    }
}