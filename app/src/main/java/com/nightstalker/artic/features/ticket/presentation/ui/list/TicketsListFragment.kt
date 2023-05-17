package com.nightstalker.artic.features.ticket.presentation.ui.list

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.nightstalker.artic.R
import com.nightstalker.artic.core.presentation.ext.ui.setDivider
import com.nightstalker.artic.databinding.FragmentTicketsListBinding
import com.nightstalker.artic.features.ticket.domain.model.ExhibitionTicket
import com.nightstalker.core.presentation.ext.handleContent
import com.nightstalker.core.presentation.model.ContentResultState
import org.koin.androidx.viewmodel.ext.android.viewModel


/**
 * Фрагмент для отображения списка билетов
 * @author Maxim Zimin
 * @created 2022-10-13
 */
class TicketsListFragment : Fragment(R.layout.fragment_tickets_list) {
    private lateinit var adapter: TicketsListAdapter
    private val ticketsViewModel by viewModel<TicketsViewModel>()
    private val binding: FragmentTicketsListBinding by viewBinding(FragmentTicketsListBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initObserver()
        prepareAdapter()
        ticketsViewModel.getAllTickets()

    }

    private fun prepareAdapter() = with(binding) {
        rvTickets.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        adapter = TicketsListAdapter { id -> onItemClicked(id) }
        rvTickets.adapter = adapter
        rvTickets.setDivider(R.drawable.line_divider)

    }

    private fun initObserver() {
        ticketsViewModel.ticketsContent.observe(viewLifecycleOwner, ::handleTickets)
    }

    private fun handleTickets(contentResultState: ContentResultState) =
        contentResultState.handleContent(
            viewToShow = binding.content,
            progressBar = binding.progressBar,
            onStateSuccess = {
                adapter.setData(it as List<ExhibitionTicket>)
            },
            errorLayout = binding.errorLayout
        )

    private fun onItemClicked(id: Long) {
        TicketsListFragmentDirections
            .toTicketDetailsFragment(id.toInt())
            .run { findNavController().navigate(this) }
    }

}