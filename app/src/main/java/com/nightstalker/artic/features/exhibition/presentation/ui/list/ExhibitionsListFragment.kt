package com.nightstalker.artic.features.exhibition.presentation.ui.list

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.nightstalker.artic.R
import com.nightstalker.artic.core.presentation.ext.ui.setDivider
import com.nightstalker.artic.core.presentation.ext.ui.setup
import com.nightstalker.artic.databinding.FragmentExhibitionsListBinding
import com.nightstalker.artic.features.exhibition.domain.model.Exhibition
import com.nightstalker.artic.features.wip.newFuncForHandling
import com.nightstalker.core.presentation.model.ContentResultState
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Фрагмент для отображения списка выставок
 * @author Tamerlan Mamukhov
 * @created 2022-09-18
 */
class ExhibitionsListFragment : Fragment(R.layout.fragment_exhibitions_list) {

    private lateinit var adapter: ExhibitionsListAdapter
    private val binding: FragmentExhibitionsListBinding
            by viewBinding(FragmentExhibitionsListBinding::bind)
    private val viewModel by viewModel<ExhibitionsListViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.errorPanele.btnTry.setOnClickListener { viewModel.getExhibitions() }
        initObserver()
        viewModel.loadExhibitions()
        prepareAdapter()
    }

    private fun prepareAdapter() =
        with(binding) {
            adapter = ExhibitionsListAdapter { id -> onItemClicked(id) }
            rvExhibitions.setup(requireActivity(), adapter)
            rvExhibitions.setDivider(R.drawable.line_divider)
        }

    private fun initObserver() = with(viewModel) {
        exhibitions.observe(viewLifecycleOwner, ::setExhibitions)
    }

    private fun setExhibitions(contentResultState: ContentResultState) = with(binding) {
        contentResultState.newFuncForHandling(
            successStateAction = {
                adapter.setData(it as List<Exhibition>)
            },
            viewToShow = rvExhibitions,
            errorPanelBinding = errorPanele
        )
    }


    private fun onItemClicked(id: Int) =
        ExhibitionsListFragmentDirections
            .toExhibitionDetailsFragment(id)
            .run { findNavController().navigate(this) }

}