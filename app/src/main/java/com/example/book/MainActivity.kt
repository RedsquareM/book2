package com.example.book

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.book.adapter.BookAdapter
import com.example.book.model.BookModel

class MainActivity : AppCompatActivity() {



    //
    private lateinit var mRequestQueue: RequestQueue
    private lateinit var booksList: ArrayList<BookModel>
    private lateinit var loading: ProgressBar
    private lateinit var searchEdit: EditText
    private lateinit var searchBtn: ImageButton



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loading = findViewById(R.id.Loading)
        searchEdit = findViewById(R.id.EdtSearchBooks)
        searchBtn = findViewById(R.id.BtnSearch)


        searchBtn.setOnClickListener {
            loading.visibility = View.VISIBLE

            if (searchEdit.text.toString().isEmpty()) {
                searchEdit.error = "Please enter search query"
            }
            // the books from the API.
            getBooksData(searchEdit.text.toString())
        }

    }

    private fun getBooksData(searchQuery: String) {
        booksList = ArrayList()


        mRequestQueue = Volley.newRequestQueue(this@MainActivity)


        mRequestQueue.cache.clear()

        // url for getting data from API in json format.
        val url = "https://www.googleapis.com/books/v1/volumes?q=$searchQuery"


        val queue = Volley.newRequestQueue(this@MainActivity)

        
        val request = JsonObjectRequest(Request.Method.GET, url, null, { response ->
            loading.visibility = View.GONE

            try {
                val itemsArray = response.getJSONArray("items")
                for (i in 0 until itemsArray.length()) {
                    val itemsObj = itemsArray.getJSONObject(i)
                    val volumeObj = itemsObj.getJSONObject("volumeInfo")
                    val title = volumeObj.optString("title")
                    val subtitle = volumeObj.optString("subtitle")
                    val authorsArray = volumeObj.getJSONArray("authors")
                    val publisher = volumeObj.optString("publisher")
                    val publishedDate = volumeObj.optString("publishedDate")
                    val description = volumeObj.optString("description")
                    val pageCount = volumeObj.optInt("pageCount")
                    val imageLinks = volumeObj.optJSONObject("imageLinks")
                    val thumbnail = imageLinks?.optString("thumbnail")
                    val previewLink = volumeObj.optString("previewLink")
                    val infoLink = volumeObj.optString("infoLink")
                    val saleInfoObj = itemsObj.optJSONObject("saleInfo")
                    val buyLink = saleInfoObj?.optString("buyLink")
                    val authorsArrayList: ArrayList<String> = ArrayList()
                    if (authorsArray.length() != 0) {
                        for (j in 0 until authorsArray.length()) {
                            authorsArrayList.add(authorsArray.optString(i))
                        }
                    }


                    val bookInfo = buyLink?.let {
                        BookModel(
                            title,
                            subtitle,
                            authorsArrayList,
                            publisher,
                            publishedDate,
                            description,
                            pageCount,
                            thumbnail,
                            previewLink,
                            infoLink,
                            it
                        )
                    }

                    if (bookInfo != null) {
                        booksList.add(bookInfo)
                    }


                    val adapter = BookAdapter(booksList, this@MainActivity)


                    val layoutManager = GridLayoutManager(this, 3)
                    val mRecyclerView = findViewById<RecyclerView>(R.id.RecyleViewBooks)


                    mRecyclerView.layoutManager = layoutManager
                    mRecyclerView.adapter = adapter

                }
            } catch (e: Exception) {
                e.printStackTrace()
            }


        }, {

            Toast.makeText(this@MainActivity, "No books found..", Toast.LENGTH_SHORT)
                .show()
        })
        queue.add(request)


    }
}