package com.nightstalker.artic.features.artwork.data.model.information


import com.google.gson.annotations.SerializedName

/**
 * Класс описания экспоната
 */
data class ArtworkInformationModel(
    @SerializedName("description")
    val description: List<Description>
)