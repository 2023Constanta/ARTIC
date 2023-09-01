package com.nightstalker.core.presentation.ext.ui

import androidx.annotation.ArrayRes
import androidx.fragment.app.Fragment

fun Fragment.createMapFrom2StrArrays(
    @ArrayRes
    arrayAId: Int,
    @ArrayRes
    arrayBId: Int
): Map<String, String> {
    val arrayA = resources.getStringArray(arrayAId)
    val arrayB = resources.getStringArray(arrayBId)

    if (arrayA.size != arrayB.size) {
        throw IllegalArgumentException("Необходимы массивы одинаковой длины!")
    }
    return arrayA.zip(arrayB).toMap()
}