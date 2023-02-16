package com.nightstalker.artic.features.exhibition.presentation.ui.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nightstalker.artic.core.presentation.ext.viewModelCall
import com.nightstalker.artic.core.presentation.model.ContentResultState
import com.nightstalker.artic.features.exhibition.domain.usecase.ExhibitionsUseCase

/**
 * Вью модель для получения экспоната
 *
 * @property useCase   репозиторий
 * @author Tamerlan Mamukhov on 2022-09-17
 */
class ExhibitionDetailsViewModel(
    private val useCase: ExhibitionsUseCase
) : ViewModel() {

    private var _exhibitionContentState = MutableLiveData<ContentResultState>()
    val exhibitionContentState get() = _exhibitionContentState

    fun getExhibition(id: Int) = viewModelCall(
        call = { useCase.getExhibitionById(id) },
        contentResultState = _exhibitionContentState
    )

}