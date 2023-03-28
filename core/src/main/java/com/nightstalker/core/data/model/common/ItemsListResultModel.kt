package com.nightstalker.core.data.model.common

import com.google.gson.annotations.SerializedName

/**
 * Класс результатов запроса в сеть (список)
 *
 * @param T                 тип данных
 * @property data           данные
 * @property pagination     пагинация
 * @author Tamerlan Mamukhov on 2022-11-20
 */
data class ItemsListResultModel<T>(
    @SerializedName("data")
    val data: List<T>,
    @SerializedName("pagination")
    val pagination: Pagination
)