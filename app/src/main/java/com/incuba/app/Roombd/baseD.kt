package com.incuba.app.Roombd

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [notificaciones::class,datosMedidos::class],
    version = 1
)
abstract class baseD:RoomDatabase() {
    abstract fun notificacionesDao():notificacionesDao
    abstract fun datosMedidosDao():datosMedidosDao

    companion object{
        const val   DATABASE_NAME="baseD"
    }
}