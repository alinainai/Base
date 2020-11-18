package com.gas.test.ui.fragment.room.data

import android.graphics.Bitmap
import androidx.room.*

@Entity(tableName = "users")
data class User(
        @PrimaryKey(autoGenerate = true) var id: Int,
        @ColumnInfo(name = "first_name")  var firstName: String?,
        @ColumnInfo(name = "last_name")  var lastName: String?,
        var age:Int,
        @Ignore val picture: Bitmap?,
        @Embedded(prefix = "address_") val address: Address?
)

data class Address(
        val street: String?,
        val state: String?,
        val city: String?,
        @ColumnInfo(name = "post_code") val postCode: Int
)

data class NameTuple(
        @ColumnInfo(name = "first_name") val firstName: String?,
        @ColumnInfo(name = "last_name") val lastName: String?
)
