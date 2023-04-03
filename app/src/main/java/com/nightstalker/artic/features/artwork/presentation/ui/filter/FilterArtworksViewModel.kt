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
    val numberOfArtworks get() = _numberOfArtworks

    private val _queryWord = MutableLiveData<String>()
    val queryWord: LiveData<String> get() = _queryWord

    private val _fullQuery = MutableLiveData<String>()
    val fullQuery: LiveData<String> get() = _fullQuery

    private val _countryPos = MutableLiveData<Int>()
    val countryPos: LiveData<Int> get() = _countryPos

    private val _typePos = MutableLiveData<Int>()
    val typePos: LiveData<Int> get() = _typePos

    private val _country = MutableLiveData<String>()
    val country: LiveData<String> get() = _country

    private val _type = MutableLiveData<String>()
    val type: LiveData<String> get() = _type

    /**
     * Получение числа найденных экспонатов по запросу
     */
//    fun getNumberOfArtworks(query: String) =
//        viewModelCall(
//            call = { useCase.getNumber(query) },
//            contentResultState = _numberOfArtworks
//        )

    fun resetNumber() {
        if (_numberOfArtworks.value is ContentResultState.Content) {
            _numberOfArtworks.value = ContentResultState.Loading
            return
        }
    }

    fun getNumberOfArtworks(query: String) {

        resetNumber()
//        if (_numberOfArtworks.value is ContentResultState.Content) {
//            _numberOfArtworks.value = ContentResultState.Loading
//        }

        var searchQuery = SearchArtworksQueryConstructor.create(query)

        val country = _country.value.toString()
        val type = _type.value.toString()

        if (country.isNotBlank() || type.isNotBlank()) {
            searchQuery = SearchArtworksQueryConstructor.create(
                searchQuery = query, place = country, type = type
            )
        }

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
    fun setQuery(query: String) {
        var searchQuery = SearchArtworksQueryConstructor.create(query)

        val country = _country.value.toString()
        val type = _type.value.toString()

        if (country.isNotBlank() || type.isNotBlank()) {
            searchQuery = SearchArtworksQueryConstructor.create(
                searchQuery = query, place = country, type = type
            )
        }

        _fullQuery.value = searchQuery
    }

    fun setQueryWord(word: String) {
        _queryWord.value = word
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