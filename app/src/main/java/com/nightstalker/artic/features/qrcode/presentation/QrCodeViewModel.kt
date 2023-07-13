package com.nightstalker.artic.features.qrcode.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nightstalker.artic.features.exhibition.domain.usecase.ExhibitionsUseCase
import com.nightstalker.artic.features.qrcode.domain.model.ExhibitionBriefDataDom
import com.nightstalker.core.domain.model.data
import io.ktor.utils.io.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Вью-модель, которая работает с результатом сканирования QR-кодов
 *
 * @constructor Create empty Qr code view model
 */
class QrCodeViewModel(
    private val useCase: ExhibitionsUseCase
) : ViewModel() {

    private var _scanResult = MutableLiveData<String>()
    val scanResult get() = _scanResult

    private val _exhibitionData = MutableLiveData<ExhibitionBriefDataDom>()
    val exhibitionData: LiveData<ExhibitionBriefDataDom> get() = _exhibitionData

    fun getData(id: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val data = useCase.getBrief(id)

                withContext(Dispatchers.Main) {
                    _exhibitionData.value = data.data
                }
            }
        }
    }




}