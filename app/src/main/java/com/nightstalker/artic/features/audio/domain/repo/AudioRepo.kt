package com.nightstalker.artic.features.audio.domain.repo

import com.nightstalker.artic.features.audio.domain.model.AudioFile

/**
 * @author Tamerlan Mamukhov
 * @created 2022-11-17
 */
interface AudioRepo {
    suspend fun getAudioById(id: Int): AudioFile
    suspend fun getSoundByArtworkTitle(title: String): AudioFile
}