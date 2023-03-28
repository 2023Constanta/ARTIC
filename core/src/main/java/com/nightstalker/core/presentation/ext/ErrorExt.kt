package com.nightstalker.core.presentation.ext

import com.nightstalker.core.R
import com.nightstalker.core.presentation.model.ErrorModel

/**
 * Функция, которой задает [ErrorModel] в зависимости от наличия ошибки из-за Сети
 *
 * @return
 */
fun Boolean.parseError(): ErrorModel =
    if (this) ErrorModel(
        R.string.network_error_title,
        R.string.network_error_description,
    ) else ErrorModel(
        R.string.loading_error_title,
        R.string.loading_error_description,
    )