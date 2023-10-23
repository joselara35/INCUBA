package com.incuba.app.API

import com.google.gson.annotations.SerializedName

data class respuestaUsuarios(
    @SerializedName("results")
    val results: List<datosUsuarios>
)
data class datosUsuarios(
    @SerializedName("objectId")
    val objectId: String,
    @SerializedName("fecha_fin")
    val fecha_fin: String,
    @SerializedName("fehca_comiezo")
    val fehca_comiezo: String,
    @SerializedName("humedad")
    val humedad: String,
    @SerializedName("temperatura_ideal")
    val temperatura_ideal: String,
    @SerializedName("tiempo_ciclo")
    val tiempo_ciclo: String,
    @SerializedName("tipo_huevo")
    val tipo_huevo: String,
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("updatedAt")
    val updatedAt: String
)