package com.nightstalker.core.presentation.ext

import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.core.view.isVisible
import com.nightstalker.core.databinding.LayoutErrorBinding
import com.nightstalker.core.presentation.model.ContentResultState
import com.nightstalker.core.presentation.model.ErrorModel
import com.nightstalker.core.presentation.ui.LayoutErrorView

typealias SuccessStateAction = (content: Any?) -> Unit
typealias ErrorStateAction = (error: ErrorModel) -> Unit
typealias LoadingStateAction = () -> Unit
typealias TryAgainAction = () -> Unit

/**
 * Функция для удобной работы с готовым [ContentResultState] в фрагментах
 *
 * @param onStateSuccess        действие при успехе
 * @param onStateError          действие при неудаче
 * @author Tamerlan Mamukhov on 2023-01-07
 */
fun ContentResultState.handleContent(
    onStateSuccess: SuccessStateAction,
    onStateError: ErrorStateAction,
    onStateLoading: LoadingStateAction? = null
) = when (this) {
    is ContentResultState.Content -> {
        onStateSuccess.invoke(this.content)
    }
    is ContentResultState.Error -> {
        onStateError.invoke(this.error)
    }
    is ContentResultState.Loading -> {
        onStateLoading?.invoke()
    }
}

/**
 * Функция, которая упрощает работу с [ContentResultState]
 *
 * @param onStateSuccess    действие при успехе загрузки данных
 * @param tryAgainAction    бействие при неудаче (напр., повторная загрузка)
 * @param viewToShow        [ViewGroup], которую надо показать после загрузки данных
 * @param progressBar       [ProgressBar], показывающий процесс загрузки
 * @param errorLayout       лайаут с информацией об ошибке
 */
fun ContentResultState.handleContent(
    onStateSuccess: SuccessStateAction,
    tryAgainAction: TryAgainAction? = null,
    viewToShow: ViewGroup,
    progressBar: ProgressBar,
    errorLayout: LayoutErrorBinding? = null,
) = when (this) {

    is ContentResultState.Content -> {
        progressBar.isVisible = false
        errorLayout?.root?.isVisible = false
        viewToShow.isVisible = true

        onStateSuccess.invoke(this.content)
    }
    is ContentResultState.Loading -> {
        progressBar.isVisible = true
        errorLayout?.root?.isVisible = false
        viewToShow.isVisible = false
    }
    is ContentResultState.Error -> {
        progressBar.isVisible = false
        errorLayout?.root?.isVisible = true
        viewToShow.isVisible = false

        errorLayout?.apply {
            textErrorTitle.setText(this@handleContent.error.title)
            textErrorDescription.setText(this@handleContent.error.description)
            btnErrorTryAgain.setOnClickListener {
                tryAgainAction?.invoke()
            }
        }
    }
}

/**
 * Функция, которая упрощает работу с [ContentResultState]
 *
 * @param onStateSuccess    действие при успехе загрузки данных
 * @param tryAgainAction    бействие при неудаче (напр., повторная загрузка)
 * @param viewToShow        [ViewGroup], которую надо показать после загрузки данных
 * @param errorView      лайаут с информацией об ошибке
 */
fun ContentResultState.handleContent(
    onStateSuccess: SuccessStateAction,
    tryAgainAction: TryAgainAction? = null,
    viewToShow: ViewGroup,
    errorView: LayoutErrorView? = null
) = when (this) {
    is ContentResultState.Content -> {
        errorView?.setViewInvis()
        viewToShow.isVisible = true
        onStateSuccess.invoke(this.content)
    }
    is ContentResultState.Loading -> {
        errorView?.setViewVis()
        errorView?.setProgressVisibility(true)
        viewToShow.isVisible = false
    }
    is ContentResultState.Error -> {
        errorView?.setViewVis()
        errorView?.let {
            it.setProgressVisibility(false)
            it.setTextForTitle(this@handleContent.error.title)
            it.setTextForDescription(this@handleContent.error.description)
            it.setListenerForButton {
                tryAgainAction?.invoke()
            }
        }
        viewToShow.isVisible = false
    }
}