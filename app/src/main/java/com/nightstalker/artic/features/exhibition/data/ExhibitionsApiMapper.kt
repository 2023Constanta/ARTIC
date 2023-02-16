package com.nightstalker.artic.features.exhibition.data

import com.nightstalker.artic.features.artwork.data.api.ArtworksApi
import com.nightstalker.artic.features.exhibition.data.api.ExhibitionsApi
import com.nightstalker.artic.features.exhibition.data.mappers.toExhibition

/**
 * Класс получения данных с помощью [ArtworksApi]
 *
 * @property api    API
 * @author Tamerlan Mamukhov on 2022-09-16
 */
class ExhibitionsApiMapper(private val api: ExhibitionsApi) {
    suspend fun getExhibitionById(id: Int) = api.getExhibitionById(id).data.toExhibition()

    suspend fun getExhibitions() =
        api.getExhibitions().data.filter { it.imageUrl != null }.map { it.toExhibition() }
}