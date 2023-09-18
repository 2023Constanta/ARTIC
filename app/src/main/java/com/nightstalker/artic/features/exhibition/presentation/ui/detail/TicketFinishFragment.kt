package com.nightstalker.artic.features.exhibition.presentation.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import by.kirich1409.viewbindingdelegate.viewBinding
import com.nightstalker.artic.R
import com.nightstalker.artic.databinding.FragmentTicketFinishBinding

/**
 * Фрагмент, которые показывается после покупки билета
 */
class TicketFinishFragment : Fragment(R.layout.fragment_ticket_finish) {

    private val binding: FragmentTicketFinishBinding by viewBinding(FragmentTicketFinishBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

}