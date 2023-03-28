package com.nightstalker.artic.features.artwork.presentation.ui.list

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.nightstalker.artic.R
import com.nightstalker.artic.core.presentation.ext.ui.setDivider
import com.nightstalker.artic.databinding.FragmentArtworksListBinding
import com.nightstalker.artic.features.artwork.domain.model.Artwork
import com.nightstalker.artic.features.artwork.presentation.ui.filter.FilterArtworksViewModel
import com.nightstalker.core.presentation.ext.handleContent
import com.nightstalker.core.presentation.model.ContentResultState
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Фрагмент для отображения списка эспонатов
 * @author Tamerlan Mamukhov
 * @created 2022-09-18
 */
class ArtworksListFragment : Fragment(R.layout.fragment_artworks_list) {

    private val binding: FragmentArtworksListBinding by viewBinding(FragmentArtworksListBinding::bind)
    private lateinit var adapter: ArtworksListAdapter
    private val artworksListViewModel by viewModel<ArtworksListViewModel>()
    private val filterArtworksViewModel by sharedViewModel<FilterArtworksViewModel>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initArtworkObserver()

        artworksListViewModel.loadArtworks()

        prepareSearch()

        prepareWord()
        prepareAdapter()

        setLoadingDefault()
    }

    private fun prepareWord() {
        filterArtworksViewModel.queryWord.observe(viewLifecycleOwner) {
            with(binding?.tilSearch?.editText) {
                if (this?.text?.isEmpty() == true) {
                    this?.setText(it)
                }
            }
        }
    }


    private fun prepareAdapter() {
        with(binding) {
            rvArtworks.layoutManager =
                LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            adapter = ArtworksListAdapter { id -> onItemClick(id) }

            rvArtworks.apply {
                this.adapter = adapter
                this.setDivider(R.drawable.line_divider)
            }
        }
    }

    private fun prepareSearch() {
        with(binding) {
            tilSearch.apply {
                editText?.setOnEditorActionListener { _, actionId, _ ->
                    if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                        val word = editText?.text.toString()

                        filterArtworksViewModel.setQuery(word)
                        filterArtworksViewModel.setQueryWord(word)

                        artworksListViewModel.getArtworksByQuery(filterArtworksViewModel.fullQuery.value.toString())

                        initObserversForSearchedArtworks()
                    }
                    true
                }
                setEndIconOnClickListener {
                    findNavController().navigate(R.id.action_artworksListFragment_to_filterArtworksBottomSheetDialog)
                }
            }
        }
    }

    private fun initArtworkObserver() =
        artworksListViewModel.artworksContentState.observe(viewLifecycleOwner, ::handleArtworks)

    private fun initObserversForSearchedArtworks() =
        artworksListViewModel.searchedArtworksContentState.observe(
            viewLifecycleOwner,
            ::handleSearchedArtworks
        )

    private fun handleArtworks(contentResultState: ContentResultState) =
        with(binding) {
            ivLoadDefault.visibility = View.GONE
            contentResultState.handleContent(
                viewToShow = content,
                onStateSuccess = {
                    adapter.setData(it as List<Artwork>)
                    rvArtworks.adapter = adapter
                },
                progressBar = progressBar,
                errorLayout = errorLayout,
                tryAgainAction = {
                    artworksListViewModel.getArtworks()
                })
        }


    private fun handleSearchedArtworks(contentResultState: ContentResultState) =
        with(binding) {
            ivLoadDefault.visibility = View.VISIBLE
            contentResultState.handleContent(
                viewToShow = content,
                onStateSuccess = {
                    adapter.setData(it as List<Artwork>)
                    rvArtworks.adapter = adapter
                },
                progressBar = progressBar,
                errorLayout = errorLayout,
                tryAgainAction = {
                    artworksListViewModel.getArtworks()
                }
            )
        }

    private fun setLoadingDefault() {
        binding?.ivLoadDefault?.setOnClickListener {
            artworksListViewModel.getArtworks()
        }
    }

    private fun tryAgain() {
        artworksListViewModel.getArtworks()
        initArtworkObserver()
    }

    private fun onItemClick(id: Int) = ArtworksListFragmentDirections.toArtworkDetailsFragment(id)
        .run { findNavController().navigate(this) }

}