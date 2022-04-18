package com.picpay.desafio.android.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.picpay.desafio.android.data.local.UserDao
import com.picpay.desafio.android.data.local.database.AppDatabase.Companion.VERSION
import com.picpay.desafio.android.data.local.entities.UserEntity

@Database(entities = [UserEntity::class], version = VERSION)
abstract class AppDatabase: RoomDatabase() {
    companion object {
        const val VERSION = 1
        const val FILE_NAME = "pic_pay.db"
    }
    abstract fun userDao(): UserDao
}