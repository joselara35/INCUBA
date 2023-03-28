package com.incuba.app.API

import com.google.gson.annotations.SerializedName

data class envioDatoIncubadora(
    @SerializedName("numero")
    val numero: String,
    @SerializedName("ubicacion")
    val ubicacion: String,
    @SerializedName("marca")
    val marca: String,
    @SerializedName("id_ciclo")
    val id_ciclo: String
)
