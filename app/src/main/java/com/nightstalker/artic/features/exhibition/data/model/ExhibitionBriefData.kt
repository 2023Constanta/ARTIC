package com.nightstalker.artic.features.exhibition.data.model

import com.google.gson.annotations.SerializedName

data class ExhibitionBriefData(
    @SerializedName("id")
    val id: Int,
    @SerializedName("short_description")
    val shortDescription: String,
    @SerializedName("title")
    val title: String,
) {
}