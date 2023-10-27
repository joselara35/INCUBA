package com.incuba.app.Roombd

import androidx.room.*

@Dao
interface usuariosDao {
    @Query("SELECT * FROM usuarios ")
    suspend fun getListaAll():List<usuarios>
    @Query("SELECT * FROM usuarios WHERE objectId = :id ")
    suspend fun getById(id:String):usuarios
    @Update
    suspend fun updateData(data:usuarios)
    @Insert
    suspend fun insertListaData(data:List<usuarios>)
    @Insert
    suspend fun insertData(data:usuarios)
    @Delete
    suspend fun deleteData(pedido: usuarios)
    @Query("DELETE FROM usuarios")
    suspend fun allTableDeleteData()
}