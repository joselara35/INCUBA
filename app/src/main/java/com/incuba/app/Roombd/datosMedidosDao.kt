package com.incuba.app.Roombd

import androidx.room.*

@Dao
interface datosMedidosDao {
    @Query("SELECT * FROM parametros ")
    suspend fun getListaParametrosAll():List<datosMedidos>

    @Query("SELECT * FROM parametros WHERE objectId = :id ")
    suspend fun getByIdParametros(id:String):datosMedidos

    @Update
    suspend fun updateParametros(pedido:datosMedidos)

    @Insert
    suspend fun insertListaParametros(data:List<datosMedidos>)

    @Insert
    suspend fun insertParametros(data:datosMedidos)

    @Delete
    suspend fun deleteParametros(pedido: datosMedidos)

    @Query("DELETE FROM parametros")
    suspend fun allTableDeleteParametros()
}