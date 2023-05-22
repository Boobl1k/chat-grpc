package com.example.android_clean_architecture.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.example.android_clean_architecture.R
import com.example.android_clean_architecture.view_model.MyBookViewModel

class BooksRecyclerAdapter(
    private val viewModel: MyBookViewModel,
    private val lifecycleOwner: LifecycleOwner
) :
    RecyclerView.Adapter<BooksRecyclerAdapter.MyViewHolder>() {

    private var itemCount = 1
    init {
        viewModel.booksDataMutable.observe(lifecycleOwner) {
            if(it != null) itemCount = it.size
        }
    }
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(R.id.book_title)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder =
        MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.books_list_item, parent, false)
        )

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        viewModel.booksDataMutable.observe(lifecycleOwner) {
            if (it == null) holder.textView.text = "Loading"
            else holder.textView.text = it[position].title
        }
    }

    override fun getItemCount() = itemCount
}