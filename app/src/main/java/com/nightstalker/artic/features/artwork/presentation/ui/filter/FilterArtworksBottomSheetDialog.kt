package com.nightstalker.artic.features.artwork.presentation.ui.filter

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.nightstalker.artic.R
import com.nightstalker.artic.databinding.FragmentFilterArtworksBottomSheetDialogBinding
import com.nightstalker.core.presentation.ext.handleContent
import com.nightstalker.core.presentation.ext.ui.selectedItem
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
        handleSearchArguments()
        restorePositions()
    }

    // TODO: хочу сделать так, чтобы при изменении места и/или типа обновлялось кол-во экспонатов...
    private fun listenToChanges() {

        with(filterArtworksViewModel) {

            binding.spTypes.selectedItem {

//                setCountry(it)
                Log.d(TAG, "listenToChanges: $it")
            }
            binding.spCountries.selectedItem {
//                setType(it)
                Log.d(TAG, "listenToChanges: $it")
            }

            resetNumber()

            numberOfArtworks.observe(viewLifecycleOwner, ::handleFilterResult)
        }

    }


    private fun handleSearchArguments() = with(filterArtworksViewModel) {
        getNumberOfArtworks(fullQuery.value.orEmpty())
        numberOfArtworks.observe(viewLifecycleOwner, ::handleFilterResult)
    }

    private fun handleFilterResult(contentResultState: ContentResultState) {

        contentResultState.handleContent(viewToShow = binding.content,
            progressBar = binding.progressBar,
            onStateSuccess = {
                binding.btnApply.text = resources.getString(R.string.text_found_artworks, it)
            })
    }


    private fun prepareViews() = with(binding) {
        this.btnApply.setOnClickListener {
            saveArgs()
            findNavController().popBackStack()
        }

        this.btnRefresh.setOnClickListener {
            saveArgs()
            listenToChanges()
        }
    }

    private fun saveArgs() = with(filterArtworksViewModel) {
        setCountry(binding.spCountries.selectedItem.toString())
        setType(binding.spTypes.selectedItem.toString())

        setCountryPos(binding.spCountries.selectedItemPosition)
        setTypePos(binding.spTypes.selectedItemPosition)
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