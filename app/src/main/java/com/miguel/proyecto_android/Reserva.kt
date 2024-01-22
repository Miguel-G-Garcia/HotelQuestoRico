package com.miguel.proyecto_android

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Reserva(
    var fecha: String,
    var foto: String
): Parcelable
