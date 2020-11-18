package com.gas.test.ui.fragment.room.data

import androidx.room.*


@Dao
interface UserDao {

    @Insert
    fun insertUser(user:User):Long

    @Insert
    fun insertVarargUser(vararg users: User):List<Long>

    @Insert
    fun insertUsers(users: List<User>)

    @Update
    fun updateUsers(vararg users: User)

    @Delete
    fun deleteUsers(vararg users: User)

    @Query("SELECT * FROM users")
    fun loadAllUsers(): Array<User>

    @Query("SELECT * FROM users WHERE age > :minAge")
    fun loadAllUsersOlderThan(minAge: Int): Array<User>

    @Query("SELECT * FROM users WHERE age BETWEEN :minAge AND :maxAge")
    fun loadAllUsersBetweenAges(minAge: Int, maxAge: Int): Array<User>

    @Query("SELECT * FROM users WHERE first_name LIKE :search OR last_name LIKE :search")
    fun findUserWithName(search: String): List<User>

    //从查询中返回任何基于 Java 的对象，前提是结果列集合会映射到返回的对象
    @Query("SELECT first_name, last_name FROM users")
    fun loadFullName(): List<NameTuple>

}