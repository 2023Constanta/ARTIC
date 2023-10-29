package com.nightstalker.artic.features.exhibition.presentation.ui.detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import coil.load
import com.nightstalker.artic.R
import com.nightstalker.artic.databinding.FragmentExhibitionDetailsBinding
import com.nightstalker.artic.features.exhibition.domain.model.Exhibition
import com.nightstalker.artic.features.wip.newFuncForHandling
import com.nightstalker.core.presentation.model.ContentResultState
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

        binding.errorPanele.btnTry.setOnClickListener {
            exhibitionsViewModel.getExhibition(args.exhibitionId)
        }

        exhibitionsViewModel.exhibition.observe(viewLifecycleOwner, ::handleExhibition)
        args.exhibitionId.run {
            exhibitionsViewModel.getExhibition(this)
        }
    }

    private fun handleExhibition(contentResultState: ContentResultState) =
        contentResultState.newFuncForHandling(
            successStateAction = {
                setViews(it as Exhibition)
            },
            viewToShow = binding.content,
            errorPanelBinding = binding.errorPanele
        )


    private fun setViews(exhibition: Exhibition) = with(binding) {
        titleTextView.text = exhibition.title.orEmpty()
        tvStatus.text = exhibition.status.orEmpty()
        ivImage.load(exhibition.imageUrl.orEmpty())

        buyTicketFloatingActionButton.setOnClickListener {
            findNavController().navigate(ExhibitionDetailsFragmentDirections.actionExhibitionDetailsFragmentToBuyTicketFragment())
//            findNavController().navigate(
//                R.id.ticketDetailsFragment,
//                bundleOf(ApiConstants.BUNDLE_EXHIBITION_ID to args.exhibitionId)
//            )
        }
    }

    companion object {
        private const val TAG = "ExhibitionDetailFragment"
    }
}