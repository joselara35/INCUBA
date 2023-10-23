package com.incuba.app.auxiliar

import android.content.Context
import androidx.room.Room
import com.google.android.material.snackbar.Snackbar
import com.incuba.app.Roombd.baseD
import com.incuba.app.Roombd.notificaciones
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

fun alertaNotificacion(id_incu:String,temp:Float,humedad:Float,fecha:String, context:Context) {
    if(temp<=36.50){
        guardarBDNotificacion(context,"La incubadora $id_incu tiene la temperatura muy baja en $temp",id_incu,fecha)
    }
    if(temp>=38.50){
        guardarBDNotificacion(context,"La incubadora $id_incu tiene la temperatura muy alta en $temp ",id_incu,fecha)
    }
}
fun guardarBDNotificacion(context:Context,texto:String,id_incu:String,fecha:String){
    var dataBase: baseD = Room
        .databaseBuilder(context, baseD::class.java, baseD.DATABASE_NAME)
        .build()
    CoroutineScope(Dispatchers.IO).launch {
        dataBase.notificacionesDao().insertNotificaciones(
            notificaciones(
                mensaje = texto,
                estado = "0",
                tipo = "1",
                incubadora = id_incu,
                fecha = fecha
            )
        )
        withContext(Dispatchers.Main) {
            //Snackbar.make(it, "Notificacion agregada", Snackbar.LENGTH_LONG).show()
        }
    }
}