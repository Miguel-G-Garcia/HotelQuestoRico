package com.miguel.proyecto_android.model

data class Note(
    var id: String = "",
    val userId: String,
    val title: String,
    val description: String
)
