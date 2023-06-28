package com.incuba.app.Adaptadores

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.incuba.app.API.datosIncuabdora
import com.incuba.app.R
import com.incuba.app.Roombd.notificaciones

class adapterNotificaciones(val respuesta:List<notificaciones>): RecyclerView.Adapter<adapterNotificaciones.ViewHolder>()  {
    private lateinit var mlistener:onItemClickListener

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }
    fun setOnItemClickListener(listener: adapterNotificaciones.onItemClickListener){
        mlistener=listener
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): adapterNotificaciones.ViewHolder {
        val layaotInflater: LayoutInflater = LayoutInflater.from(parent.context)
        return adapterNotificaciones.ViewHolder(
            layaotInflater.inflate(R.layout.modelo_notificaciones, parent, false),mlistener
        )
    }
    override fun getItemCount(): Int {
        return respuesta.size
    }
    class ViewHolder(ItemView: View, listener: adapterNotificaciones.onItemClickListener) : RecyclerView.ViewHolder(ItemView) {
        val texto_fecha: TextView = itemView.findViewById(R.id.texto_fechaNoti)
        val texto_mensaje: TextView = itemView.findViewById(R.id.texto_mensajeNoti)
        val img_mensaje: ImageView = itemView.findViewById(R.id.img_notificacion)
        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
    }
    override fun onBindViewHolder(holder: adapterNotificaciones.ViewHolder, position: Int) {

        val resp: notificaciones = respuesta[position]
        holder.texto_mensaje.setText(resp.mensaje)
        holder.texto_fecha.setText(resp.fecha)
        //holder.img_mensaje.setText(resp.ubicacion)
    }
}