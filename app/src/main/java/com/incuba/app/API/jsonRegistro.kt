package com.incuba.app.API

import com.google.gson.annotations.SerializedName

data class jsonRegistro (
    @SerializedName("username")
    var username: String,
    @SerializedName("email")
    var correo: String,
    @SerializedName("password")
    var Pws: String,
    @SerializedName("rol")
    var Rol: String,
    @SerializedName("n_empresa")
    var n_empresa: String,
    @SerializedName("nombre")
    var nombre: String
        )