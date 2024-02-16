package com.miguel.proyecto_android.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Reserva(
    var id: String = "",
    var fecha: String,
    var foto: String,
    var user: String,
): Parcelable
{
    constructor() : this("","","","")
}

