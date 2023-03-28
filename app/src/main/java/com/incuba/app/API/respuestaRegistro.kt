package com.incuba.app.API

import com.google.gson.annotations.SerializedName

data class respuestaRegistro(
    @SerializedName("objectId")
    var objectId: String,
    @SerializedName("createdAt")
    var createdAt: String,
    @SerializedName("sessionToken")
    var sessionToken: String
)
