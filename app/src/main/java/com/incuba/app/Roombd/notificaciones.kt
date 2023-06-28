package com.incuba.app.Roombd

import android.support.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "notificaciones")
data class notificaciones(
    @PrimaryKey(autoGenerate = true)
    var Id_notificacion:Int=0,
    @NonNull
    var mensaje:String,
    @NonNull
    var estado:String,
    var tipo:String,
    @NonNull
    var fecha:String
)
