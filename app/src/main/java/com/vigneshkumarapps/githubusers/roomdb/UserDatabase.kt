package com.vigneshkumarapps.githubusers.roomdb

import androidx.room.Database
import androidx.room.RoomDatabase
import com.vigneshkumarapps.githubusers.pogo.UserData
import com.vigneshkumarapps.githubusers.pogo.UserDetailData
import com.vigneshkumarapps.githubusers.pogo.UserNoteData


@Database(entities = [UserData::class, UserDetailData::class, UserNoteData::class], version = 1)
abstract class UserDatabase : RoomDatabase() {
    abstract fun userDao() : UserDao
}