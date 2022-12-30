package com.example.book.model



data class BookModel(
    var title: String,
    var subtitle: String,
    var authors: ArrayList<String>,
    var publisher: String,
    var publishedDate: String,
    var description: String,
    var thumbnail: Int,
    var pageCount: String?,
    var previewLink: String,
    var infoLink: String,
    var buyLink: String,

    )