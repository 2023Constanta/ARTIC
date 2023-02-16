package com.nightstalker.artic.features.artwork.data.mappers

import com.nightstalker.artic.features.artwork.data.model.ArtworkData
import com.nightstalker.artic.features.artwork.data.model.information.ArtworkInformationModel
import com.nightstalker.artic.features.artwork.domain.model.Artwork
import com.nightstalker.artic.features.artwork.domain.model.ArtworkInformation

/**
 * Функции для преобразования данных из дата слоя в домайн
 *
 * @author Tamerlan Mamukhov
 */
fun ArtworkData.toArtwork(): Artwork =
    Artwork(
        id = id,
        title = title,
        imageId = imageId,
        artist = artistDisplay,
        audioUrl = soundIds.firstOrNull(),
        placeOfOrigin = placeOfOrigin
    )

fun List<ArtworkData>.toListOfArtworks(): List<Artwork> =
    map {
        Artwork(
            id = it.id,
            title = it.title,
            imageId = it.imageId,
            artist = it.artistDisplay,
            audioUrl = it.objectSelectorNumber,
            placeOfOrigin = it.placeOfOrigin
        )
    }


fun ArtworkInformationModel.toArtworkInformation(): ArtworkInformation =
    ArtworkInformation(
        description = description.first().value
    )