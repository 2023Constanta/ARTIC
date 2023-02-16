package com.nightstalker.artic.features.artwork.data

import com.nightstalker.artic.features.artwork.data.api.ArtworksApi
import com.nightstalker.artic.features.artwork.data.mappers.toArtwork
import com.nightstalker.artic.features.artwork.data.mappers.toArtworkInformation
import com.nightstalker.artic.features.artwork.data.mappers.toListOfArtworks
import com.nightstalker.artic.features.artwork.domain.model.Artwork
import com.nightstalker.artic.features.artwork.domain.model.ArtworkInformation

/**
 * Класс получения данных с помощью [ArtworksApi]
 *
 * @property api    API
 * @author Tamerlan Mamukhov on 2022-09-16
 */
class ArtworksApiMapper(private val api: ArtworksApi) {
    suspend fun getArtWorkById(id: Int): Artwork = api.getArtworkById(id).data.toArtwork()

    suspend fun getArtworks(): List<Artwork> = api.getArtworks().data.toListOfArtworks()

    suspend fun getArtworkInformation(id: Int): ArtworkInformation =
        api.getArtworkInformation(id).toArtworkInformation()

    suspend fun getArtworksByQuery(search: String): List<Artwork> =
        api.getArtworksByQuery(search).data.map {
            api.getArtworkById(it.id).data
        }.toListOfArtworks()

    suspend fun getNumber(search: String): Int =
        api.getArtworksByQuery(search).pagination.total
}