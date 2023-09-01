package com.nightstalker.artic.features.artwork.presentation.ui.filter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nightstalker.artic.features.artwork.domain.usecase.ArtworksUseCase
import com.nightstalker.core.presentation.ext.viewModelCall
import com.nightstalker.core.presentation.model.ContentResultState

/**
 * [ViewModel] для поиска экспонатов
 *
 * @author Tamerlan Mamukhov on 2022-11-15
 */
class FilterArtworksViewModel(
    private val useCase: ArtworksUseCase
) : ViewModel() {

    private val _numberOfArtworks = MutableLiveData<ContentResultState>()
    val numberOfArtworks: LiveData<ContentResultState> get() = _numberOfArtworks

    // "Сырой" запрос из поиска
    private val _searchQuery = MutableLiveData<String>()
    val searchQuery: LiveData<String> get() = _searchQuery

    // Готовый запрос дял поиска
    private val _fullQuery = MutableLiveData<String>()
    val fullQuery: LiveData<String> get() = _fullQuery

    private val _countryPos = MutableLiveData<Int>()
    val countryPos: LiveData<Int> get() = _countryPos

    private val _typePos = MutableLiveData<Int>()
    val typePos: LiveData<Int> get() = _typePos

    private val _country = MutableLiveData<String>()
    private val _type = MutableLiveData<String>()


    // Получение числа экспонатов по запросу
    fun getNumberOfArtworks() {
        val rawQuery = _searchQuery.value ?: ""
        var searchQuery = SearchArtworksQueryConstructor.create(rawQuery)

        val country = _country.value.toString()
        val type = _type.value.toString()

        if (country.isNotBlank() || type.isNotBlank()) {
            searchQuery = SearchArtworksQueryConstructor.create(
                searchQuery = rawQuery, place = country, type = type
            )
        }

        viewModelCall(
            call = { useCase.getNumber(searchQuery) },
            contentResultState = _numberOfArtworks
        )
    }

    fun resetQuery() {
        _searchQuery.value = ""
        var searchQuery = SearchArtworksQueryConstructor.create("")
        viewModelCall(
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
        val rawQuery = _searchQuery.value!!
        var searchQuery = SearchArtworksQueryConstructor.create(rawQuery)

        val country = _country.value.toString()
        val type = _type.value.toString()

        if (country.isNotBlank() || type.isNotBlank()) {
            searchQuery = SearchArtworksQueryConstructor.create(
                searchQuery = rawQuery, place = country, type = type
            )
        }

        _fullQuery.value = searchQuery
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
}