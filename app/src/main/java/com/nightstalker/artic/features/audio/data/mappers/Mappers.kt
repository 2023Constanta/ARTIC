package com.nightstalker.artic.features.audio.data.mappers

import com.nightstalker.artic.features.audio.data.model.MobileSoundData
import com.nightstalker.artic.features.audio.domain.model.AudioFile

/**
 * Функции для преобразования данных из дата слоя в домайн
 *
 * @author Tamerlan Mamukhov
 */
fun MobileSoundData.toAudioFile(): AudioFile =
    AudioFile(title, webUrl, transcript)
