package com.nightstalker.artic.features.exhibition.presentation.ui.detail

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import coil.load
import com.nightstalker.artic.R
import com.nightstalker.artic.core.presentation.ext.refreshPage
import com.nightstalker.artic.core.presentation.model.ContentResultState
import com.nightstalker.artic.databinding.FragmentExhibitionDetailsBinding
import com.nightstalker.artic.features.ApiConstants
import com.nightstalker.artic.features.exhibition.domain.model.Exhibition
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

/**
 * Фрагмент для отображения деталей выставки
 * @author Tamerlan Mamukhov on 2022-09-18
 */
class ExhibitionDetailsFragment : Fragment(R.layout.fragment_exhibition_details) {
    private val args: ExhibitionDetailsFragmentArgs by navArgs()
    private val exhibitionsViewModel: ExhibitionDetailsViewModel by sharedViewModel()
    private val binding: FragmentExhibitionDetailsBinding by viewBinding(
        FragmentExhibitionDetailsBinding::bind
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        args.exhibitionId.run {
            exhibitionsViewModel.getExhibition(this)
        }
        initObserver()
    }

    private fun initObserver() {
        exhibitionsViewModel.exhibitionContentState.observe(viewLifecycleOwner, ::handleExhibition)
    }

    private fun handleExhibition(contentResultState: ContentResultState) =
        contentResultState.refreshPage(
            viewToShow = binding.content,
            progressBar = binding.progressBar,
            onStateSuccess = {
                setViews(it as Exhibition)
            },
            errorLayout = binding.errorLayout
        )


    private fun setViews(exhibition: Exhibition) = with(binding) {
        titleTextView.text = exhibition.title.orEmpty()
        tvStatus.text = exhibition.status.orEmpty()
        ivImage.load(exhibition.imageUrl.orEmpty())

        buyTicketFloatingActionButton.setOnClickListener {
            findNavController().navigate(
                R.id.ticketDetailsFragment,
                bundleOf(ApiConstants.BUNDLE_EXHIBITION_ID to args.exhibitionId)
            )
        }
    }

    companion object {
        const val TAG = "ExhibitionDetailFragment"
    }
}