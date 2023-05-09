package com.nightstalker.core.presentation.ui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.annotation.StringRes
import androidx.constraintlayout.widget.ConstraintLayout
import com.nightstalker.core.R
import com.nightstalker.core.databinding.ViewLayoutErrorBinding

class LayoutErrorView(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : ConstraintLayout(context, attributeSet, defStyleAttr, defStyleRes) {

    private val binding: ViewLayoutErrorBinding

    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int) : this(
        context,
        attributeSet,
        defStyleAttr,
        0
    )

    constructor(context: Context, attributeSet: AttributeSet?) : this(context, attributeSet, 0)
    constructor(context: Context) : this(context, null)

    init {
        val inflater = LayoutInflater.from(context)
        inflater.inflate(R.layout.view_layout_error, this, true)
        binding = ViewLayoutErrorBinding.bind(this)
        initAttrs(attributeSet, defStyleAttr, defStyleRes)
    }

    private fun initAttrs(attributeSet: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) {
        if (attributeSet == null) return
        val typedArray = context.obtainStyledAttributes(
            attributeSet,
            R.styleable.LayoutErrorView,
            defStyleAttr,
            defStyleRes
        )

        with(binding) {
            val applyButtonText = typedArray.getString(R.styleable.LayoutErrorView_applyButtonText)
            btnErrorTryAgain.text = applyButtonText ?: context.getText(R.string.refresh)

            val title = typedArray.getString(R.styleable.LayoutErrorView_errorTitleText)
            textErrorTitle.text = title ?: context.getText(R.string.network_error_title)

            val description = typedArray.getString(R.styleable.LayoutErrorView_errorDescriptionText)
            textErrorDescription.text =
                description ?: context.getText(R.string.network_error_description)

            val isProgressMode =
                typedArray.getBoolean(R.styleable.LayoutErrorView_loadingProgressMode, false)

            // Visibility
            if (isProgressMode) {
                pbLoading.visibility = VISIBLE
                btnErrorTryAgain.visibility = INVISIBLE
                textErrorTitle.visibility = INVISIBLE
                textErrorDescription.visibility = INVISIBLE
            } else {
                pbLoading.visibility = GONE
                btnErrorTryAgain.visibility = VISIBLE
                textErrorTitle.visibility = VISIBLE
                textErrorDescription.visibility = VISIBLE
            }
        }

        typedArray.recycle()
    }

    fun setListenerForButton(action: () -> Unit) {
        binding.btnErrorTryAgain.setOnClickListener {
            action.invoke()
        }
    }

    fun setTextForTitle(@StringRes res: Int) {
        binding.textErrorTitle.setText(res)
    }

    fun setTextForDescription(@StringRes res: Int) {
        binding.textErrorDescription.setText(res)
    }

    fun setProgressVisibility(visible: Boolean) = with(binding) {
        if (visible) {
            binding.root.visibility = VISIBLE
            pbLoading.visibility = VISIBLE
            btnErrorTryAgain.visibility = INVISIBLE
            textErrorTitle.visibility = INVISIBLE
            textErrorDescription.visibility = INVISIBLE
        } else {
            pbLoading.visibility = GONE
            btnErrorTryAgain.visibility = VISIBLE
            textErrorTitle.visibility = VISIBLE
            textErrorDescription.visibility = VISIBLE
        }
    }

    fun setViewInvis() {
        binding.root.visibility = GONE
    }

    fun setViewVis() {
        binding.root.visibility = VISIBLE
    }
}