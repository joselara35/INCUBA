package com.incuba.app.Roombd

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "usuarios")
data class usuarios(
    @PrimaryKey()
    val objectId: String,
    val fecha_fin: String,
    val fehca_comiezo: String,
    val humedad: String,
    val temperatura_ideal: String,
    val tiempo_ciclo: String,
    val tipo_huevo: String,
    val createdAt: String,
    val updatedAt: String
)
