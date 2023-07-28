package com.nightstalker.artic.features.artwork.presentation.ui.filter

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.nightstalker.artic.R
import com.nightstalker.artic.databinding.FragmentFilterArtworksBottomSheetDialogBinding
import com.nightstalker.core.presentation.ext.handleContent
import com.nightstalker.core.presentation.ext.ui.setupAdapter
import com.nightstalker.core.presentation.ext.ui.setupOnItemSelectedListener
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
    private lateinit var countriesMap: Map<String, String>
    private lateinit var typesMap: Map<String, String>

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
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFilterArtworksBottomSheetDialogBinding.bind(view)

        with(filterArtworksViewModel) {
            numberOfArtworks.observe(viewLifecycleOwner, ::handleFilterResult)
            getNumberOfArtworks(fullQuery.value.orEmpty())
        }

        prepareViews()
        restorePositions()
    }

    private fun handleSearchArguments() = with(filterArtworksViewModel) {
        getNumberOfArtworks(fullQuery.value.orEmpty())
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

        countriesMap =
            resources.getStringArray(R.array.places_of_origin).zip(
                resources.getStringArray(R.array.places_of_origin_human)
            ).toMap()

        typesMap = resources.getStringArray(R.array.artwork_types).zip(
            resources.getStringArray(R.array.artwork_types_human)
        ).toMap()

        spCountries.setupAdapter(countriesMap.values.toMutableList(), requireContext())
        spTypes.setupAdapter(typesMap.values.toMutableList(), requireContext())

        spCountries.setupOnItemSelectedListener(
            action1 = {
                val countryKey = countriesMap.map { entry -> entry.key }[it]
                Log.d(TAG, "prepareViews: $countryKey")
                handleSearchArguments()
            },
            action2 = {

            }
        )

        spTypes.setupOnItemSelectedListener(
            action1 = {
                val typeKey = typesMap.map { entry -> entry.key }[it]
                Log.d(TAG, "prepareViews: $typeKey")
                handleSearchArguments()
            },
            action2 = {

            }
        )

        btnApply.setOnClickListener {
            saveArgs()
            findNavController().popBackStack()
        }

        btnRefresh.setOnClickListener {
            saveArgs()
            handleSearchArguments()
        }
    }

    private fun saveArgs() = with(filterArtworksViewModel) {
        with(binding) {
            with(spTypes) {
                setType(typesMap.map { entry -> entry.key }[selectedItemPosition])
                setTypePos(selectedItemPosition)
            }

            with(spCountries) {
                setCountry(countriesMap.map { entry -> entry.key }[selectedItemPosition])
                setCountryPos(selectedItemPosition)
            }
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