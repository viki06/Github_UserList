package com.vigneshkumarapps.githubusers.main_activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vigneshkumarapps.githubusers.repository.RepositoryClass
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivityViewModel : ViewModel() {

    val repository = RepositoryClass()
    fun getUserList() = repository.userLiveData
    var page = 0
    var isOnline = true

    fun getUser(online: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            if (online) {
                repository.getUser(page)
            }else{
                repository.getOfflineUser()
            }
        }
    }


}