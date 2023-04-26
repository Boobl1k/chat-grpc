package com.example.android_clean_architecture.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.example.android_clean_architecture.R
import com.example.android_clean_architecture.view_model.SecondViewModel

class CustomRecyclerAdapter(
    private val viewModel: SecondViewModel,
    private val lifecycleOwner: LifecycleOwner
) :
    RecyclerView.Adapter<CustomRecyclerAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(R.id.textView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder =
        MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.todo_list_item, parent, false)
        )

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        viewModel.getTodoData(position).observe(lifecycleOwner) {
            if (it == null) holder.textView.text = "Loading"
            else holder.textView.text = it.title
        }
    }

    override fun getItemCount() = 10000
}