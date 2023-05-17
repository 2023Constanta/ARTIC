package com.nightstalker.artic.features.audio.domain.usecase

import com.nightstalker.artic.features.audio.domain.model.AudioFile
import com.nightstalker.artic.features.audio.domain.repo.AudioRepo
import com.nightstalker.core.domain.model.ResultState
import com.nightstalker.core.domain.model.safeCall

/**
 * @author Tamerlan Mamukhov
 * @created 2022-11-17
 */
class AudioUseCase(private val repo: AudioRepo) {
    suspend fun getAudioById(id: Int): ResultState<AudioFile> = safeCall { repo.getAudioById(id) }
    suspend fun getSoundByArtworkTitle(title: String): ResultState<AudioFile> =
        safeCall { repo.getSoundByArtworkTitle(title) }
}