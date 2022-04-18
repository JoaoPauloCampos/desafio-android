package com.picpay.desafio.android.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.picpay.desafio.android.data.local.entities.UserEntity

internal const val USER_TABLE_NAME = "user"

@Dao
interface UserDao {
    @Query("SELECT * FROM $USER_TABLE_NAME")
    fun getAll(): List<UserEntity>

    @Query("SELECT count(*) FROM $USER_TABLE_NAME")
    suspend fun count(): Int

    @Query("SELECT * FROM $USER_TABLE_NAME ORDER BY user_name ASC LIMIT :size OFFSET :offset")
    suspend fun getUsersPaginated(size: Int, offset: Int): List<UserEntity>

    @Insert
    fun insertAll(vararg users: UserEntity)

    @Query("DELETE FROM $USER_TABLE_NAME")
    suspend fun deleteAll()
}