package com.example.book.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.book.BookActivity
import com.example.book.R
import com.example.book.model.BookModel
import com.squareup.picasso.Picasso

class BookAdapter(

    private var bookList: ArrayList<BookModel>,
    private var context: Context
) : RecyclerView.Adapter<BookAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.activity_item,
            parent, false
        )

        return ViewHolder(itemView)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val bookTitle: TextView = itemView.findViewById(R.id.BookName)
        val bookPages: TextView = itemView.findViewById(R.id.BookPages)
        val book: ImageView = itemView.findViewById(R.id.Book)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val bookInfo = bookList.get(position)

        Picasso.get().load(bookInfo.thumbnail).into(holder.book);
        holder.bookTitle.text = bookInfo.title
        holder.bookPages.text = "Pages : " + bookInfo.pageCount


        holder.itemView.setOnClickListener {

            val i = Intent(context, BookActivity::class.java)
            i.putExtra("title", bookInfo.title)
            i.putExtra("subtitle", bookInfo.subtitle)
            i.putExtra("authors", bookInfo.authors)
            i.putExtra("publisher", bookInfo.publisher)
            i.putExtra("publishedDate", bookInfo.publishedDate)
            i.putExtra("description", bookInfo.description)
            i.putExtra("pageCount", bookInfo.pageCount)
            i.putExtra("thumbnail", bookInfo.thumbnail)



            context.startActivity(i)
        }

    }




    override fun getItemCount(): Int {
        return bookList.size
    }






}