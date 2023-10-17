package com.nightstalker.artic.features.audio.presentation.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.nightstalker.artic.R
import com.nightstalker.artic.databinding.FragmentAudioLookupBinding
import com.nightstalker.artic.features.audio.domain.model.AudioFile
import com.nightstalker.artic.features.audio.domain.service.AudioInfo
import com.nightstalker.artic.features.audio.domain.service.NewAudioPlayerService
import com.nightstalker.artic.features.audio.presentation.viewmodel.AudioViewModel
import com.nightstalker.core.presentation.ext.handleContent
import com.nightstalker.core.presentation.ext.primitives.filterHtmlEncodedText
import com.nightstalker.core.presentation.model.ContentResultState
import com.nightstalker.core.presentation.ui.setupSearch
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
        initObservers()
        setupViews()
    }

    private fun initObservers() = with(audioViewModel) {
        audioFileContentState.observe(viewLifecycleOwner, ::handleAudioSearch)
        audioNumber.observe(viewLifecycleOwner) {
            binding.tilAudioNumber.editText?.setText(it.toString())
        }
    }

    private fun handleAudioSearch(contentResultState: ContentResultState) =
        contentResultState.handleContent(onStateSuccess = { content ->
            binding.audioDescription.text =
                (content as AudioFile).transcript?.filterHtmlEncodedText()

            val audioInfo = AudioInfo(content.title.toString(), content.url.toString())

            with(binding) {

                flowPlayerControls.isVisible = true

                playerPlay.setOnClickListener {
                    audioTextArea.isVisible = true
                    Intent(requireContext(), NewAudioPlayerService::class.java).also {
                        it.putExtra(NewAudioPlayerService.INTENT_EXTRA, audioInfo)
                        it.action = NewAudioPlayerService.PlayerActions.START.toString()
                        requireActivity().startService(it)
                    }
                }
                playerPause.setOnClickListener {
                    Intent(requireContext(), NewAudioPlayerService::class.java).also {
                        it.action = NewAudioPlayerService.PlayerActions.PAUSE.toString()
                        requireActivity().startService(it)
                    }
                }
                playerStop.setOnClickListener {
                    flowPlayerControls.isVisible = false
                    audioTextArea.isVisible = false
                    Intent(requireContext(), NewAudioPlayerService::class.java).also {
                        it.action = NewAudioPlayerService.PlayerActions.STOP.toString()
                        requireActivity().startService(it)
                    }
                }
            }

        }, onStateError = {
            binding.tilAudioNumber.error = getString(R.string.audio_not_found)
        })

    private fun setupViews() = with(binding) {
        tilAudioNumber.editText?.setupSearch(textRes = R.string.enter_num_val, callback = {
            audioViewModel.performSearchById(it)
        })
    }


    companion object {
        private const val TAG = "AudioLookupFragment"
    }
}