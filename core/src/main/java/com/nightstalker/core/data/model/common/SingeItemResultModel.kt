package com.nightstalker.core.data.model.common

import com.google.gson.annotations.SerializedName

/**
 * Класс результатов запроса в сеть (один объект)
 * @author Tamerlan Mamukhov on 2022-11-20
 */
data class SingeItemResultModel<T>(
    @SerializedName("data")
    val data: T
)