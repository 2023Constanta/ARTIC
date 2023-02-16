package com.nightstalker.artic.features.ticket.domain.model


/**
 * Билет на выставку
 *
 * @property title              название выставки
 * @property exhibitionId       id выставки
 * @property galleryId          id галлереи, в которой проходит выставка
 * @property galleryTitle       название галереи
 * @property aicStartAt         дата начала
 * @property aicEndAt           дата завершения
 * @property shortDescription   краткое описание
 * @property numberOfPersons    на сколько персон билет
 * @property timestamp          время покупки билета
 *
 * @author Maxim Zimin
 */
data class ExhibitionTicket(
    val id: Long = 0L,
    val title: String = "",
    val exhibitionId: String = "",
    val imageUrl: String = "",
    val galleryId: Int = 0,
    val galleryTitle: String = "",
    val aicEndAt: String = "",
    val aicStartAt: String = "",
    val shortDescription: String = "",
    val numberOfPersons: Int = 1,
    val timestamp: Long = 0L,
)