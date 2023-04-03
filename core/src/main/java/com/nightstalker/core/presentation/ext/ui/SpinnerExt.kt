package com.nightstalker.core.presentation.ext.ui

import android.view.View
import android.widget.AdapterView
import android.widget.Spinner

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
