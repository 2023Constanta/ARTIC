package com.nightstalker.artic.features.artwork.domain.repo

import com.nightstalker.artic.features.artwork.domain.model.Artwork
import com.nightstalker.artic.features.artwork.domain.model.ArtworkInformation

/**
 * Репозиторий получения экспонатов
 *
 * @author Tamerlan Mamukhov on 2022-09-13
 */
interface ArtworkRepo {
    suspend fun getArtworkById(id: Int): Artwork
    suspend fun getArtworks(): List<Artwork>
    suspend fun getArtworkInformation(id: Int): ArtworkInformation
    suspend fun getArtworksByQuery(search: String): List<Artwork>
    suspend fun getNumber(search: String): Int
}