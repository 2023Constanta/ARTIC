package com.nightstalker.artic.features.qrcode.presentation.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.nightstalker.artic.R
import com.nightstalker.artic.databinding.FragmentQrscannerBinding
import com.nightstalker.artic.features.qrcode.QrCodeGenerator.QR_BORDER_STROKE_WIDTH
import com.nightstalker.core.presentation.ext.ui.snack
import me.dm7.barcodescanner.zbar.Result
import me.dm7.barcodescanner.zbar.ZBarScannerView

class QRScannerFragment : Fragment(R.layout.fragment_qrscanner), ZBarScannerView.ResultHandler {

    private val binding: FragmentQrscannerBinding by viewBinding(FragmentQrscannerBinding::bind)

    private lateinit var scannerView: ZBarScannerView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeQRCamera()
        onFlashToggleClicked()
    }

    private fun initializeQRCamera() {
        scannerView = ZBarScannerView(activity)
        with(scannerView) {
            setResultHandler(this@QRScannerFragment)
            setBackgroundColor(ContextCompat.getColor(context!!, R.color.colorTranslucent))
            setBorderColor(ContextCompat.getColor(context!!, R.color.colorPrimaryDark))
            setLaserColor(ContextCompat.getColor(context!!, R.color.colorPrimaryDark))
            setBorderStrokeWidth(QR_BORDER_STROKE_WIDTH)
            setSquareViewFinder(true)
            setupScanner()
            setAutoFocus(true)
            startQRCamera()
            binding.containerScanner.addView(this)
        }
    }

    private fun startQRCamera() = scannerView.startCamera()

    private fun onFlashToggleClicked() =
        with(binding) {
            flashToggle.setOnClickListener {
                if (flashToggle.isSelected) {
                    offFlashLight()
                } else {
                    onFlashLight()
                }
            }

        }

    private fun onFlashLight() {
        binding.flashToggle.isSelected = true
        scannerView.flash = true
    }

    private fun offFlashLight() {
        binding.flashToggle.isSelected = false
        scannerView.flash = false
    }

    override fun onResume() {
        super.onResume()
        scannerView.setResultHandler(this)
    }

    override fun onPause() {
        super.onPause()
        scannerView.stopCamera()
    }

    override fun onDestroy() {
        super.onDestroy()
    }


    override fun handleResult(rawResult: Result?) {
        if (rawResult?.contents?.contains(Regex("exhibitions*/+\\d*(?:/+\\d+)*$")) == true) {
            Log.d("Scanner", "handleResult: ${rawResult?.contents}")
            findNavController().navigate(
                QRScannerFragmentDirections.actionQrScannerToScannedCodeFragment(
                    rawResult?.contents
                )
            )
        } else {
            binding.root.snack(getString(R.string.error_wrong_qr))
        }

    }
}