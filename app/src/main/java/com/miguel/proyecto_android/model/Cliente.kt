package com.miguel.proyecto_android.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Cliente(
	var nickname: String,
	var phone: Int,
	var address: String,
): Parcelable
{
	constructor() : this("",0,"")
}

