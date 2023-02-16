package com.nightstalker.artic.features.exhibition.data.model


import com.google.gson.annotations.SerializedName

/**
 * Класс выставки
 */
data class ExhibitionData(
    @SerializedName("aic_end_at")
    val aicEndAt: String,
    @SerializedName("aic_start_at")
    val aicStartAt: String,
    @SerializedName("alt_image_ids")
    val altImageIds: List<String> = listOf(),
    @SerializedName("gallery_title")
    val galleryTitle: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("image_url")
    val imageUrl: String,
    @SerializedName("short_description")
    val shortDescription: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("title")
    val title: String,
)