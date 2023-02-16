package com.nightstalker.artic.features.artwork.data

import com.nightstalker.artic.features.artwork.domain.model.Artwork
import com.nightstalker.artic.features.artwork.domain.model.ArtworkInformation
import com.nightstalker.artic.features.artwork.domain.repo.ArtworkRepo

class ArtworkRepoImpl(
    private val apiMapper: ArtworksApiMapper
) : ArtworkRepo {
    override suspend fun getArtworkById(id: Int): Artwork =
        apiMapper.getArtWorkById(id)

    override suspend fun getArtworks(): List<Artwork> = apiMapper.getArtworks()

    override suspend fun getArtworkInformation(id: Int): ArtworkInformation =
        apiMapper.getArtworkInformation(id)

    override suspend fun getArtworksByQuery(search: String): List<Artwork> =
        apiMapper.getArtworksByQuery(search)

    override suspend fun getNumber(search: String): Int =
        apiMapper.getNumber(search)
}