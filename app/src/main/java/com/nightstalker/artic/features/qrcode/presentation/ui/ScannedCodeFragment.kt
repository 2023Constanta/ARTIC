package com.nightstalker.artic.features.qrcode.presentation.ui

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context.CLIPBOARD_SERVICE
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.nightstalker.artic.R
import com.nightstalker.artic.databinding.FragmentScannedCodeBinding
import com.nightstalker.core.presentation.ext.ui.snack

/**
 * Фрагмент, который показывает информацию о выставке, которая доступна по коду
 */
class ScannedCodeFragment : Fragment(R.layout.fragment_scanned_code) {

    private val args by navArgs<ScannedCodeFragmentArgs>()
    private val binding: FragmentScannedCodeBinding by viewBinding(FragmentScannedCodeBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
    }

    private fun setupViews() = with(binding) {

        val exhibitionsId = args.codeContent?.replace("\\D".toRegex(), "")

        tvCodeContent.text = exhibitionsId
        btnOpenDetails.setOnClickListener {
            findNavController().navigate(
                R.id.exhibitionDetailsFragment,
                args = bundleOf("exhibition_id" to exhibitionsId?.toInt())
            )
        }
        btnCopyToClip.setOnClickListener {

            val clipboardManager = requireActivity().getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
            clipboardManager.setPrimaryClip(ClipData.newPlainText("id", binding.tvTitle.text))
            binding.root.snack("Скопировано!")
            val item = clipboardManager.primaryClip?.getItemAt(0)
            val text = item?.text
            binding.root.snack(text.toString())
        }
        btnShare.setOnClickListener {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, "${binding.tvTitle.text}")
                type = "text/plain"
            }

            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)

        }

    }
}