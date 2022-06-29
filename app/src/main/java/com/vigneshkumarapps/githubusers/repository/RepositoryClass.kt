package com.vigneshkumarapps.githubusers.repository

import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.vigneshkumarapps.githubusers.CommonMethods
import com.vigneshkumarapps.githubusers.main_activity.MainActivity.Companion.userDao
import com.vigneshkumarapps.githubusers.pogo.UserDetailData
import com.vigneshkumarapps.githubusers.pogo.UserList
import com.vigneshkumarapps.githubusers.pogo.UserNoteData

class RepositoryClass {

    val commonMethods = CommonMethods()
    val gson = Gson()
    val userLiveData = MutableLiveData<UserList>()
    val userDetailLiveData = MutableLiveData<UserDetailData>()
    val usersNoteLiveData = MutableLiveData<ArrayList<UserNoteData>>()
    val userNoteLiveData = MutableLiveData<UserNoteData>()


    fun getUser(s : Int){
        val response = commonMethods.serviceMethodUserList("users?since="+s)
        val list = gson.fromJson(response, UserList::class.java)
        for (i in list.indices){
            val notedata : UserNoteData? = getUserNote(list.get(i).login)
            if(notedata != null) {
                if (commonMethods.nullHandleString(notedata.Note) != "") {
                    list.get(i).note = commonMethods.nullHandleString(notedata.Note)
                }
            }
        }
        userLiveData.apply { postValue(list) }
        for (i in list){
            userDao.insertOrUpdate(i)
            val note = getUserNote(i.login)
        }

    }
    fun getOfflineUser() {
        val list = userDao.getAll()
        val arrayList = UserList()
        arrayList.addAll(list)
        for (i in arrayList.indices){
            val notedata = getUserNote(arrayList.get(i).login)
            if(notedata != null) {
                if (commonMethods.nullHandleString(notedata.Note) != "") {
                    arrayList.get(i).note = notedata.Note
                }
            }
        }
        userLiveData.apply { postValue(arrayList) }
    }

    fun getUserDetails(login: String) {
        val response = commonMethods.serviceMethodUserDetails("users/"+login)
        val list = gson.fromJson(response, UserDetailData::class.java)
        userDao.insertOrUpdate(list)
        userDetailLiveData.apply { postValue(list) }
        getUserNote(login)

    }

    fun getOfflineUserDetails(login: String) {
        val list = userDao.getDetail(login)
        userDetailLiveData.apply { postValue(list) }
        getUserNote(login)
    }

    fun saveNote(note: String, login: String) {
        userDao.insertOrUpdate(UserNoteData(login,note))
    }

    fun getUserNote(login: String): UserNoteData? {
        val list = userDao.getNoteDetail(login)
        userNoteLiveData.apply { postValue(list) }
        return list
    }


}