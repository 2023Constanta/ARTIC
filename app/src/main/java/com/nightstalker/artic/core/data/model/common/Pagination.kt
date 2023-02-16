package com.nightstalker.artic.core.data.model.common


import com.google.gson.annotations.SerializedName

data class Pagination(
    @SerializedName("current_page")
    val currentPage: Int,
    @SerializedName("limit")
    val limit: Int,
    @SerializedName("next_url")
    val nextUrl: String,
    @SerializedName("offset")
    val offset: Int,
    @SerializedName("total")
    val total: Int,
    @SerializedName("total_pages")
    val totalPages: Int
)