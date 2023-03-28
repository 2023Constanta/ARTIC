package com.nightstalker.artic.features.artwork.presentation.ui.fullview

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import coil.load
import com.nightstalker.artic.R
import com.nightstalker.artic.databinding.FragmentArtworkFullViewBinding
import com.nightstalker.artic.features.artwork.presentation.ui.detail.ArtworkDetailsViewModel
import com.nightstalker.core.presentation.ext.handleContent
import kotlinx.android.synthetic.main.fragment_artwork_full_view.ivFullArtwork
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

/**
 * Фрагмент для отображения полного изображения экспоната
 *
 * @author Tamerlan Mamukhov on 2023-01-06
 */
class ArtworkFullViewFragment : Fragment(R.layout.fragment_artwork_full_view) {

    private val binding: FragmentArtworkFullViewBinding by viewBinding(
        FragmentArtworkFullViewBinding::bind
    )

    private val args: ArtworkFullViewFragmentArgs by navArgs()

    private val artworkViewModel: ArtworkDetailsViewModel by sharedViewModel()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prepareLink()
        observeLink()
    }

    private fun observeLink() {
        artworkViewModel.detailedArtworkImageId.observe(viewLifecycleOwner) {
            it.handleContent(
                viewToShow = binding.content,
                progressBar = binding.progressBar,
                onStateSuccess = { it ->
                    Log.d("ArtFull", "observeLink: $it")
                    ivFullArtwork.load(it)
                }

            )
        }
    }

    private fun prepareLink() {
        artworkViewModel.setDetailedArtworkImageId(args.imageId)
    }

}