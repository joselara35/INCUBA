package com.incuba.app.API

import com.google.gson.annotations.SerializedName

data class respuestaLogin(
    @SerializedName("objectId")
    val objectId: String,
    @SerializedName("username")
    val username: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("updatedAt")
    val updatedAt: String,
    @SerializedName("rol")
    val rol: String,
    @SerializedName("n_empresa")
    val n_Empresa: String,
    @SerializedName("nombre")
    val nombre: String,
    @SerializedName("ACL")
    val acl: ACL1,
    @SerializedName("sessionToken")
    val sessionToken: String
)

data class ACL1(
    @SerializedName("*")
    val read: Read,
    @SerializedName("Ba1SxJ7gX9")
    val readWrite: ReadWrite
)

data class Read(
    @SerializedName("read")
    val read: Boolean
)

data class ReadWrite(
    @SerializedName("read") val read: Boolean,
    @SerializedName("write") val write: Boolean
)
