package com.nightstalker.artic.features.audio.player.utils

/**
 * Создатель ссылки на аудио файл
 *
 * Используется для получения аудиофайла с описанием и/или историей экспоната
 *
 * @author Tamerlan Mamukhov on 2022-11-02
 */
object AudioFileLinkCreator {
    fun create(soundId: String) = "https://www.artic.edu/assets/$soundId"
}