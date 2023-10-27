package com.incuba.app.Roombd

import androidx.room.*

@Dao
interface ciclosDao {
    @Query("SELECT * FROM ciclos ")
    suspend fun getListaCilcosAll():List<ciclos>
    @Query("SELECT * FROM ciclos WHERE objectId = :id ")
    suspend fun getByIdCiclos(id:String):ciclos
    @Update
    suspend fun updateCiclos(data:ciclos)
    @Insert
    suspend fun insertListaCiclos(data:List<ciclos>)
    @Insert
    suspend fun insertCiclos(data:ciclos)
    @Delete
    suspend fun deleteCiclos(pedido: ciclos)
    @Query("DELETE FROM ciclos")
    suspend fun allTableDeleteCiclos()
}