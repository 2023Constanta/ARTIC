package com.nightstalker.artic.features.artwork.domain.usecase

import com.nightstalker.artic.features.artwork.domain.model.Artwork
import com.nightstalker.artic.features.artwork.domain.model.ArtworkInformation
import com.nightstalker.artic.features.artwork.domain.repo.ArtworkRepo
import com.nightstalker.core.domain.model.ResultState
import com.nightstalker.core.domain.model.safeCall

/**
 * Юз кейс для получения результата с данными экспонатов
 * @author Tamerlan Mamukhov
 * @created 2022-10-16
 */
class ArtworksUseCase(
    private val repo: ArtworkRepo,
) {
    suspend fun getArtworkById(id: Int): ResultState<Artwork> = safeCall { repo.getArtworkById(id) }
    suspend fun getArtworks(): ResultState<List<Artwork>> = safeCall { repo.getArtworks() }
    suspend fun getArtworkInformation(id: Int): ResultState<ArtworkInformation> =
        safeCall { repo.getArtworkInformation(id) }

    suspend fun getArtworksByQuery(query: String): ResultState<List<Artwork>> =
        safeCall { repo.getArtworksByQuery(query) }

    suspend fun getNumber(query: String): ResultState<Int> = safeCall { repo.getNumber(query) }
}