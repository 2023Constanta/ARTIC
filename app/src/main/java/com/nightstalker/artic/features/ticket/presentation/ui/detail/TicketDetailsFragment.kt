package com.nightstalker.artic.features.ticket.presentation.ui.detail


import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.nightstalker.artic.MainActivity
import com.nightstalker.artic.R
import com.nightstalker.artic.databinding.FragmentTicketDetailsBinding
import com.nightstalker.artic.features.ApiConstants
import com.nightstalker.artic.features.exhibition.data.mappers.toExhibitionTicket
import com.nightstalker.artic.features.exhibition.domain.model.Exhibition
import com.nightstalker.artic.features.exhibition.presentation.ui.detail.ExhibitionDetailsViewModel
import com.nightstalker.artic.features.qrcode.QrCodeGenerator
import com.nightstalker.artic.features.ticket.data.mappers.toCalendarEvent
import com.nightstalker.artic.features.ticket.domain.model.ExhibitionTicket
import com.nightstalker.core.presentation.ext.handleContent
import com.nightstalker.core.presentation.ext.primitives.reformatIso8601
import com.nightstalker.core.presentation.model.ContentResultState
import kotlinx.android.synthetic.main.fragment_ticket_details.addCalendarEventButton
import kotlinx.android.synthetic.main.fragment_ticket_details.deleteTicketButton
import kotlinx.android.synthetic.main.fragment_ticket_details.undoTicketButton
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class TicketDetailsFragment : Fragment(R.layout.fragment_ticket_details) {
    private val args: TicketDetailsFragmentArgs by navArgs()
    private val exhibitionsViewModel: ExhibitionDetailsViewModel by sharedViewModel()
    private val ticketViewModel by viewModel<TicketDetailsViewModel>()
    private val binding: FragmentTicketDetailsBinding by viewBinding(FragmentTicketDetailsBinding::bind)

    private lateinit var undoExhibitionTicket: ExhibitionTicket

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initTicketDetailsFragment()
    }

    // 2023-01-10 Не могу пока разобраться с логикой здесь. Этот код писал не я
    // Разберу его обязательно...
    private fun initTicketDetailsFragment() {
        // Форма TicketDetailsFragment открывается при покупке билета и из списка билетов
        // При покупке билета нам известен только id выставки
        // Из списка билетов получаем id билета

        var exhibitionId = -1   // заполняется в ExhibitionDetailsFragment
        var ticketId = -1L     // заполняется в TicketsListFragment

        // exhibition_id получаем из ExhibitionDetailsFragment через bundle
        arguments?.getInt(ApiConstants.BUNDLE_EXHIBITION_ID)?.let {
            exhibitionId = it
            exhibitionsViewModel.getExhibition(exhibitionId)
            initExhibitionObserver()
        }

        // ticket_id получаем из TicketsListFragment как аргумент фрагмента
        args.ticketId.toLong().let {
            ticketId = it
            ticketViewModel.getTicket(it)
            initTicketObserver()
        }

        // удаление билета
        binding.deleteTicketButton.setOnClickListener {

            ticketViewModel.deleteTicket(
                ticketId = ticketId,
                exhibitionId = exhibitionId.toString()
            )

            deleteTicketButton.visibility = View.INVISIBLE
            addCalendarEventButton.visibility = View.INVISIBLE
            undoTicketButton.visibility = View.VISIBLE
        }
        // восстановление билета
        binding.undoTicketButton.setOnClickListener {

            ticketViewModel.saveTicket(undoExhibitionTicket)

            deleteTicketButton.visibility = View.VISIBLE
            addCalendarEventButton.visibility = View.VISIBLE
            undoTicketButton.visibility = View.INVISIBLE
        }
    }


    private fun initTicketObserver() {
        ticketViewModel.ticketContent.observe(viewLifecycleOwner, ::handleTicket)
    }

    private fun initExhibitionObserver() {
        exhibitionsViewModel.exhibitionContentState.observe(viewLifecycleOwner, ::handleTicket)
    }

    private fun handleTicket(contentResultState: ContentResultState) =
        contentResultState.handleContent(
            viewToShow = binding.content,
            progressBar = binding.progressBar,
            onStateSuccess = {
                when (it) {
                    is Exhibition -> setViews(it)
                    is ExhibitionTicket -> setData(it)
                }
            }
        )

    private fun setViews(exhibition: Exhibition) {
        ticketViewModel.saveTicket(exhibition.toExhibitionTicket())
        setData(exhibition.toExhibitionTicket())
    }

    private fun setData(ticket: ExhibitionTicket) = with(binding) {
        undoExhibitionTicket = ticket

        titleTextView.text = ticket.title
        exhibitionIdTextView.text = ticket.galleryTitle

        aicStartAtTextView.text =
            "${getString(R.string.exhibition_start)} : ${
                ticket.aicStartAt.reformatIso8601()
            }"

        aicEndAtTextView.text =
            "${getString(R.string.exhibition_end)} : ${
                ticket.aicEndAt.reformatIso8601()
            }"

        //QRCode
        qrCodeImageView.setImageBitmap(
            QrCodeGenerator.makeImage(
                String.format(
                    getString(
                        R.string.qr_code_msg,
                        ticket.title,
                        ticket.galleryTitle
                    )
                )
            )
        )

        // Регистрация нового события в календаре Google
        addCalendarEventButton.setOnClickListener {
            (activity as MainActivity).addCalendarEvent(ticket.toCalendarEvent())
        }
    }
}