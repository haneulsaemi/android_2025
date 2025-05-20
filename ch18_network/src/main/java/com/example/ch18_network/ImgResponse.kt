package com.example.ch18_network

class ItemModel (
    var id: Long = 0,
    var author: String? = null,
    var title: String? = null,
    var description: String? = null,
    var urlToImage: String? = null,
    var publishedAt: String? = null
)

class ImgResponse {
    var articles: MutableList<ItemModel>? = null
}
