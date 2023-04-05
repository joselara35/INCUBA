package com.incuba.app.API

import com.google.gson.annotations.SerializedName

data class respuestaParametros(
    @SerializedName("results")
    var results: List<datosParametros>,
)
data class datosParametros(
    @SerializedName("objectId")
    val objectId: String,
    @SerializedName("temperatura")
    val temperatura: String,
    @SerializedName("humedad")
    val humedad: String,
    @SerializedName("volteos")
    val volteos: String,
    @SerializedName("id_incubadora")
    val id_incubadora: String,
    @SerializedName("fecha")
    val fecha: String,
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("updatedAt")
    val updatedAt: String
)
