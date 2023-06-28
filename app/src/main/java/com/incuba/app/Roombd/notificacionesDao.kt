package com.incuba.app.Roombd

import androidx.room.*

@Dao
interface notificacionesDao {
    @Query("SELECT * FROM notificaciones ")
    suspend fun getListaNotificacionesAll():List<notificaciones>

    @Query("SELECT * FROM notificaciones WHERE Id_notificacion = :id ")
    suspend fun getByIdNotificaciones(id:String):notificaciones

    @Update
    suspend fun updateNotificaciones(pedido:notificaciones)

    @Insert
    suspend fun insertListaNotificaciones(data:List<notificaciones>)

    @Insert
    suspend fun insertNotificaciones(data:notificaciones)

    @Delete
    suspend fun deleteNotificaciones(pedido: notificaciones)

    @Query("DELETE FROM notificaciones")
    suspend fun allTableDeleteNotificaciones()
}