package com.nightstalker.artic.features.audio.data

import com.nightstalker.artic.features.audio.domain.model.AudioFile
import com.nightstalker.artic.features.audio.domain.repo.AudioRepo

/**
 * @author Tamerlan Mamukhov
 * @created 2022-11-17
 */
class AudioRepoImpl(
    private val apiMapper: AudioApiMapper,
) : AudioRepo {

    override suspend fun getAudioById(id: Int): AudioFile = apiMapper.getAudioById(id)
    override suspend fun getSoundByArtworkTitle(title: String): AudioFile =
        apiMapper.getSoundByArtworkTitle(title)

}