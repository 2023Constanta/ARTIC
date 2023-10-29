package com.nightstalker.artic.features.artwork.presentation.ui.filter

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nightstalker.artic.features.artwork.domain.usecase.ArtworksUseCase
import com.nightstalker.core.presentation.ext.viewModelCall
import com.nightstalker.core.presentation.model.ContentResultState
import kotlinx.coroutines.CoroutineDispatcher

/**
 * [ViewModel] для поиска экспонатов
 *
 * @author Tamerlan Mamukhov on 2022-11-15
 */
class FilterArtworksViewModel(
    private val useCase: ArtworksUseCase,
    private val ioDispatcher: CoroutineDispatcher,
    private val mainDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _numberOfArtworks = MutableLiveData<ContentResultState>()
    val numberOfArtworks: LiveData<ContentResultState> get() = _numberOfArtworks

    // "Сырой" запрос из поиска
    private val _searchQuery = MutableLiveData<String>()
    val searchQuery: LiveData<String> get() = _searchQuery

    // Готовый запрос дял поиска
    private val _fullQuery = MutableLiveData<String>()
    val fullQuery: LiveData<String> get() = _fullQuery

    // Позиции для Spinner
    private val _countryPos = MutableLiveData<Int>()
    val countryPos: LiveData<Int> get() = _countryPos

    private val _typePos = MutableLiveData<Int>()
    val typePos: LiveData<Int> get() = _typePos

    private val _country = MutableLiveData<String>()
    private val _type = MutableLiveData<String>()


    // Получение числа экспонатов по запросу
    fun getNumberOfArtworks() {
        var searchQuery = ""

        if (getCountry().isNotBlank() || getType().isNotBlank()) {
            searchQuery = SearchArtworksQueryConstructor.createQuery(
                searchQuery = getRawSearchQuery(), place = getCountry(), type = getType()
            )
        }
        Log.d(TAG, "getNumberOfArtworks after: $searchQuery")

        _numberOfArtworks.value = ContentResultState.Loading
        viewModelCall(
            ioDispatcher = ioDispatcher,
            mainDispatcher = mainDispatcher,
            call = { useCase.getNumber(searchQuery) },
            contentResultState = _numberOfArtworks
        )
    }

    fun resetQuery() {
        _searchQuery.value = ""
        val searchQuery = SearchArtworksQueryConstructor.createQuery("null", "null")
        Log.d(TAG, "resetQuery: $searchQuery")
        _fullQuery.value = searchQuery
        _numberOfArtworks.value = ContentResultState.Loading
        viewModelCall(
            ioDispatcher = ioDispatcher,
            mainDispatcher = mainDispatcher,
            call = { useCase.getNumber(searchQuery) },
            contentResultState = _numberOfArtworks
        )
    }

    /**
     * Создание запроса на поиск экспоната
     *
     * @param query
     */
    fun getReadyQuery() {
        var searchQuery = SearchArtworksQueryConstructor.createQuery(getRawSearchQuery())
        Log.d(TAG, "getReadyQuery before: $searchQuery")
        if (getCountry().isNotBlank() || getType().isNotBlank()) {
            searchQuery = SearchArtworksQueryConstructor.createQuery(
                searchQuery = getRawSearchQuery(), place = getCountry(), type = getType()
            )
        }
        Log.d(TAG, "getReadyQuery after: $searchQuery")
        _fullQuery.value = searchQuery
    }

    private fun getCountry(): String {
        return _country.value.toString()
    }

    private fun getType(): String {
        return _type.value.toString()
    }

    private fun getRawSearchQuery(): String {
        return _searchQuery.value.orEmpty()
    }

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun setCountry(country: String) {
        _country.value = country
    }

    fun setType(type: String) {
        _type.value = type
    }

    fun setCountryPos(countryPos: Int) {
        _countryPos.value = countryPos
    }

    fun setTypePos(typePos: Int) {
        _typePos.value = typePos
    }

    companion object {
        private const val TAG = "FilterArtworksViewModel"
    }
}