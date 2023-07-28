package com.nightstalker.core.presentation.ext.ui

import android.content.Context
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
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

fun Spinner.setupAdapter(values: MutableList<String>, context: Context) {
    this.adapter = ArrayAdapter(
        context,
        android.R.layout.simple_spinner_dropdown_item,
        values
    )
}

fun Spinner.setupOnItemSelectedListener(action1: (pos: Int) -> Unit, action2: () -> Unit) {

    this.onItemSelectedListener = object : OnItemSelectedListener {
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            action1(position)
        }

        override fun onNothingSelected(parent: AdapterView<*>?) {
            action2.invoke()
        }

    }
}
