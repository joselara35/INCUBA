package com.elrosal.app.api

import com.google.gson.annotations.SerializedName

data class respuestaRegistroApi(
    @SerializedName("objectId")
    var objectId: String,
    @SerializedName("createdAt")
    var createdAt: String
)
