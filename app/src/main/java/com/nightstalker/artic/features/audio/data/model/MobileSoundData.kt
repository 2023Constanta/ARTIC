package com.nightstalker.artic.features.audio.data.model

import com.google.gson.annotations.SerializedName

/**
 * Класс аудиозаписи
 *
 * @property id         ид
 * @property title      заголовок
 * @property webUrl     ссылка на звук
 * @property transcript текст (транскрипт)
 *
 * @author Tamerlan Mamukhov on 2022-11-17
 */
data class MobileSoundData(
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String? = "",
    @SerializedName("web_url")
    val webUrl: String? = "",
    @SerializedName("transcript")
    val transcript: String? = "",
)
