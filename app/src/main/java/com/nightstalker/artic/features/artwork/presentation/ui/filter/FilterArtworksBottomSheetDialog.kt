package com.nightstalker.artic.features.artwork.presentation.ui.filter

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.nightstalker.artic.R
import com.nightstalker.artic.core.presentation.ext.refreshPage
import com.nightstalker.artic.core.presentation.model.ContentResultState
import com.nightstalker.artic.databinding.FragmentFilterArtworksBottomSheetDialogBinding
import com.nightstalker.artic.features.ApiConstants.ARG_KEY_ART_PLACE
import com.nightstalker.artic.features.ApiConstants.ARG_KEY_ART_TYPE
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
        handleSearchArguments()
        restorePositions()

        filterArtworksViewModel.country.observe(viewLifecycleOwner) {
            Log.d(TAG, "onViewCreated: $it")
        }

        filterArtworksViewModel.type.observe(viewLifecycleOwner) {
            Log.d(TAG, "onViewCreated: $it")
        }
    }


    private fun handleSearchArguments() = with(filterArtworksViewModel) {
        getNumberOfArtworks(fullQuery.value.orEmpty())
        numberOfArtworks.observe(viewLifecycleOwner, ::handleFilterResult)
    }

    private fun handleFilterResult(contentResultState: ContentResultState) =
        contentResultState.refreshPage(viewToShow = binding.content,
            progressBar = binding.progressBar,
            onStateSuccess = {
                binding.btnApply.text = resources.getString(R.string.text_found_artworks, it)
            })


    private fun prepareViews() = with(binding) {
        this.btnApply.setOnClickListener {
            saveArgs()
            sendArgs()
        }
    }

    private fun saveArgs() = with(filterArtworksViewModel) {
        setCountry(binding.spCountries.selectedItem.toString())
        setType(binding.spTypes.selectedItem.toString())

        setCountryPos(binding.spCountries.selectedItemPosition)
        setTypePos(binding.spTypes.selectedItemPosition)
    }


    private fun sendArgs() =
        Bundle().apply {
            with(binding) {
                putString(ARG_KEY_ART_PLACE, this.spCountries.selectedItem.toString())
                putString(ARG_KEY_ART_TYPE, this.spTypes.selectedItem.toString())
            }
        }.run {
            findNavController().navigate(
                R.id.action_filterArtworksBottomSheetDialog_to_artworksListFragment, this
            )
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