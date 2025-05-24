package com.dicoding.producthub.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class ProductResponse(

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("title")
    val title: String,

    @field:SerializedName("price")
    val price: Double,

    @field:SerializedName("description")
    val description: String,

    @field:SerializedName("category")
    val category: String,

    @field:SerializedName("image")
    val image: String,

    )

