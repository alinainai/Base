package com.gas.app.bean

/**
 *
 */
class CollectionBean {
    private var _id: String? = null
    var desc: String? = null
    var dataTime: String? = null
    var url: String? = null
    fun get_id(): String? {
        return _id
    }

    fun set_id(_id: String?) {
        this._id = _id
    }

    override fun toString(): String {
        return "CollectionBean{" +
                "_id='" + _id + '\'' +
                ", desc='" + desc + '\'' +
                ", dataTime='" + dataTime + '\'' +
                ", url='" + url + '\'' +
                '}'
    }
}