package com.nightstalker.artic.features.audio.data

import com.nightstalker.artic.features.artwork.data.api.ArtworksApi
import com.nightstalker.artic.features.audio.data.api.AudioApi
import com.nightstalker.artic.features.audio.data.mappers.toAudioFile
import com.nightstalker.artic.features.audio.domain.model.AudioFile

/**
 * Класс получения данных с помощью [ArtworksApi]
 *
 * @property api    API
 * @author Tamerlan Mamukhov on 2022-09-16
 */
class AudioApiMapper(private val api: AudioApi) {
    suspend fun getAudioById(id: Int): AudioFile = api.getSoundById(id).data.toAudioFile()

    suspend fun getSoundByArtworkTitle(title: String): AudioFile =
        api.getSoundByArtworkTitle(title).data.first().toAudioFile()
}