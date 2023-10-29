package com.nightstalker.artic.features.artwork.presentation.ui.filter

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.nightstalker.artic.R
import com.nightstalker.artic.databinding.FragmentFilterArtworksBinding
import com.nightstalker.artic.features.wip.newFuncForHandling
import com.nightstalker.core.presentation.ext.ui.createMapFrom2StrArrays
import com.nightstalker.core.presentation.ext.ui.setupAdapter
import com.nightstalker.core.presentation.ext.ui.setupOnItemSelectedListener
import com.nightstalker.core.presentation.model.ContentResultState
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

/**
 * Фрагмент для фильтра эксопнатов
 *
 * @author Tamerlan Mamukhov on 2022-11-15
 */
class FilterArtworksFragment :
    Fragment(R.layout.fragment_filter_artworks) {

    private val binding: FragmentFilterArtworksBinding by viewBinding(
        FragmentFilterArtworksBinding::bind
    )
    private val filterArtworksViewModel by sharedViewModel<FilterArtworksViewModel>()
    private lateinit var countriesMap: Map<String, String>
    private lateinit var typesMap: Map<String, String>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.errorPanel.btnTry.setOnClickListener { tryAgain() }

        with(filterArtworksViewModel) {
            numberOfArtworks.observe(viewLifecycleOwner, ::handleFilteredCount)
            getNumberOfArtworks()

        }

        restorePositions()
        setupSpinners()
        prepareViews()
    }

    @SuppressLint("StringFormatMatches")
    private fun handleFilteredCount(contentResultState: ContentResultState) = with(binding) {
        contentResultState.newFuncForHandling(
            successStateAction = { count ->
                if (count as Int > 0) {
                    btnApply.text = resources.getString(R.string.text_found_artworks, count)
                } else {
                    btnApply.text = "Ничего нет!"
                }
            },
            viewToShow = group,
            errorPanelBinding = errorPanel
        )
    }

    private fun setupSpinners() = with(binding) {
        countriesMap =
            createMapFrom2StrArrays(R.array.places_of_origin, R.array.places_of_origin_human)
        typesMap = createMapFrom2StrArrays(R.array.artwork_types, R.array.artwork_types_human)

        spCountries.setupAdapter(countriesMap.values.toMutableList(), requireContext())
        spTypes.setupAdapter(typesMap.values.toMutableList(), requireContext())

        spCountries.setupOnItemSelectedListener(
            onItemSelectedAction = {
                saveArgs()
                filterArtworksViewModel.getNumberOfArtworks()
            }
        )

        spTypes.setupOnItemSelectedListener(
            onItemSelectedAction = {
                saveArgs()
                filterArtworksViewModel.getNumberOfArtworks()
            }
        )
    }

    private fun prepareViews() = with(binding) {
        btnApply.setOnClickListener {
            saveArgs()
            findNavController().popBackStack()
        }

        btnReset.setOnClickListener {
            filterArtworksViewModel.resetQuery()
            spCountries.setSelection(0)
            spTypes.setSelection(0)
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

    private fun tryAgain() {
        filterArtworksViewModel.getNumberOfArtworks()
    }

    companion object {
        private const val TAG = "FilterArtworksDialog"
    }

}