package com.nightstalker.artic.core.presentation.ext.ui

import android.view.inputmethod.EditorInfo
import android.widget.EditText
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
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
            if (text.isNotBlank()) {
                callback.invoke(text.trim().toString())
            } else {
                error = context.getString(R.string.enter_num_val)
            }
            return@setOnEditorActionListener false
        }
        false
    }

fun RecyclerView.setDivider(@DrawableRes drawableRes: Int) {
    val divider = DividerItemDecoration(
        this.context,
        DividerItemDecoration.VERTICAL
    )
    val drawable = ContextCompat.getDrawable(
        this.context,
        drawableRes
    )
    drawable?.let {
        divider.setDrawable(it)
        addItemDecoration(divider)
    }
}