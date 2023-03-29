package com.example.secretgenerator

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TodosRecyclerAdapter(private val entityArrayList: ArrayList<TodoEntity>) :
    RecyclerView.Adapter<TodosRecyclerAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val username: TextView
        val title: TextView
        val completed: TextView

        init {
            username = view.findViewById(R.id.todosCardUsernameTextView)
            title = view.findViewById(R.id.todosCardTitleTextView)
            completed = view.findViewById(R.id.todosCardCompletedTextView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.todos_row_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = entityArrayList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.title.text = entityArrayList[position].title
        holder.username.text = entityArrayList[position].userId.toString()
        holder.completed.text = entityArrayList[position].completed.toString()
    }
}