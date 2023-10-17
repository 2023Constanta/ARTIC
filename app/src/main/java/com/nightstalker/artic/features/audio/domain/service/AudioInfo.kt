package com.nightstalker.artic.features.audio.domain.service

import java.io.Serializable

class AudioInfo(val name: String? = null, val audioId: String) : Serializable {
    override fun toString(): String {
        return "AudioInfo(audioId='$audioId', name=$name)"
    }
}