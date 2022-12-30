package com.example.book

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso

class BookActivity : AppCompatActivity() {

    private lateinit var title: TextView
    private lateinit var subtitle: TextView
    private lateinit var publisher: TextView
    private lateinit var desc: TextView
    private lateinit var page: TextView
    private lateinit var publisherDate: TextView
    private lateinit var previewBtn: Button
    private lateinit var buyBtn: Button
    private lateinit var book: ImageView

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book)


        title = findViewById(R.id.title)
        subtitle = findViewById(R.id.subtitle)
        publisher = findViewById(R.id.publisher)
        desc = findViewById(R.id.description)
        page = findViewById(R.id.NoOfPages)
        publisherDate = findViewById(R.id.PublishDate)
        previewBtn = findViewById(R.id.Preview)
        buyBtn = findViewById(R.id.BtnBuy)
        book = findViewById(R.id.Book)



            val title = intent.getStringExtra("title")
        val subtitle = intent.getStringExtra("subtitle")
        val publisher: String? = intent.getStringExtra("publisher")
        val publishedDate: String? = intent.getStringExtra("publishedDate")
        val description: String? = intent.getStringExtra("description")
        val pageCount: Int = intent.getIntExtra("pageCount", 0)
        val thumbnail: String? = intent.getStringExtra("thumbnail")
        val previewLink = intent.getStringExtra("previewLink")
        //val buyLink = intent.getStringExtra("buyLink")  


        publisherDate.text = "Published On : $publishedDate"
        desc.text = description
        page.text = "No Of Pages : $pageCount"
        Picasso.get().load(thumbnail).into(book)


        previewBtn.setOnClickListener {
            if (previewLink.isNullOrEmpty()) {
                Toast.makeText(
                    this@BookActivity,
                    "Nothing to Display ",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }

            val uri: Uri = Uri.parse(previewLink)
            val i = Intent(Intent.ACTION_VIEW, uri)
            startActivity(i)
        }




    }
}