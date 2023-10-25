package com.nightstalker.artic.features.artwork.presentation.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.nightstalker.artic.R
import com.nightstalker.artic.core.presentation.ext.ui.setDivider
import com.nightstalker.artic.core.presentation.ext.ui.setup
import com.nightstalker.artic.databinding.ErrorPanelBinding
import com.nightstalker.artic.databinding.FragmentArtworksListBinding
import com.nightstalker.artic.features.artwork.domain.model.Artwork
import com.nightstalker.artic.features.artwork.presentation.ui.filter.FilterArtworksViewModel
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
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        binding = FragmentArtworksListBinding.inflate(layoutInflater, container, false)
//        return binding.root
//    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.errorPanele.btnTry.setOnClickListener { tryAgain() }
        initArtworkObserver()
        artworksListViewModel.loadArtworks()

        prepareSearch()
        insertSearchQuery()
        prepareAdapter()
    }

    private fun insertSearchQuery() =
        filterArtworksViewModel.searchQuery.observe(viewLifecycleOwner) {
            with(binding.tilSearch.editText) {
                this?.setText(it)
            }
        }

    private fun prepareAdapter() = with(binding) {
        adapter = ArtworksListAdapter { id -> onItemClick(id) }

        rvArtworks.setup(requireActivity(), adapter)
        rvArtworks.setDivider(R.drawable.line_divider)
    }

    private fun prepareSearch() = with(binding) {
        tilSearch.apply {
            editText?.setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    val query = editText?.text.toString()

                    filterArtworksViewModel.setSearchQuery(query)
                    filterArtworksViewModel.getReadyQuery()
                    artworksListViewModel.getArtworksByQuery(filterArtworksViewModel.fullQuery.value.toString())

                    initObserversForSearchedArtworks()
                }
                false
            }

            setEndIconOnClickListener {
                findNavController().navigate(R.id.action_artworksListFragment_to_filterArtworksBottomSheetDialog)
            }

            setEndIconOnLongClickListener {
                Toast.makeText(activity, "SBROSS", Toast.LENGTH_SHORT).show()
                artworksListViewModel.getArtworks()
                true
            }
        }
    }

    private fun initArtworkObserver() =
        artworksListViewModel.artworksContentState.observe(viewLifecycleOwner, ::handleArtworks)

    private fun initObserversForSearchedArtworks() =
        artworksListViewModel.searchedArtworksContentState.observe(
            viewLifecycleOwner,
            ::handleArtworks
        )

    private fun handleArtworks(contentResultState: ContentResultState) = with(binding) {
        when (contentResultState) {
            is ContentResultState.Content -> {
                errorPanele.root.isVisible = false
                artListContent.isVisible = true
                adapter.setData(contentResultState.content as List<Artwork>)
            }
            is ContentResultState.Error -> {
                artListContent.isVisible = false
                errorPanele.root.isVisible = true
                changeVisibilityForErrorPanel(false, errorPanele)
            }
            ContentResultState.Loading -> {
                artListContent.isVisible = false
                errorPanele.root.isVisible = true
                changeVisibilityForErrorPanel(true, errorPanele)
            }
        }
    }

    private fun changeVisibilityForErrorPanel(visible: Boolean, binding: ErrorPanelBinding) {
        if (visible) {
            binding.pbLoadingProgress.isVisible = visible
            binding.textErroreTitle.isVisible = !visible
            binding.tvErrore.isVisible = !visible
            binding.btnTry.isVisible = !visible
        } else {
            binding.pbLoadingProgress.isVisible = visible
            binding.textErroreTitle.isVisible = !visible
            binding.tvErrore.isVisible = !visible
            binding.btnTry.isVisible = !visible
        }
    }

    private fun tryAgain() {
        artworksListViewModel.getArtworks()
    }

    private fun onItemClick(id: Int) = ArtworksListFragmentDirections.toArtworkDetailsFragment(id)
        .run { findNavController().navigate(this) }

}