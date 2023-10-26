package com.nightstalker.artic.features.exhibition.presentation.ui.list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nightstalker.artic.features.exhibition.domain.usecase.ExhibitionsUseCase
import com.nightstalker.core.presentation.ext.viewModelCall
import com.nightstalker.core.presentation.model.ContentResultState
import kotlinx.coroutines.CoroutineDispatcher

/**
 * Вью модель для получения экспонатов
 *
 * @property useCase   репозиторий
 * @author Tamerlan Mamukhov on 2022-09-17
 */
class ExhibitionsListViewModel(
    private val useCase: ExhibitionsUseCase,
    private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    private var _exhibitionsContentState =
        MutableLiveData<ContentResultState>(ContentResultState.Loading)
    val exhibitionsContentState get() = _exhibitionsContentState

    fun getExhibitions() {
        viewModelCall(
            dispatcher = ioDispatcher,
            call = { useCase.getExhibitions() },
            contentResultState = _exhibitionsContentState
        )
    }

    fun loadExhibitions() {
        if (_exhibitionsContentState.value is ContentResultState.Loading) {
            getExhibitions()
        }
    }

}