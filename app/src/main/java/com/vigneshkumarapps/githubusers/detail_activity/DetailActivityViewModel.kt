package com.vigneshkumarapps.githubusers.detail_activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vigneshkumarapps.githubusers.pogo.UserDetailData
import com.vigneshkumarapps.githubusers.repository.RepositoryClass
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailActivityViewModel : ViewModel() {
    var login = ""
    val repository = RepositoryClass()

    fun getDetailData() = repository.userDetailLiveData
    fun getUserNote() = repository.userNoteLiveData


    var name = "Name : "
    var company = "Company : "
    var blog = "Blog : "
    var note = ""
    var follower = "Followers : "
    var following = "Followings : "


    fun getUserDetails(isOnline : Boolean){
        viewModelScope.launch(Dispatchers.IO) {
            if(isOnline) {
                repository.getUserDetails(login)
            }else{
                repository.getOfflineUserDetails(login)
            }

        }
    }

    fun saveNote() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.saveNote(note,login)
        }
    }



}