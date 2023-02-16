package com.nightstalker.artic.features.artwork.presentation.ui.list

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.nightstalker.artic.R
import com.nightstalker.artic.core.presentation.ext.refreshPage
import com.nightstalker.artic.core.presentation.model.ContentResultState
import com.nightstalker.artic.databinding.FragmentArtworksListBinding
import com.nightstalker.artic.features.artwork.domain.model.Artwork
import com.nightstalker.artic.features.artwork.presentation.ui.dialog.FilterArtworksViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Фрагмент для отображения списка эспонатов
 * @author Tamerlan Mamukhov
 * @created 2022-09-18
 */
class ArtworksListFragment : Fragment(R.layout.fragment_artworks_list) {

    private val binding: FragmentArtworksListBinding by viewBinding(FragmentArtworksListBinding::bind)
    private lateinit var adapter: ArtworksListAdapter
    private val viewModel by viewModel<ArtworksListViewModel>()
    private val searchViewModel by viewModel<FilterArtworksViewModel>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initArtworkObserver()
        observeFullQuery()

        viewModel.loadArtworks()

        prepareSearch()

        prepareWord()
        prepareAdapter()

        setLoadingDefault()
    }

    private fun prepareWord() {
        searchViewModel.queryWord.observe(viewLifecycleOwner) {
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
                val divider = DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL)
                ContextCompat.getDrawable(this.context, R.drawable.line_divider)
                    ?.let { divider.setDrawable(it) }
                this.addItemDecoration(
                    divider
                )
            }
        }
    }

    private fun observeFullQuery() {
        searchViewModel.queryFull.observe(viewLifecycleOwner) {
            Log.d(TAG, "observerQuery: $it")
        }
    }

    private fun prepareSearch() {
        with(binding) {
            tilSearch.apply {
                editText?.setOnEditorActionListener { _, actionId, _ ->
                    if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                        getArgs()
                        val word = editText?.text.toString()
                        searchViewModel.setQuery(word, place, type)
                        searchViewModel.setQueryWord(word)

                        viewModel.getArtworksByQuery(searchViewModel.queryFull.value!!)

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
        viewModel.artworksContentState.observe(viewLifecycleOwner, ::handleArtworks)

    private fun initObserversForSearchedArtworks() =
        viewModel.searchedArtworksContentState.observe(viewLifecycleOwner, ::handleSearchedArtworks)

    private fun handleArtworks(contentResultState: ContentResultState) =
        with(binding) {
            ivLoadDefault.visibility = View.GONE
            contentResultState.refreshPage(
                viewToShow = content,
                onStateSuccess = {
                    adapter.setData(it as List<Artwork>)
                    rvArtworks.adapter = adapter
                },
                progressBar = progressBar,
                errorLayout = errorLayout,
                tryAgainAction = {
                    viewModel.getArtworks()
                })
        }


    private fun handleSearchedArtworks(contentResultState: ContentResultState) =
        with(binding) {
            ivLoadDefault.visibility = View.VISIBLE
            contentResultState.refreshPage(
                viewToShow = content,
                onStateSuccess = {
                    adapter.setData(it as List<Artwork>)
                    rvArtworks.adapter = adapter
                },
                progressBar = progressBar,
                errorLayout = errorLayout,
                tryAgainAction = {
                    viewModel.getArtworks()
                }
            )
        }

    private fun setLoadingDefault() {
        binding?.ivLoadDefault?.setOnClickListener {
            viewModel.getArtworks()
        }
    }

    private fun tryAgain() {
        viewModel.getArtworks()
        initArtworkObserver()
    }

    private fun onItemClick(id: Int) = ArtworksListFragmentDirections.toArtworkDetailsFragment(id)
        .run { findNavController().navigate(this) }

    private fun getArgs() {
        arguments?.apply {
            place = getString("place").toString()
            type = getString("type").toString()

            Log.d(TAG, "getArgs: $place")
            Log.d(TAG, "getArgs: $type")
        }
    }

    companion object {
        private var place = ""
        private var type = ""
        private const val TAG = "ArtworksListFragment"
    }

}