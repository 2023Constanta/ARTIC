package com.nightstalker.artic.features.exhibition.data.api

import com.nightstalker.artic.core.data.model.common.ItemsListResultModel
import com.nightstalker.artic.core.data.model.common.SingeItemResultModel
import com.nightstalker.artic.features.exhibition.data.model.ExhibitionData
import com.nightstalker.artic.features.ApiConstants
import com.nightstalker.artic.features.ApiConstants.ALT_IMAGE_IDS
import com.nightstalker.artic.features.ApiConstants.GALLERY_TITLE
import com.nightstalker.artic.features.ApiConstants.ID
import com.nightstalker.artic.features.ApiConstants.IMAGE_URL
import com.nightstalker.artic.features.ApiConstants.SHORT_DESCRIPTION
import com.nightstalker.artic.features.ApiConstants.STATUS
import com.nightstalker.artic.features.ApiConstants.TITLE
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Запросы получения выставок
 *
 * @author Tamerlan Mamukhov on 2022-09-16
 */
interface ExhibitionsApi {
    @GET("exhibitions/{$ID}?fields=$ID,$IMAGE_URL,$GALLERY_TITLE,$TITLE,$ALT_IMAGE_IDS,$STATUS,$SHORT_DESCRIPTION,${ApiConstants.AICSTARTAT},${ApiConstants.AICENDAT}")
    suspend fun getExhibitionById(@Path(ID) id: Int): SingeItemResultModel<ExhibitionData>

    @GET("exhibitions/search?sort[aic_end_at]=DESC&fields=$ID,$IMAGE_URL,$GALLERY_TITLE,$TITLE,$ALT_IMAGE_IDS,$STATUS,$SHORT_DESCRIPTION,${ApiConstants.AICSTARTAT},${ApiConstants.AICENDAT}")
    suspend fun getExhibitions(): ItemsListResultModel<ExhibitionData>
}