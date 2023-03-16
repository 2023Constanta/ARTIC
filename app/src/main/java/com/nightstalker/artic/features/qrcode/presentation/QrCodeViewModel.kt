package com.nightstalker.artic.features.qrcode.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * Вью-модель, которая работает с результатом сканирования QR-кодов
 *
 * @constructor Create empty Qr code view model
 */
class QrCodeViewModel : ViewModel() {
    private var _scanResult = MutableLiveData<String>()
    val scanResult get() = _scanResult


}