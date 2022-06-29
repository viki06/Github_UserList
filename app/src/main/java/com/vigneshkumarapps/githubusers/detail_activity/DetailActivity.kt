package com.vigneshkumarapps.githubusers.detail_activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.vigneshkumarapps.githubusers.CommonMethods
import com.vigneshkumarapps.githubusers.R
import com.vigneshkumarapps.githubusers.databinding.ActivityDetailBinding
import com.vigneshkumarapps.githubusers.pogo.UserDetailData
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity(){
    lateinit var viewModel: DetailActivityViewModel
    lateinit var binding : ActivityDetailBinding
    val commonMethods = CommonMethods()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(DetailActivityViewModel::class.java)
        binding = DataBindingUtil.setContentView<ActivityDetailBinding>(this,R.layout.activity_detail)
        binding.viewModel = viewModel

        viewModel.login = intent.getStringExtra("login")!!



        if(commonMethods.isNetworkAvailable(this)) {
            viewModel.getUserDetails(true)
        }else{
            commonMethods.alertDialog(this,"No Internet! Please check internet connection")
            viewModel.getUserDetails(false)
        }
        back.setOnClickListener {
            finish()
        }

        save.setOnClickListener {
            if(note.text.toString().trim() == ""){
                CommonMethods().alertDialog(this,"Please enter note before save")
            }else{
                viewModel.saveNote()
                Toast.makeText(this,"Note saved",Toast.LENGTH_SHORT).show()
            }

        }

        viewModel.getDetailData().observe(this, Observer {
            title_name.setText(viewModel.login)
            if (it?.id == null) {
                nodata.visibility = View.VISIBLE
                details_layout.visibility = View.GONE
            } else {
                nodata.visibility = View.GONE
                details_layout.visibility = View.VISIBLE

                viewModel.name += it.login
                viewModel.company += it.company
                viewModel.blog += it.blog
                viewModel.follower += it.followers
                viewModel.following += it.following

                name.setText(viewModel.name)
                followers.setText(viewModel.follower)
                followings.setText(viewModel.following)
                company.setText(viewModel.company)
                blog.setText(viewModel.blog)
                Glide
                    .with(this)
                    .load(it.avatar_url)
                    .placeholder(R.drawable.pro_pic)
                    .circleCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .circleCrop()
                    .into(image)
            }
        })

        viewModel.getUserNote().observe(this, Observer {
            if (it != null) {
                note.setText(it.Note)
            }
        })



    }

}