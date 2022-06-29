package com.vigneshkumarapps.githubusers.roomdb

import androidx.room.*
import com.vigneshkumarapps.githubusers.pogo.UserData
import com.vigneshkumarapps.githubusers.pogo.UserDetailData
import com.vigneshkumarapps.githubusers.pogo.UserNoteData

@Dao
interface UserDao {

    @Insert
    fun insert(vararg userListData: UserData)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(userListData: UserData)

    @Query("SELECT id FROM UserData WHERE id = :uid LIMIT 1")
    fun getItemId(uid: Int): Int?

    @Query("SELECT * FROM UserData")
    fun getAll(): List<UserData>

    fun insertOrUpdate(userListData: UserData) {
        val id = getItemId(userListData.id)

        if(id == null) {
            insert(userListData)
        }
        else {
            update(userListData)
        }

    }

    @Insert
    fun insert(vararg userListData: UserDetailData)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(userListData: UserDetailData)

    @Query("SELECT id FROM UserDetailData WHERE id = :uid LIMIT 1")
    fun getId(uid: Int): Int?

    @Query("SELECT * FROM UserDetailData WHERE login = :login LIMIT 1")
    fun getDetail(login: String): UserDetailData

    fun insertOrUpdate(userListData: UserDetailData) {
        val id = getId(userListData.id)

        if(id == null) {
            insert(userListData)
        }
        else {
            update(userListData)
        }

    }

    @Insert
    fun insert(vararg userListData: UserNoteData)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(userListData: UserNoteData)

    @Query("SELECT login FROM UserNoteData WHERE login = :login LIMIT 1")
    fun getnoteId(login: String): String?

    @Query("SELECT * FROM UserNoteData")
    fun getNoteDetailList(): List<UserNoteData>

    @Query("SELECT * FROM UserNoteData WHERE login = :login LIMIT 1")
    fun getNoteDetail(login: String): UserNoteData?

    fun insertOrUpdate(userListData: UserNoteData) {
        val id = getnoteId(userListData.login)

        if(id == null) {
            insert(userListData)
        }
        else {
            update(userListData)
        }

    }
}