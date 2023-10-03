package com.nightstalker.core.presentation.ext.ui

import android.content.Context
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.Spinner

typealias OnItemSelectedAction = (position: Int) -> Unit
typealias OnNothingSelectedAction = () -> Unit

/**
 * Фукнция, которая работает с выбранным элементом [Spinner]
 *
 * @param action    действие
 */
fun Spinner.selectedItem(action: (item: String) -> Unit) {
    this.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(parent: AdapterView<*>?) {}
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            val selectedItem = parent?.getItemAtPosition(position).toString()
            action(selectedItem)
        }
    }
}

fun Spinner.setupAdapter(values: MutableList<String>, context: Context) {
    this.adapter = ArrayAdapter(
        context,
        android.R.layout.simple_spinner_dropdown_item,
        values
    )
}

fun Spinner.setupOnItemSelectedListener(
    onItemSelectedAction: OnItemSelectedAction,
    onNothingSelectedAction: OnNothingSelectedAction? = null
) {

    val currentPosition = this.selectedItemPosition

    this.onItemSelectedListener = object : OnItemSelectedListener {
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            if (currentPosition != position) {
                onItemSelectedAction(position)
            }
        }

        override fun onNothingSelected(parent: AdapterView<*>?) {
            onNothingSelectedAction?.invoke()
        }

    }
}