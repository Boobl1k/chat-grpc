package com.example.android_clean_architecture.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.example.android_clean_architecture.R
import com.example.android_clean_architecture.view_model.MyBookViewModel

class BooksRecyclerAdapter(
    private val viewModel: MyBookViewModel,
    private val lifecycleOwner: LifecycleOwner,
    private val token: String,
    private val context: Context?
) :
    RecyclerView.Adapter<BooksRecyclerAdapter.MyViewHolder>() {

    private var itemCount = 1

    init {
        viewModel.booksDataMutable.observe(lifecycleOwner) {
            if (it != null) itemCount = it.size
        }
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(R.id.book_title)
        val openBookButton: Button = itemView.findViewById(R.id.open_book_button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder =
        MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.books_list_item, parent, false)
        )

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        viewModel.booksDataMutable.observe(lifecycleOwner) {
            if (it == null) holder.textView.text = "Loading"
            else {
                holder.textView.text = it[position].title

                holder.openBookButton.setOnClickListener { _ ->
                    viewModel.getBookDetails(token, it[position].id)
                    viewModel.bookDetailsDataMutableMap[it[position].id]!!.observe(lifecycleOwner) { bookData ->
                        Toast.makeText(
                            context,
                            "Opened book ${bookData.title}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }

    override fun getItemCount() = itemCount
}