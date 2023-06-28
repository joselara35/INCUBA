package com.incuba.app.Adaptadores

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.incuba.app.API.datosIncuabdora
import com.incuba.app.API.respuestaIncubadora
import com.incuba.app.R

class adapterIncubadora(val respuesta:List<datosIncuabdora>): RecyclerView.Adapter<adapterIncubadora.ViewHolder>() {
    private lateinit var mlistener:onItemClickListener

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }
    fun setOnItemClickListener(listener:onItemClickListener){
        mlistener=listener
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): adapterIncubadora.ViewHolder {
        val layaotInflater: LayoutInflater = LayoutInflater.from(parent.context)
        return adapterIncubadora.ViewHolder(
            layaotInflater.inflate(R.layout.modelo_incubadora, parent, false),mlistener
        )
    }
    override fun getItemCount(): Int {
        return respuesta.size
    }
    class ViewHolder(ItemView: View, listener: adapterIncubadora.onItemClickListener) : RecyclerView.ViewHolder(ItemView) {
        val texto_ubicacion: TextView = itemView.findViewById(R.id.texto_ubicacion)
        val texto_modelo: TextView = itemView.findViewById(R.id.texto_modelo)
        val texto_numero: TextView = itemView.findViewById(R.id.texto_numero)
        val texto_cilco: TextView = itemView.findViewById(R.id.texto_ciclo)
        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)

            }
        }
    }
    //----------------------------------Acciones del recycleview---------------------------
    override fun onBindViewHolder(holder: adapterIncubadora.ViewHolder, position: Int) {

        val resp: datosIncuabdora = respuesta[position]
        holder.texto_modelo.setText(resp.marca)
        holder.texto_cilco.setText(resp.id_ciclo)
        holder.texto_ubicacion.setText(resp.ubicacion)
        holder.texto_numero.setText(resp.numero)
    }
}