package com.nightstalker.artic.features.artwork.presentation.ui.list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nightstalker.artic.features.artwork.domain.usecase.ArtworksUseCase
import com.nightstalker.core.presentation.ext.viewModelCall
import com.nightstalker.core.presentation.model.ContentResultState
import kotlinx.coroutines.CoroutineDispatcher

/**
 * Вью модель для получения списка экспонатов
 *
 * @property useCase    юз кейс
 * @author Tamerlan Mamukhov on 2022-09-18
 */
class ArtworksListViewModel(
    private val useCase: ArtworksUseCase,
//    private val ioDispatcher: CoroutineDispatcher,
//    private val mainDispatcher: CoroutineDispatcher,
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

    fun getArtworks() {
        _artworksContentState.value = ContentResultState.Loading

        viewModelCall(
//            ioDispatcher = ioDispatcher,
//            mainDispatcher = mainDispatcher,
            call = {

                useCase.getArtworks()
                   },
            contentResultState = _artworksContentState
        )

    }
    // Получение экспонатов по запросу
    fun getArtworksByQuery(query: String) =
        viewModelCall(
//            ioDispatcher = ioDispatcher,
//            mainDispatcher = mainDispatcher,
            call = { useCase.getArtworksByQuery(query) },
            contentResultState = _artworksContentState
        )
}