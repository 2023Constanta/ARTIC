package com.nightstalker.artic.features.exhibition.domain.model

/**
 * Класс выставки
 *
 * @property imageUrl           ссылка на главную картинку
 * @property galleryTitle       заголовок галереи
 * @property title              загаловок выставки
 * @property altImageIds        ид картинок
 * @property status             статус
 * @property shortDescription   краткое описание
 * @property aicEndAt           дата закрытия
 * @property aicStartAt         дата открытия
 * @author Tamerlan Mamukhov
 */
data class Exhibition(
    val id: Int,
    val imageUrl: String?,
    val galleryTitle: String?,
    val title: String?,
    val altImageIds: List<String>?,
    val status: String?,
    val shortDescription: String?,
    val aicEndAt: String?,
    val aicStartAt: String?,
)
