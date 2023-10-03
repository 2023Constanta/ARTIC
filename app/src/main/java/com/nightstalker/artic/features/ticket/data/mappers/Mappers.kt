package com.nightstalker.artic.features.ticket.data.mappers

import com.nightstalker.artic.features.ApiConstants
import com.nightstalker.artic.features.ticket.data.room.TicketEntity
import com.nightstalker.artic.features.ticket.domain.model.ExhibitionTicket
import com.nightstalker.core.presentation.ext.primitives.normalizeEventDateTime
import com.nightstalker.core.presentation.ext.primitives.toCalendarInMillis


/**
 * Функции для преобразования данных из дата слоя в домайн
 *
 * @author Tamerlan Mamukhov
 */
fun ExhibitionTicket.toLocalTicket(): TicketEntity =
    TicketEntity(
        id = id,
        title = title,
        exhibitionId = exhibitionId,
        imageUrl = imageUrl,
        galleryId = galleryId,
        galleryTitle = galleryTitle,
        aicEndAt = aicEndAt,
        aicStartAt = aicStartAt,
        shortDescription = shortDescription,
        numberOfPersons = numberOfPersons,
        timestamp = timestamp,
        comments = comments
    )

fun ExhibitionTicket.toCalendarEvent(): Map<String, String> = mapOf(
    ApiConstants.CalendarEventConstants.EVENT_BEGIN to aicStartAt
        .toCalendarInMillis()
        .normalizeEventDateTime()
        .toString(),
    ApiConstants.CalendarEventConstants.EVENT_ALLDAY to "false",
    ApiConstants.CalendarEventConstants.EVENT_RULE to ApiConstants.CalendarEventConstants.EVENT_CALENDAR_RRULE,
    ApiConstants.CalendarEventConstants.EVENT_END to aicEndAt
        .toCalendarInMillis()
        .normalizeEventDateTime()
        .toString(),
    ApiConstants.CalendarEventConstants.EVENT_TITLE to "Exhibition: ${title}",
    ApiConstants.CalendarEventConstants.EVENT_DESCRIPTION to "Place:  ${galleryTitle} of ${ApiConstants.CalendarEventConstants.ARTIC_TITLE}",
    ApiConstants.CalendarEventConstants.EVENT_LOCATION to ApiConstants.CalendarEventConstants.ARTIC_LOCATION,
)

fun TicketEntity.toExhibitionTicket(): ExhibitionTicket =
    ExhibitionTicket(
        id = id,
        title = title,
        exhibitionId = exhibitionId,
        imageUrl = imageUrl,
        galleryId = galleryId,
        galleryTitle = galleryTitle,
        aicEndAt = aicEndAt,
        aicStartAt = aicStartAt,
        shortDescription = shortDescription,
        numberOfPersons = numberOfPersons,
        timestamp = timestamp,
        comments = comments
    )
