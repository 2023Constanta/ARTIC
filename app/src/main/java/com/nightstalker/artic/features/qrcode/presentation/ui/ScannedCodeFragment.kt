package com.nightstalker.artic.features.qrcode.presentation.ui

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.nightstalker.artic.R
import com.nightstalker.artic.databinding.FragmentScannedCodeBinding

/**
 * Фрагмент, который показывает информацию о том экспонате, который доступен по коду
 *
 * @constructor Create empty Scanned code fragment
 */
class ScannedCodeFragment : Fragment(R.layout.fragment_scanned_code) {

    private val args by navArgs<ScannedCodeFragmentArgs>()
    private val binding: FragmentScannedCodeBinding by viewBinding(FragmentScannedCodeBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        with(binding) {
            var res = args.codeContent?.replace("[^0-9]".toRegex(), "")

            tvCodeContent.text = res
            btnOpenDetails.setOnClickListener {
                findNavController().navigate(
                    R.id.exhibitionDetailsFragment,
                    args = bundleOf("exhibition_id" to res?.toInt())
                )
            }

        }
    }
}