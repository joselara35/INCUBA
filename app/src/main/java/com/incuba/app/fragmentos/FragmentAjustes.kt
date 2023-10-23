package com.incuba.app.fragmentos

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.room.Room
import com.incuba.app.Administrar_ciclos
import com.incuba.app.Administrar_incubadora
import com.incuba.app.Administrar_usuarios
import com.incuba.app.Roombd.baseD
import com.incuba.app.auxiliar.MainFragmentActionListener
import com.incuba.app.databinding.FragmentAjustesBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class FragmentAjustes : Fragment() {

    private lateinit var binding:FragmentAjustesBinding
    private var listener: MainFragmentActionListener?=null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is MainFragmentActionListener){
            listener=context
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentAjustesBinding.inflate(inflater,container,false)
        return binding.root
    }
    override fun onDetach() {
        super.onDetach()
        listener=null
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        //---------------------------acciones--------------------------

        binding.btnAdminCiclos.setOnClickListener {
            startActivity(Intent(activity, Administrar_ciclos::class.java))
        }
        binding.btnAdminIncuabdora.setOnClickListener {
            startActivity(Intent(activity, Administrar_incubadora::class.java))
        }
        binding.btnAdminUsuarios.setOnClickListener {
            startActivity(Intent(activity, Administrar_usuarios::class.java))
        }
        binding.btnBorrarNotificaciones.setOnClickListener {
            borrar_notificaciones()
        }
    }
    private fun borrar_notificaciones() {
        var dataBase: baseD = Room
            .databaseBuilder(requireContext(), baseD::class.java, baseD.DATABASE_NAME)
            .build()
        CoroutineScope(Dispatchers.IO).launch {
            dataBase.notificacionesDao().allTableDeleteNotificaciones()
            Looper.prepare()
            toastNOtificacion("Notificaciones eliminadas")
            Looper.loop()
        }
    }

    fun toastNOtificacion(texto: String) {
        Toast.makeText(requireContext(), texto, Toast.LENGTH_SHORT)
            .show()
    }
}