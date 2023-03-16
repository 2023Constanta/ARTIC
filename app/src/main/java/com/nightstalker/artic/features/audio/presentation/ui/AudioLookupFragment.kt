package com.nightstalker.artic.features.audio.presentation.ui

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.nightstalker.artic.R
import com.nightstalker.artic.core.presentation.ext.handleContents
import com.nightstalker.artic.core.presentation.model.ContentResultState
import com.nightstalker.artic.databinding.FragmentAudioLookupBinding
import com.nightstalker.artic.features.ApiConstants
import com.nightstalker.artic.features.audio.presentation.viewmodel.AudioViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

/**
 * Фрагмент для поиска аудио
 *
 * @author Tamerlan Mamukhov on 2022-11-01
 */
class AudioLookupFragment : Fragment(R.layout.fragment_audio_lookup) {

    private val audioViewModel: AudioViewModel by sharedViewModel()
    private val binding: FragmentAudioLookupBinding by viewBinding(FragmentAudioLookupBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        initObservers()
    }

    private fun initObservers() = with(audioViewModel) {
        audioFileContentState.observe(viewLifecycleOwner, ::handleAudioSearch)
        audioNumber2.observe(viewLifecycleOwner) {
            binding.audioNumber.editText?.setText(it.toString())
        }
    }

    private fun handleAudioSearch(contentResultState: ContentResultState) =
        contentResultState.handleContents(
            onStateSuccess = {
            },
            onStateError = {
                binding.audioNumber.error = getString(R.string.audio_not_found)
            }
        )

    private fun setupView() =
        with(binding) {
            val tv = audioNumber.editText

            audioNumber.setStartIconOnClickListener {
                searchAudio(tv?.text?.trim().toString().toInt())
                var soundId = 0
                if (tv?.text?.toString()?.isNotEmpty() == true) {
                    soundId = tv.text.toString().toInt()
                }

                findNavController().navigate(
                    R.id.audioPlayerBottomSheetDialog,
                    args = bundleOf(ApiConstants.KEY_AUDIO_NUMBER to soundId)
                )

            }
        }

    private fun searchAudio(sequence: Int = 0): Boolean =
        when (sequence) {
            null -> {
                true
            }
            else -> {
                audioViewModel.performSearchById(sequence)
                audioViewModel.audioNumber2.value = sequence
                true
            }
        }

    companion object {
        private const val TAG = "AudioLookupFragment"
    }
}