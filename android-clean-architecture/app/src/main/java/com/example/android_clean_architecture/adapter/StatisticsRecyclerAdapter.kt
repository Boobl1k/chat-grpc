package com.example.android_clean_architecture.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.example.android_clean_architecture.R
import com.example.android_clean_architecture.view_model.MyBookViewModel

class StatisticsRecyclerAdapter(
    private val viewModel: MyBookViewModel,
    private val lifecycleOwner: LifecycleOwner,
    private val token: String,
    private val context: Context?
) :
    RecyclerView.Adapter<StatisticsRecyclerAdapter.MyViewHolder>() {

    private var itemCount = 1

    init {
        viewModel.booksDataMutable.observe(lifecycleOwner) {
            if (it != null) itemCount = it.size
        }
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val bookTitleTextView: TextView = itemView.findViewById(R.id.statistics_book_title)
        val readCountTextView: TextView = itemView.findViewById(R.id.read_count)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder =
        MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.statistics_list_item, parent, false)
        )

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var id: String? = null
        viewModel.booksDataMutable.observe(lifecycleOwner) {
            if (it == null) holder.bookTitleTextView.text = "Loading"
            else {
                id = it[position].id
                holder.bookTitleTextView.text = it[position].title
                holder.readCountTextView.text = "0"
            }
        }
        if(id != null)
            viewModel.statisticsDataMutableMap[id]!!.observe(lifecycleOwner) {
                holder.readCountTextView.text = it.readCount.toString()
            }
    }

    override fun getItemCount() = itemCount
}