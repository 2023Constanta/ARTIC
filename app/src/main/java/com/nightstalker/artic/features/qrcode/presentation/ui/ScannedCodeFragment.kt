package com.nightstalker.artic.features.qrcode.presentation.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.nightstalker.artic.R
import com.nightstalker.artic.databinding.FragmentScannedCodeBinding

/**
 * Фрагмент, который показывает информацию о том экспонате, который доступен по коду
 *
 * @constructor Create empty Scanned code fragment
 */
class ScannedCodeFragment : Fragment(R.layout.fragment_scanned_code) {

    private val binding: FragmentScannedCodeBinding by viewBinding(FragmentScannedCodeBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}