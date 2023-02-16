package com.nightstalker.artic.features.exhibition.data.mappers

import com.nightstalker.artic.features.exhibition.data.model.ExhibitionData
import com.nightstalker.artic.features.exhibition.domain.model.Exhibition
import com.nightstalker.artic.features.ticket.domain.model.ExhibitionTicket
import java.util.Date

/**
 * Функции для преобразования данных из дата слоя в домайн
 *
 * @author Tamerlan Mamukhov
 */
fun ExhibitionData.toExhibition(): Exhibition =
    Exhibition(
        id = id,
        imageUrl = imageUrl,
        galleryTitle = galleryTitle,
        title = title,
        altImageIds = listOf(),
        status = status,
        shortDescription = shortDescription,
        aicEndAt = aicEndAt,
        aicStartAt = aicStartAt,
    )


fun Exhibition.toExhibitionTicket(): ExhibitionTicket =
    ExhibitionTicket(
        title = title ?: "",
        exhibitionId = id.toString(),
        imageUrl = imageUrl ?: "",
        galleryTitle = galleryTitle ?: "",
        shortDescription = shortDescription ?: "",
        numberOfPersons = 1,
        aicEndAt = aicEndAt ?: "",
        aicStartAt = aicStartAt ?: "",
        timestamp = Date().time
    )
