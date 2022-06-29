package com.vigneshkumarapps.githubusers.main_activity

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.vigneshkumarapps.githubusers.CommonMethods
import com.vigneshkumarapps.githubusers.R
import com.vigneshkumarapps.githubusers.pogo.UserData
import com.vigneshkumarapps.githubusers.pogo.UserList
import kotlinx.android.synthetic.main.custom_user_list.view.*

class UserListAdapter() : RecyclerView.Adapter<UserListAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    var listener : onItemSelectListener? = null
    lateinit var context : Context
    var userList = ArrayList<UserData>()
    var userMainList = ArrayList<UserData>()

    constructor( context: Context, userList: UserList):this(){
        this.context = context
        this.userList.addAll(userList)
        this.userMainList.addAll(userList)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.custom_user_list,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.profile_name.text = userList.get(position).login
        holder.itemView.details.text = "Type : " +userList.get(position).type
        Glide
            .with(context)
            .load(userList.get(position).avatar_url)
            .placeholder(R.drawable.pro_pic)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .circleCrop()
            .into(holder.itemView.image)
        holder.itemView.setOnClickListener {
            listener?.onItemSelct(userList.get(position).login)
        }
        if(CommonMethods().nullHandleString(userList.get(position).note) == ""){
            holder.itemView.note_img.visibility = View.GONE
        }else{
            holder.itemView.note_img.visibility = View.VISIBLE
        }
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    fun filter(p0: String?) {
        userMainList
        userList.clear()
        for (i in userMainList){
            if(i.login.lowercase().contains(p0!!.lowercase())){
                userList.add(i)
            }
        }

        notifyDataSetChanged()
    }

    fun getLastItem() {
        listener?.lastItem(userMainList.get(userMainList.size-1).id)
    }

    fun updateList(it: UserList?) {
        for(i in it!!){
            userList.add(i)
            userMainList.add(i)
        }
        notifyDataSetChanged()
    }


    interface onItemSelectListener{
        fun onItemSelct(login: String)
        fun lastItem(id : Int)
    }
}