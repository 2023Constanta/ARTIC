package com.nightstalker.artic.features.artwork.data.model.information


import com.google.gson.annotations.SerializedName

data class Description(
    @SerializedName("language")
    val language: String,
    @SerializedName("value")
    val value: String
)