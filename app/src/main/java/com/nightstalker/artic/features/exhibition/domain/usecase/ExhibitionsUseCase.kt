package com.nightstalker.artic.features.exhibition.domain.usecase

import com.nightstalker.artic.core.domain.model.ResultState
import com.nightstalker.artic.core.domain.model.safeCall
import com.nightstalker.artic.features.exhibition.domain.model.Exhibition
import com.nightstalker.artic.features.exhibition.domain.repo.ExhibitionsRepo

/**
 * @author Tamerlan Mamukhov
 * @created 2022-10-24
 */
class ExhibitionsUseCase(
    private val repo: ExhibitionsRepo,
) {
    suspend fun getExhibitionById(id: Int): ResultState<Exhibition> =
        safeCall { repo.getExhibitionById(id) }

    suspend fun getExhibitions(): ResultState<List<Exhibition>> = safeCall { repo.getExhibitions() }
}