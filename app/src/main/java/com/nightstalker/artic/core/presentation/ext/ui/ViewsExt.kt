package com.nightstalker.artic.core.presentation.ext.ui

import android.view.inputmethod.EditorInfo
import android.widget.EditText
import com.nightstalker.artic.R

/**
 * Ext-функции для вьюшек
 * @author Tamerlan Mamukhov on 2022-11-21
 */

/**
 * Функция для [EditText] -- слушатель текста
 *
 * Выполняет поиск при не пустой строке
 */
fun EditText.onDone(callback: (query: String) -> Unit) =
    setOnEditorActionListener { _, actionId, _ ->
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            if (!text.isNullOrBlank()) {
                callback.invoke(text.trim().toString())
            } else {
                error = context.getString(R.string.enter_num_val)
            }
            return@setOnEditorActionListener false
        }
        false
    }