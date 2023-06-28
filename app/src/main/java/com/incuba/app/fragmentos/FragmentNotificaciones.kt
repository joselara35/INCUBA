package com.incuba.app.fragmentos

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.incuba.app.Adaptadores.adapterNotificaciones
import com.incuba.app.R
import com.incuba.app.Roombd.baseD
import com.incuba.app.Roombd.notificaciones
import com.incuba.app.auxiliar.MainFragmentActionListener
import com.incuba.app.databinding.FragmentNotificacionesBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.logging.Handler
import java.util.logging.Logger

class FragmentNotificaciones : Fragment() {

    private lateinit var binding: FragmentNotificacionesBinding
    private var listener: MainFragmentActionListener?=null
    lateinit var listaPedidos: List<notificaciones>

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is MainFragmentActionListener) {
            listener = context
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNotificacionesBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //---------------------------acciones--------------------------
        cargar_notificaciones()

    }

    private fun cargar_notificaciones() {
        var dataBase: baseD = Room
            .databaseBuilder(requireContext(), baseD::class.java, baseD.DATABASE_NAME)
            .build()
        CoroutineScope(Dispatchers.IO).launch {
            listaPedidos = dataBase.notificacionesDao().getListaNotificacionesAll()
            rellenarRecycleViewNotificaciones()
        }
    }

    private fun rellenarRecycleViewNotificaciones() {
        Log.d("BD", listaPedidos.toString())
        activity?.runOnUiThread(java.lang.Runnable {
            android.os.Handler().postDelayed({
                //binding.animationEspera.visibility=View.GONE
                try {
                    //binding.textoError.visibility=View.GONE
                    val recycle_area: RecyclerView = binding.RecycleNotificaciones
                    var adapter = adapterNotificaciones(listaPedidos)
                    recycle_area.layoutManager = LinearLayoutManager(
                        requireContext(),
                        LinearLayoutManager.VERTICAL, false
                    )
                    recycle_area.adapter = adapter
                    adapter!!.setOnItemClickListener(object : adapterNotificaciones.onItemClickListener {
                        override fun onItemClick(position: Int) {
                            //----------------------------------------
                        }
                    })
                } catch (Ex: Exception) {
                    // binding.textoError.visibility=View.VISIBLE
                }

            }, 1000)
        })
    }
    override fun onDetach() {
        super.onDetach()
        listener = null
    }
}