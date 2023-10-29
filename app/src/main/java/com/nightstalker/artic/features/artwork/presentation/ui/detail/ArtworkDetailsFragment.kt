package com.nightstalker.artic.features.artwork.presentation.ui.detail

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import coil.load
import com.nightstalker.artic.R
import com.nightstalker.artic.databinding.FragmentArtworkDetailsBinding
import com.nightstalker.artic.features.ImageLinkCreator
import com.nightstalker.artic.features.artwork.domain.model.Artwork
import com.nightstalker.artic.features.artwork.domain.model.ArtworkInformation
import com.nightstalker.artic.features.wip.newFuncForHandling
import com.nightstalker.core.presentation.model.ContentResultState
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

/**
 * Фрагмент для отображения деталей эскпоната
 * @author Tamerlan Mamukhov
 * @created 2022-09-18
 */
class ArtworkDetailsFragment : Fragment(R.layout.fragment_artwork_details) {

    private val binding: FragmentArtworkDetailsBinding by viewBinding(FragmentArtworkDetailsBinding::bind)
    private val args: ArtworkDetailsFragmentArgs by navArgs()
    private val artworkViewModel: ArtworkDetailsViewModel by sharedViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.errorPanele.btnTry.setOnClickListener {
            tryAgain()
        }
        initObserver()

        args.posterId.run {
            artworkViewModel.getArtwork(this)
            artworkViewModel.getArtworkInformation(this)
        }
    }

    private fun initObserver() = with(artworkViewModel) {
        artworkContentState.observe(viewLifecycleOwner, ::handleResult)
        artworkDescriptionState.observe(viewLifecycleOwner, ::handleResult)
    }

    private fun handleResult(contentResultState: ContentResultState) = with(binding) {
        contentResultState.newFuncForHandling(
            successStateAction = {
                when (it) {
                    is Artwork -> {
                        Log.d(TAG, "handleResult: $it")
                        setArtworkViews(it)
                    }
                    is ArtworkInformation -> setDescriptionView(it)
                }
            },
            viewToShow = artDetailsContent,
            errorPanelBinding = errorPanele
        )

    }

    private fun setDescriptionView(artworkInformation: ArtworkInformation) = with(binding) {
        tvDescription.text = artworkInformation.description
    }

    private fun setArtworkViews(artwork: Artwork) = with(binding) {
        titleTextView.text = artwork.title
        tvAuthor.text = artwork.artist

        val imageUrl = artwork.imageId?.let { ImageLinkCreator.createImageDefaultLink(it) }

        with(placeImage) {
            load(imageUrl)
            setOnClickListener {
                artwork.imageId?.let { id ->
                    ArtworkDetailsFragmentDirections.actionArtworkDetailsFragmentToArtworkFullViewFragment(
                        id
                    )
                }.run {
                    this?.let { dir -> findNavController().navigate(dir) }
                }
            }
        }
    }

    private fun tryAgain() {
        artworkViewModel.loadAgain(args.posterId)
    }


    companion object {
        private const val TAG = "ArtworkDetailsFragment"
    }

}