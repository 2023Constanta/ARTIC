package com.nightstalker.artic.features.exhibition.presentation.ui.detail

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.textfield.TextInputLayout

import com.nightstalker.artic.R
import com.nightstalker.artic.databinding.FragmentBuyTicketBinding
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
        exhibitionsViewModel.exhibition.observe(viewLifecycleOwner, ::handle)

        setupTilListeners()
    }

    private fun handle(contentResultState: ContentResultState) {
        if (contentResultState is ContentResultState.Content) {
            with(binding) {
                tvTicketInfo.text = "${(contentResultState.content as Exhibition).shortDescription}"

                // После нажатия
                btnApply.setOnClickListener {
                    if (validateFirst() && validateLast()) {
                        Toast.makeText(activity, "Данные ок!", Toast.LENGTH_SHORT).show()
//                        val ticket =
                        val ticketComment = "Имя: Джон \nФамилия: Вик \nЦена: $50"
                        binding.tvTicketInfo.text = ticketComment
                    } else {
                        Toast.makeText(activity, "Данные не ок!", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun setupTilListeners() = with(binding) {
        tilFirstName.editText?.addTextChangedListener(TextFieldValidation(tilFirstName))
        tilLastName.editText?.addTextChangedListener(TextFieldValidation(tilLastName))
    }

    private fun validateName(textInputLayout: TextInputLayout, errorMsg: String): Boolean = with(textInputLayout) {
        if (editText?.text.toString().trim().isBlank()) {
            this.error = errorMsg
            this.requestFocus()
            false
        } else {
            this.isErrorEnabled = false
        }
        true
    }

    private fun validateFirst(): Boolean = validateName(binding.tilFirstName, "Введите имя!")

    private fun validateLast(): Boolean = validateName(binding.tilLastName, "Введите фамилию!")

    inner class TextFieldValidation(private val view: View) : TextWatcher {
        override fun afterTextChanged(s: Editable?) {}
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            // checking ids of each text field and applying functions accordingly.
            when(view.id) {
                R.id.tilFirstName -> {
                    validateFirst()
                }
                R.id.tilLastName -> {
                    validateLast()
                }
            }
        }

    }

    companion object {
        private const val TAG = "BuyTicket"
    }


}