package com.incuba.app.Roombd

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [notificaciones::class,datosMedidos::class,
        ciclos::class,usuarios::class],
    version = 1
)
abstract class baseD:RoomDatabase() {
    abstract fun notificacionesDao():notificacionesDao
    abstract fun datosMedidosDao():datosMedidosDao
    abstract fun ciclosDao():ciclosDao
    abstract fun usuariosDao():usuariosDao

    companion object{
        const val   DATABASE_NAME="baseD"
    }
}