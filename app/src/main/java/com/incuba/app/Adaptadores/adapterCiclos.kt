package com.incuba.app.Adaptadores

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.incuba.app.API.datosCiclos
import com.incuba.app.API.datosIncuabdora
import com.incuba.app.API.respuestaIncubadora
import com.incuba.app.R

class adapterCiclos(val respuesta:List<datosCiclos>): RecyclerView.Adapter<adapterCiclos.ViewHolder>() {
    private lateinit var mlistener:onItemClickListener

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }
    fun setOnItemClickListener(listener:onItemClickListener){
        mlistener=listener
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): adapterCiclos.ViewHolder {
        val layaotInflater: LayoutInflater = LayoutInflater.from(parent.context)
        return adapterCiclos.ViewHolder(
            layaotInflater.inflate(R.layout.modelo_ciclos, parent, false),mlistener
        )
    }
    override fun getItemCount(): Int {
        return respuesta.size
    }
    class ViewHolder(ItemView: View, listener: adapterCiclos.onItemClickListener) : RecyclerView.ViewHolder(ItemView) {
        val texto_tipoHuevo: TextView = itemView.findViewById(R.id.texto_ciclo_tipo)
        val texto_humedad: TextView = itemView.findViewById(R.id.texto_ciclo_humedad)
        val texto_temp: TextView = itemView.findViewById(R.id.texto_ciclo_temp)
        val texto_duracion: TextView = itemView.findViewById(R.id.texto_ciclo_tiempo)
        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)

            }
        }
    }
    //----------------------------------Acciones del recycleview---------------------------
    override fun onBindViewHolder(holder: adapterCiclos.ViewHolder, position: Int) {

        val resp: datosCiclos = respuesta[position]
        holder.texto_tipoHuevo.setText(resp.tipo_huevo)
        holder.texto_humedad.setText(resp.humedad)
        holder.texto_temp.setText(resp.temperatura_ideal)
        holder.texto_duracion.setText(resp.tiempo_ciclo)
    }
}