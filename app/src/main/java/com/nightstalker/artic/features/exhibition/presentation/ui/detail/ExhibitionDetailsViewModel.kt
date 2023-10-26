package com.nightstalker.artic.features.exhibition.presentation.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nightstalker.artic.features.exhibition.domain.usecase.ExhibitionsUseCase
import com.nightstalker.core.presentation.ext.viewModelCall
import com.nightstalker.core.presentation.model.ContentResultState
import kotlinx.coroutines.CoroutineDispatcher

/**
 * Вью модель для получения экспоната
 *
 * @property useCase   репозиторий
 * @author Tamerlan Mamukhov on 2022-09-17
 */
class ExhibitionDetailsViewModel(
    private val useCase: ExhibitionsUseCase,
    private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    private var _exhibition = MutableLiveData<ContentResultState>()
    val exhibition: LiveData<ContentResultState> get() = _exhibition
    fun getExhibition(id: Int) {
        _exhibition.value = ContentResultState.Loading
        viewModelCall(
            dispatcher = ioDispatcher,
            call = { useCase.getExhibitionById(id) },
            contentResultState = _exhibition
        )
    }
}