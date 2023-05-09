package com.nightstalker.artic.core.presentation.ext.ui

import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * Функция для [RecyclerView], задающая разделитель для карточек
 *
 * @author Tamerlan Mamukhov on 2022-11-21
 */
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

fun RecyclerView.setup(activity: FragmentActivity, adapter: RecyclerView.Adapter<*>) {
    this.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
    this.adapter = adapter
}