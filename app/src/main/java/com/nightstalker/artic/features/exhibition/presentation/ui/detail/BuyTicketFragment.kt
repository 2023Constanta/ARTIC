package com.nightstalker.artic.features.exhibition.presentation.ui.detail

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.textfield.TextInputLayout

import com.nightstalker.artic.R
import com.nightstalker.artic.databinding.FragmentBuyTicketBinding
import com.nightstalker.artic.features.exhibition.data.mappers.toExhibitionTicket
import com.nightstalker.artic.features.exhibition.domain.model.Exhibition
import com.nightstalker.core.presentation.model.ContentResultState
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class BuyTicketFragment : Fragment(R.layout.fragment_buy_ticket) {

    private val binding: FragmentBuyTicketBinding by viewBinding(FragmentBuyTicketBinding::bind)
    private val exhibitionsViewModel: ExhibitionDetailsViewModel by sharedViewModel()
    private val buyTicketViewModel: BuyTicketViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            tilFirstName.editText?.addTextChangedListener(textWatcher)
            tilLastName.editText?.addTextChangedListener(textWatcher)
        }

        exhibitionsViewModel.exhibition.observe(viewLifecycleOwner, ::handle)
    }

    private fun handle(contentResultState: ContentResultState) {
        if (contentResultState is ContentResultState.Content) {
            with(binding) {
                tvTicketInfo.text = "${(contentResultState.content as Exhibition).shortDescription}"

                    // После нажатия
                btnApply.setOnClickListener {
                    buyTicketViewModel.saveTicket(
                        (contentResultState.content as Exhibition).toExhibitionTicket().copy(
                            comments = "FDSFSFfFD"
                        )
                    )
                }

            }
        }
    }


    val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

//            binding.btnApply.isEnabled = false
            val name = binding.tilFirstName.editText?.text.toString().trim()
            val surname = binding.tilLastName.editText?.text.toString().trim()

            binding.btnApply.isEnabled = name.isNotEmpty() && surname.isNotEmpty()
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//            binding.btnApply.isEnabled = false
            val name = binding.tilFirstName.editText?.text.toString().trim()
            val surname = binding.tilLastName.editText?.text.toString().trim()

            binding.btnApply.isEnabled = name.isNotEmpty() && surname.isNotEmpty()
        }

        override fun afterTextChanged(s: Editable?) {

        }

    }
    companion object {
        private const val TAG = "BuyTicket"
    }


}

