package com.nightstalker.artic.features.audio.presentation.ui.dialog

import android.content.ComponentName
import android.content.Context.BIND_AUTO_CREATE
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.nightstalker.artic.R
import com.nightstalker.artic.databinding.FragmentAudioPlayerBottomSheetDialogBinding
import com.nightstalker.artic.features.ApiConstants
import com.nightstalker.artic.features.audio.domain.model.AudioFile
import com.nightstalker.artic.features.audio.player.AudioPlayerService
import com.nightstalker.artic.features.audio.presentation.viewmodel.AudioViewModel
import com.nightstalker.core.presentation.ext.handleContent
import com.nightstalker.core.presentation.ext.primitives.filterHtmlEncodedText
import com.nightstalker.core.presentation.model.ContentResultState
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

/**
 * Аудио плеер в виде диалога
 *
 * @author Tamerlan Mamukhov
 * @created 2022-11-20
 */
class AudioPlayerBottomSheetDialog : BottomSheetDialogFragment() {
    private var _binding: FragmentAudioPlayerBottomSheetDialogBinding? = null
    private val binding get() = _binding
    private val audioViewModel by sharedViewModel<AudioViewModel>()
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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(
            R.layout.fragment_audio_player_bottom_sheet_dialog,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentAudioPlayerBottomSheetDialogBinding.bind(view)

        initObserver()
    }

    private fun initObserver() {
        audioViewModel.audioFileContentState.observe(viewLifecycleOwner, ::handleAudio)
    }

    private fun handleAudio(contentResultState: ContentResultState) =
        contentResultState.handleContent(
            onStateSuccess = { content ->
                binding?.audioDescription?.text =
                    (content as AudioFile).transcript?.filterHtmlEncodedText()

                val serviceIntent = AudioPlayerService.getLaunchIntent(requireActivity())
                serviceIntent.putExtra(ApiConstants.EXTRA_AUDIO_URL, content.url)
                requireActivity().startService(serviceIntent)
                requireActivity().bindService(serviceIntent, serviceConnection, BIND_AUTO_CREATE)
            },
            onStateError = {
                Log.d(TAG, "handle: $it")
            }
        )


    companion object {
        private const val TAG = "AudioPlayerBottomSheet"
    }
}