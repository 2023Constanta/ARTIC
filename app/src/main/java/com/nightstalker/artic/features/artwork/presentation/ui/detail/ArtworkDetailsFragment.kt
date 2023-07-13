package com.nightstalker.artic.features.artwork.presentation.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import com.nightstalker.artic.databinding.FragmentArtworkDetailsBinding
import com.nightstalker.artic.features.ImageLinkCreator
import com.nightstalker.artic.features.artwork.domain.model.Artwork
import com.nightstalker.artic.features.artwork.domain.model.ArtworkInformation
import com.nightstalker.core.presentation.ext.handleContent
import com.nightstalker.core.presentation.model.ContentResultState
import com.nightstalker.core.presentation.ui.ViewBindingFragment
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

/**
 * Фрагмент для отображения деталей эскпоната
 * @author Tamerlan Mamukhov
 * @created 2022-09-18
 */
class ArtworkDetailsFragment : ViewBindingFragment<FragmentArtworkDetailsBinding>() {

    private val args: ArtworkDetailsFragmentArgs by navArgs()

    override val initBinding: (inflater: LayoutInflater, container: ViewGroup?, attachToRoot: Boolean) -> FragmentArtworkDetailsBinding
        get() = FragmentArtworkDetailsBinding::inflate

    private val artworkViewModel: ArtworkDetailsViewModel by sharedViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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

    private fun handleResult(contentResultState: ContentResultState) = withSafeBinding {
        contentResultState.handleContent(
            viewToShow = content,
            progressBar = progressBar,
            onStateSuccess = {
                when (it) {
                    is Artwork -> setArtworkViews(it)
                    is ArtworkInformation -> setDescriptionView(it)
                }
            },
            errorLayout = errorLayout
        )
    }

    private fun setDescriptionView(artworkInformation: ArtworkInformation) = withSafeBinding {
        tvDescription.text = artworkInformation.description
    }

    private fun setArtworkViews(artwork: Artwork) = withSafeBinding {
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


    companion object {
        private const val TAG = "ArtworkDetailsFragment"
    }

}