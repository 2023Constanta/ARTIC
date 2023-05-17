package com.nightstalker.artic.features.artwork.presentation.ui.list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nightstalker.artic.features.artwork.domain.usecase.ArtworksUseCase
import com.nightstalker.core.presentation.ext.viewModelCall
import com.nightstalker.core.presentation.model.ContentResultState

/**
 * Вью модель для получения списка экспонатов
 *
 * @property useCase    юз кейс
 * @author Tamerlan Mamukhov on 2022-09-18
 */
class ArtworksListViewModel(
    private val useCase: ArtworksUseCase
) : ViewModel() {

    private val _artworksContentState =
        MutableLiveData<ContentResultState>(ContentResultState.Loading)
    val artworksContentState get() = _artworksContentState

    private var _searchedArtworksContentState =
        MutableLiveData<ContentResultState>(ContentResultState.Loading)
    val searchedArtworksContentState get() = _searchedArtworksContentState

    fun loadArtworks() {
        if (_artworksContentState.value is ContentResultState.Loading) {
            getArtworks()
        }
    }

    fun getArtworks() =
        viewModelCall(call = { useCase.getArtworks() }, contentResultState = _artworksContentState)

    fun getArtworksByQuery(query: String) =
        viewModelCall(
            call = { useCase.getArtworksByQuery(query) },
            contentResultState = _artworksContentState
        )
}