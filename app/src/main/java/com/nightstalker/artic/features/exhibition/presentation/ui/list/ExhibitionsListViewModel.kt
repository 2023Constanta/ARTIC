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

    private var _exhibitions =
        MutableLiveData<ContentResultState>(ContentResultState.Loading)
    val exhibitions get() = _exhibitions

    fun getExhibitions() {
        viewModelCall(
            dispatcher = ioDispatcher,
            call = { useCase.getExhibitions() },
            contentResultState = _exhibitions
        )
    }

    fun loadExhibitions() {
        if (_exhibitions.value is ContentResultState.Loading) {
            getExhibitions()
        }
    }

}