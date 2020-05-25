package com.example.routes.model

import com.google.gson.annotations.SerializedName

data class Routes(
    @SerializedName(" ruta ")
    val routeName: String?,
    @SerializedName(" latitud ")
    val latitude: String?,
    @SerializedName(" longitud ")
    val longitude: String?
)