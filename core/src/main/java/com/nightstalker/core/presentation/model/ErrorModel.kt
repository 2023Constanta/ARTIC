package com.nightstalker.core.presentation.model

import androidx.annotation.StringRes

/**
 * Класс ошибки
 *
 * @property title          заголовок
 * @property description    описание
 * @author Tamerlan Mamukhov on 2023-01-12
 */
data class ErrorModel(@StringRes val title: Int, @StringRes val description: Int)
