package com.nightstalker.artic.features.artwork.presentation.ui.filter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.nightstalker.artic.R
import com.nightstalker.artic.databinding.FragmentFilterArtworksBottomSheetDialogBinding
import com.nightstalker.core.presentation.ext.handleContent
import com.nightstalker.core.presentation.model.ContentResultState
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

/**
 * Фрагмент для фильтра эксопнатов
 *
 * @author Tamerlan Mamukhov on 2022-11-15
 */
class FilterArtworksBottomSheetDialog : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentFilterArtworksBottomSheetDialogBinding
    private val filterArtworksViewModel by sharedViewModel<FilterArtworksViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(
            R.layout.fragment_filter_artworks_bottom_sheet_dialog, container, false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentFilterArtworksBottomSheetDialogBinding.bind(view)

        prepareViews()
        restorePositions()
        handleSearchArguments()
    }

    private fun handleSearchArguments() = with(filterArtworksViewModel) {
        getNumberOfArtworks(fullQuery.value.orEmpty())
        numberOfArtworks.observe(viewLifecycleOwner, ::handleFilterResult)
    }

    private fun handleFilterResult(contentResultState: ContentResultState) = with(binding) {
        contentResultState.handleContent(
            viewToShow = content,
            progressBar = progressBar,
            onStateSuccess = {
                btnApply.text = resources.getString(R.string.text_found_artworks, it)
            }
        )
    }

    private fun prepareViews() = with(binding) {
        btnApply.setOnClickListener {
            saveArgs()

            findNavController().popBackStack()
        }

        btnRefresh.setOnClickListener {
//            filterArtworksViewModel.resetNumber()
            saveArgs()
            handleSearchArguments()
        }
    }

    private fun saveArgs() = with(filterArtworksViewModel) {
        with(binding.spTypes) {
            setType(selectedItem.toString())
            setTypePos(selectedItemPosition)
        }

        with(binding.spCountries) {
            setCountry(selectedItem.toString())
            setCountryPos(selectedItemPosition)
        }
    }


    private fun restorePositions() = with(filterArtworksViewModel) {
        with(binding) {
            countryPos.observe(viewLifecycleOwner) {
                spCountries.setSelection(it)
            }
            typePos.observe(viewLifecycleOwner) {
                spTypes.setSelection(it)
            }
        }
    }

    companion object {
        private const val TAG = "FilterArtworks"
    }

}