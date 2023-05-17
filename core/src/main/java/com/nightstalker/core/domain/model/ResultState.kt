package com.nightstalker.core.domain.model

/**
 * Класс, оборачивающий запрашиваемые данные из сети и базы данных для безопасного доступа
 *
 * @author Tamerlan Mamukhov on 2023-01-12
 */
sealed class ResultState<out R> {
    data class Success<out S>(val data: S) : ResultState<S>()
    data class Error(val errorData: Boolean) : ResultState<Nothing>()
}

val <S>ResultState<S>.data: S?
    get() = (this as? ResultState.Success)?.data

val <S>ResultState<S>.errorData: Boolean?
    get() = (this as? ResultState.Error)?.errorData