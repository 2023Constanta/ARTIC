package com.nightstalker.artic.features.ticket.presentation.ui.detail


import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.nightstalker.artic.MainActivity
import com.nightstalker.artic.R
import com.nightstalker.artic.databinding.FragmentTicketDetailsBinding
import com.nightstalker.artic.features.ApiConstants
import com.nightstalker.artic.features.qrcode.QrCodeGenerator
import com.nightstalker.artic.features.ticket.data.mappers.toCalendarEvent
import com.nightstalker.artic.features.ticket.domain.model.ExhibitionTicket
import com.nightstalker.core.presentation.ext.handleContent
import com.nightstalker.core.presentation.ext.primitives.reformatIso8601
import com.nightstalker.core.presentation.model.ContentResultState
import org.koin.androidx.viewmodel.ext.android.viewModel


class TicketDetailsFragment : Fragment(R.layout.fragment_ticket_details) {

    private val args: TicketDetailsFragmentArgs by navArgs()
    private val binding: FragmentTicketDetailsBinding by viewBinding(FragmentTicketDetailsBinding::bind)
    private val ticketViewModel by viewModel<TicketDetailsViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ticketViewModel.ticket.observe(viewLifecycleOwner, ::handleTicket)
        ticketViewModel.getTicket(args.ticketId.toLong())

        initViewListeners()
    }

    private fun initViewListeners() = with(binding) {
        deleteTicketButton.setOnClickListener {

            ticketViewModel.deleteTicket(
                ticketId =  args.ticketId.toLong(),
                exhibitionId = arguments?.getInt(ApiConstants.BUNDLE_EXHIBITION_ID).toString()
            )

            deleteTicketButton.visibility = View.INVISIBLE
            addCalendarEventButton.visibility = View.INVISIBLE
            undoTicketButton.visibility = View.VISIBLE
        }

        undoTicketButton.setOnClickListener {

            ticketViewModel.undoTicket.value?.let { ticket -> ticketViewModel.saveTicket(ticket) }

            deleteTicketButton.visibility = View.VISIBLE
            addCalendarEventButton.visibility = View.VISIBLE
            undoTicketButton.visibility = View.INVISIBLE
        }
    }

    private fun handleTicket(contentResultState: ContentResultState) =
        contentResultState.handleContent(
            viewToShow = binding.content,
            progressBar = binding.progressBar,
            onStateSuccess = {
                setViewForTicket(it as ExhibitionTicket)
            }
        )


    private fun setViewForTicket(ticket: ExhibitionTicket) = with(binding) {
        ticketViewModel.saveUndoTicket(ticket)
        Log.d(TAG, "setViewForTicket: $ticket")

        titleTextView.text = ticket.title
        exhibitionIdTextView.text = ticket.galleryTitle

        aicStartAtTextView.text =
            String.format(getString(R.string.exhibition_start), ticket.aicStartAt.reformatIso8601())
        aicStartAtTextView.text =
            String.format(getString(R.string.exhibition_end), ticket.aicEndAt.reformatIso8601())

        // TODO: Надо добавить проверку на комментарии. Если есть, то сделать такой код 
        qrCodeImageView.setImageBitmap(
            QrCodeGenerator.makeImage(
                "exhibitions/${ticket.exhibitionId}"
//                String.format(
//                    getString(
//                        R.string.qr_code_msg,
//                        ticket.title,
//                        ticket.galleryTitle
//                    )
//                )
            )
        )

        // Регистрация нового события в календаре Google
        addCalendarEventButton.setOnClickListener {
            (activity as MainActivity).addCalendarEvent(ticket.toCalendarEvent())
        }
    }

    companion object {
        private const val TAG = "TicketDetails"
    }
}