package com.incuba.app.Roombd

import android.support.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "ciclos")
data class ciclos(
    @PrimaryKey()
    val objectId: String,
    val fecha_actual: String,
    val fehca_comiezo: String,
    val humedad: String,
    val temperatura_ideal: String,
    val tiempo_ciclo: String,
    val tipo_huevo: String,
    val createdAt: String,
    val updatedAt: String
)
