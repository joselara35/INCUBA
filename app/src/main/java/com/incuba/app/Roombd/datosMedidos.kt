package com.incuba.app.Roombd

import android.support.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "parametros")
data class datosMedidos(
    @PrimaryKey()
    var objectId:String,
    @NonNull
    var temperatura:String,
    @NonNull
    var humedad:String,
    var volteos:String,
    @NonNull
    var id_incubadora:String,
    val fecha: String,
    val createdAt: String,
    val updatedAt: String
)
