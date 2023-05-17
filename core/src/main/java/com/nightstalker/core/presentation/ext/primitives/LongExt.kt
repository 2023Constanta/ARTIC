package com.nightstalker.core.presentation.ext.primitives

import java.util.Date

// Время нового события в календаре может быть только в будущем
fun Long.normalizeEventDateTime(): Long =
    if (this < Date().time) Date().time else this