package com.nightstalker.artic.features.audio.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nightstalker.artic.core.presentation.ext.viewModelCall
import com.nightstalker.artic.core.presentation.model.ContentResultState
import com.nightstalker.artic.features.audio.domain.model.AudioFile
import com.nightstalker.artic.features.audio.domain.usecase.AudioUseCase
import com.nightstalker.artic.features.audio.player.AudioPlayerService

/**
 * ВЬю-модель для получения [AudioFile], файла с данными о аудио экспоната
 *
 * @property audioUseCase   юз-кейс получения данных
 * @author Tamerlan Mamukhov on 2022-10-04
 */
class AudioViewModel(
    private val audioUseCase: AudioUseCase,
) : ViewModel() {

    private val _binder = MutableLiveData<AudioPlayerService.NewAudioPlayerServiceBinder>()
    val binder get() = _binder

    private val _audioFileContentState = MutableLiveData<ContentResultState>()
    val audioFileContentState get() = _audioFileContentState

    private var _audioNumber = MutableLiveData(0)
    val audioNumber get() = _audioNumber

    var audioNumber2 = MutableLiveData(0)


//    private

    fun performSearchById(id: Int) {
        _audioNumber.value = id
        getSoundById(id)
    }

    fun getSoundById(id: Int) =
        viewModelCall(
            call = { audioUseCase.getAudioById(id) },
            contentResultState = _audioFileContentState
        )

    fun getSoundByTitle(title: String) =
        viewModelCall(
            call = { audioUseCase.getSoundByArtworkTitle(title) },
            contentResultState = _audioFileContentState
        )
}