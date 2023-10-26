package com.nightstalker.artic.features.wip

import android.view.View
import androidx.core.view.isVisible
import com.nightstalker.artic.databinding.ErrorPanelBinding
import com.nightstalker.core.presentation.model.ContentResultState
import com.nightstalker.core.presentation.model.ErrorModel


typealias SuccessStateAction = (content: Any?) -> Unit
typealias ErrorStateAction = (error: ErrorModel) -> Unit
typealias LoadingStateAction = () -> Unit

private fun changeVisibilityForErrorPanel(isLoading: Boolean, binding: ErrorPanelBinding) = if (isLoading) {
    binding.pbLoadingProgress.isVisible = isLoading
    binding.textErroreTitle.isVisible = !isLoading
    binding.tvErrore.isVisible = !isLoading
    binding.btnTry.isVisible = !isLoading
} else {
    binding.pbLoadingProgress.isVisible = isLoading
    binding.textErroreTitle.isVisible = !isLoading
    binding.tvErrore.isVisible = !isLoading
    binding.btnTry.isVisible = !isLoading
}

fun ContentResultState.newFuncForHandling(
    successStateAction: SuccessStateAction,
    errorStateAction: ErrorStateAction? = null,
    loadingStateAction: LoadingStateAction? = null,
    viewToShow: View,
    errorPanelBinding: ErrorPanelBinding
    ) = when(this) {
    is ContentResultState.Content -> {
        errorPanelBinding.root.isVisible = false
        viewToShow.isVisible = true
        successStateAction.invoke(content)
    }
    is ContentResultState.Error -> {
        errorStateAction?.invoke(this.error)
        viewToShow.isVisible = false
        errorPanelBinding.root.isVisible = true
        changeVisibilityForErrorPanel(false, errorPanelBinding)
    }
    ContentResultState.Loading -> {
        loadingStateAction?.invoke()
        viewToShow.isVisible = false
        errorPanelBinding.root.isVisible = true
        changeVisibilityForErrorPanel(true, errorPanelBinding)
    }
}