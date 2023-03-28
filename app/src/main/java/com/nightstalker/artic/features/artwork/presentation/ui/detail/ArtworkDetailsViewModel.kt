package com.nightstalker.artic.features.artwork.presentation.ui.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nightstalker.artic.features.ImageLinkCreator
import com.nightstalker.artic.features.artwork.domain.usecase.ArtworksUseCase
import com.nightstalker.core.presentation.ext.viewModelCall
import com.nightstalker.core.presentation.model.ContentResultState

/**
 * Вью модель для получения деталей экспоната
 *
 * @property useCase    юз кейс
 * @author Tamerlan Mamukhov on 2022-09-18
 */
class ArtworkDetailsViewModel(
    private val useCase: ArtworksUseCase,
) : ViewModel() {

    private val _artworkContentState =
        MutableLiveData<ContentResultState>(ContentResultState.Loading)
    val artworkContentState get() = _artworkContentState

    private val _artworkDescriptionState =
        MutableLiveData<ContentResultState>(ContentResultState.Loading)
    val artworkDescriptionState get() = _artworkDescriptionState

    private val _detailedArtworkImageId =
        MutableLiveData<ContentResultState>(ContentResultState.Loading)
    val detailedArtworkImageId get() = _detailedArtworkImageId

    fun getArtwork(id: Int) =
        viewModelCall(
            call = { useCase.getArtworkById(id) },
            contentResultState = _artworkContentState
        )

    fun getArtworkInformation(id: Int) =
        viewModelCall(
            call = { useCase.getArtworkInformation(id) },
            contentResultState = _artworkDescriptionState
        )

    fun setDetailedArtworkImageId(imageId: String) {
        _detailedArtworkImageId.value =
            ContentResultState.Content(ImageLinkCreator.createImageHighQualityLink(imageId))
    }
}