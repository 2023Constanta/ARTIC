package com.nightstalker.artic.features.artwork.data.model

import com.google.gson.annotations.SerializedName

/**
 * Класс экспоната
 */
data class ArtworkData(
    @SerializedName("artist_display")
    val artistDisplay: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("image_id")
    val imageId: String,
    @SerializedName("place_of_origin")
    val placeOfOrigin: String,
    @SerializedName("sound_ids")
    val soundIds: List<String> = listOf(),
    @SerializedName("title")
    val title: String,
    @SerializedName("object_selector_number") val objectSelectorNumber: String? = null,
)