package com.incuba.app.API

import com.google.gson.annotations.SerializedName

data class respuestaIncubadora(
    @SerializedName("results")
    var results: List<datosIncuabdora>,
)
data class datosIncuabdora(
    @SerializedName("objectId")
    val objectId: String,
    @SerializedName("numero")
    val numero: String,
    @SerializedName("ubicacion")
    val ubicacion: String,
    @SerializedName("marca")
    val marca: String,
    @SerializedName("id_ciclo")
    val id_ciclo: String,
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("updatedAt")
    val updatedAt: String
)
