package com.vigneshkumarapps.githubusers.main_activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.vigneshkumarapps.githubusers.CommonMethods
import com.vigneshkumarapps.githubusers.PaginationScrollListener
import com.vigneshkumarapps.githubusers.R
import com.vigneshkumarapps.githubusers.detail_activity.DetailActivity
import com.vigneshkumarapps.githubusers.roomdb.UserDao
import com.vigneshkumarapps.githubusers.roomdb.UserDatabase
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), UserListAdapter.onItemSelectListener {

    lateinit var viewModel: MainActivityViewModel
    lateinit var adapter: UserListAdapter
    lateinit var layoutManager : LinearLayoutManager
    var isLoading = true
    val commonMethods = CommonMethods()

    companion object {
        lateinit var userDao: UserDao
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)
        val db = Room.databaseBuilder(applicationContext,UserDatabase::class.java,"OfflineDB").build()
        userDao = db.userDao()
        searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                adapter.filter(p0)
                if(p0 == ""){
                    isLoading = true
                }else{
                    isLoading = false
                }
                return true
            }

        })
        layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        recyclerView?.addOnScrollListener(object : PaginationScrollListener(layoutManager) {
            override fun loadMoreItems() {
               if(isLoading) {
                   adapter.getLastItem()
               }
            }
        })
        viewModel.getUserList().observe(this, Observer {
            if(viewModel.page == 0) {
                adapter = UserListAdapter(this, it)
                adapter.listener = this
                recyclerView.adapter = adapter
            }else{
                adapter.updateList(it)
            }
        })
        if(commonMethods.isNetworkAvailable(this)) {
            viewModel.isOnline = true
            viewModel.getUser(viewModel.isOnline)
        }else{
            isLoading = false
            viewModel.isOnline = false
            commonMethods.alertDialog(this,"No Internet! Please check internet connection")
            viewModel.getUser(viewModel.isOnline)
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getUser(viewModel.isOnline)
    }

    override fun onItemSelct(login: String) {
        startActivity(Intent(this,DetailActivity::class.java).putExtra("login",login))
    }

    override fun lastItem(id: Int) {
        viewModel.page = id
        viewModel.getUser(viewModel.isOnline)
    }


}