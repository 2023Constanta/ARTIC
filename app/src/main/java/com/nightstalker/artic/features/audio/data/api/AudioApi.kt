package com.nightstalker.artic.features.audio.data.api

import com.nightstalker.artic.features.ApiConstants.ID
import com.nightstalker.artic.features.ApiConstants.PARAMS
import com.nightstalker.artic.features.ApiConstants.TITLE
import com.nightstalker.artic.features.ApiConstants.TRANSCRIPT
import com.nightstalker.artic.features.ApiConstants.WEB_URL
import com.nightstalker.artic.features.audio.data.model.MobileSoundData
import com.nightstalker.core.data.model.common.ItemsListResultModel
import com.nightstalker.core.data.model.common.SingeItemResultModel
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * API для получения аудио
 * @author Tamerlan Mamukhov
 * @created 2022-11-17
 */
interface AudioApi {
    /**
     * Получение аудио по номеру
     *
     * @param id    номер
     */
    @GET("mobile-sounds/{$ID}/?fields=$ID,$TITLE,$WEB_URL,$TRANSCRIPT")
    suspend fun getSoundById(@Path(ID) id: Int): SingeItemResultModel<MobileSoundData>

    /**
     * Получение аудио по названию экспоната
     *
     * @param query название
     */
    @GET("mobile-sounds/search?")
    suspend fun getSoundByArtworkTitle(@Query(PARAMS) query: String): ItemsListResultModel<MobileSoundData>
}