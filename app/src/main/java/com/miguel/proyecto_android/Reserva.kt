package com.miguel.proyecto_android

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Reserva(
    var id: String,
    var fecha: String,
    var foto: String,
    var user: String,
): Parcelable
{
    // Necesitas un constructor vacío para Firestore
    constructor() : this("","", "", "")
}
