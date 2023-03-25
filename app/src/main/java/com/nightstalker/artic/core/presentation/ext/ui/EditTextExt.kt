package com.nightstalker.artic.core.presentation.ext.ui

import android.view.inputmethod.EditorInfo
import android.widget.EditText
import com.google.android.material.textfield.TextInputLayout
import com.nightstalker.artic.R

/**
 * Функция для [EditText] -- слушатель текста
 *
 * Выполняет поиск при не пустой строке
 * @author Tamerlan Mamukhov on 2022-11-21
 */

fun EditText.setupSearch(callback: (num: Int) -> Unit) =
    setOnEditorActionListener { _, actionId, _ ->
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            if (text.isNotBlank()) {
                callback.invoke(text.trim().toString().toInt())
            } else {
                error = context.getString(R.string.enter_num_val)
            }
            return@setOnEditorActionListener false
        }
        false
    }

fun TextInputLayout.setupWithCallback(
    callback: (query: Int) -> Unit,
    actionWhenGood: (id: Int) -> Unit
) {
    val textFromEditText = this.editText?.text
    if (textFromEditText?.isEmpty() == true) {
        this.editText?.error = context.getString(R.string.enter_num_val)
    } else {
        this.setStartIconOnClickListener {
            this.editText?.setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    callback.invoke(textFromEditText.toString().toInt())
                    return@setOnEditorActionListener false
                }
                actionWhenGood.invoke(textFromEditText.toString().toInt())
                false
            }
        }
    }
}