package com.miguel.proyecto_android

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Client(
    var nombre: String,
    var gmail: String,
    var telefono: Int,
    var foto: String
): Parcelable
