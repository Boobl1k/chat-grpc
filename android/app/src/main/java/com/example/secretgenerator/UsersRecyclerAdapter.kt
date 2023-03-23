package com.example.secretgenerator

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class UsersRecyclerAdapter(private val entityArrayList: ArrayList<UserEntity>) :
    RecyclerView.Adapter<UsersRecyclerAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val username: TextView
        val name: TextView
        val email: TextView

        init {
            username = view.findViewById(R.id.cardUsernameTextView)
            name = view.findViewById(R.id.cardNameTextView)
            email = view.findViewById(R.id.cardEmailTextView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.users_row_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = entityArrayList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name.text = entityArrayList[position].name
        holder.username.text = entityArrayList[position].username
        holder.email.text = entityArrayList[position].email
    }
}