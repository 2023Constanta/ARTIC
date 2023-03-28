package com.nightstalker.artic.features.audio.presentation.ui

import android.content.ComponentName
import android.content.Context
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.nightstalker.artic.R
import com.nightstalker.artic.databinding.FragmentAudioLookupBinding
import com.nightstalker.artic.features.ApiConstants
import com.nightstalker.artic.features.audio.domain.model.AudioFile
import com.nightstalker.artic.features.audio.player.AudioPlayerService
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
    private var boundService: AudioPlayerService? = null

    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val binder = service as AudioPlayerService.NewAudioPlayerServiceBinder
            boundService = binder.getService()

            boundService?.let {
                binding?.audioPlayer?.player = it.player
            }
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            boundService = null
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        initObservers()
    }

    private fun initObservers() = with(audioViewModel) {
        audioFileContentState.observe(viewLifecycleOwner, ::handleAudioSearch)
        audioNumber.observe(viewLifecycleOwner) {
            binding.tilAudioNumber.editText?.setText(it.toString())
        }
    }

    private fun handleAudioSearch(contentResultState: ContentResultState) =
        contentResultState.handleContent(
            onStateSuccess = { content ->
                binding.audioDescription?.text =
                    (content as AudioFile).transcript?.filterHtmlEncodedText()

                val serviceIntent = AudioPlayerService.getLaunchIntent(requireActivity())
                serviceIntent.putExtra(ApiConstants.EXTRA_AUDIO_URL, content.url)
                requireActivity().startService(serviceIntent)
                requireActivity().bindService(
                    serviceIntent, serviceConnection,
                    Context.BIND_AUTO_CREATE
                )
            },
            onStateError = {
                binding.tilAudioNumber.error = getString(R.string.audio_not_found)
            }
        )

    private fun setupView() = with(binding) {
        tilAudioNumber.editText?.setupSearch(
            textRes = R.string.enter_num_val,
            callback =  {
            audioViewModel.performSearchById(it)
         })
    }


    companion object {
        private const val TAG = "AudioLookupFragment"
    }
}