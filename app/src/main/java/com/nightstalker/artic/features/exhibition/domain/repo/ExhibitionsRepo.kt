package com.nightstalker.artic.features.exhibition.domain.repo

import com.nightstalker.artic.features.exhibition.domain.model.Exhibition

/**
 * Репозиторий получения экспонатов
 *
 * @author Tamerlan Mamukhov on 2022-09-16
 */
interface ExhibitionsRepo {
   suspend fun getExhibitionById(id: Int): Exhibition
   suspend fun getExhibitions(): List<Exhibition>
}