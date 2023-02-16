package com.nightstalker.artic.features.artwork.presentation.ui.detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import coil.load
import com.nightstalker.artic.R
import com.nightstalker.artic.core.presentation.ext.refreshPage
import com.nightstalker.artic.core.presentation.model.ContentResultState
import com.nightstalker.artic.databinding.FragmentArtworkDetailsBinding
import com.nightstalker.artic.features.ImageLinkCreator
import com.nightstalker.artic.features.artwork.domain.model.Artwork
import com.nightstalker.artic.features.artwork.domain.model.ArtworkInformation
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

/**
 * Фрагмент для отображения деталей эскпоната
 * @author Tamerlan Mamukhov
 * @created 2022-09-18
 */
class ArtworkDetailsFragment : Fragment(R.layout.fragment_artwork_details) {

    private val args: ArtworkDetailsFragmentArgs by navArgs()
    private val binding: FragmentArtworkDetailsBinding by viewBinding(FragmentArtworkDetailsBinding::bind)
    private val artworkViewModel: ArtworkDetailsViewModel by sharedViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        args.posterId.run {
            artworkViewModel.getArtwork(this)
            artworkViewModel.getArtworkInformation(this)
        }

        initObserver()
    }

    private fun initObserver() =
        with(artworkViewModel) {
            artworkContentState.observe(viewLifecycleOwner, ::handle)
            artworkDescriptionState.observe(viewLifecycleOwner, ::handle)
        }

    private fun handle(contentResultState: ContentResultState) =
        contentResultState.refreshPage(
            viewToShow = binding.content,
            progressBar = binding.progressBar,
            onStateSuccess = {
                when (it) {
                    is Artwork -> setArtworkViews(it)
                    is ArtworkInformation -> setDescriptionView(it)
                }
            },
            errorLayout = binding.errorLayout
        )


    private fun setDescriptionView(artworkInformation: ArtworkInformation) =
        with(binding) {
            tvDescription.text = artworkInformation.description
        }

    private fun setArtworkViews(artwork: Artwork) {
        with(binding) {

            titleTextView.text = artwork.title
            tvAuthor.text = artwork.artist

            val imageUrl = artwork.imageId?.let { ImageLinkCreator.createImageDefaultLink(it) }

            with(placeImage) {
                load(imageUrl)

                setOnClickListener {
                    artwork.imageId?.let { it1 ->
                        ArtworkDetailsFragmentDirections.actionArtworkDetailsFragmentToArtworkFullViewFragment(
                            it1
                        )
                    }.run {
                        this?.let { it1 -> findNavController().navigate(it1) }
                    }
                }

            }
        }

    }

    companion object {
        private const val TAG = "ArtworkDetailsFragment"
    }
}