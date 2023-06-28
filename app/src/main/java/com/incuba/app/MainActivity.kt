package com.incuba.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.Gravity
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.room.Room
import com.google.android.material.snackbar.Snackbar
import com.incuba.app.Roombd.baseD
import com.incuba.app.Roombd.notificaciones
import com.incuba.app.auxiliar.MainFragmentActionListener
import com.incuba.app.databinding.ActivityMainBinding
import com.incuba.app.fragmentos.FragmentAjustes
import com.incuba.app.fragmentos.FragmentInicial
import com.incuba.app.fragmentos.FragmentNotificaciones
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity(), MainFragmentActionListener {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //--------------------Definición de variable iniciales------------------------------------
        val shareprefs = PreferenceManager.getDefaultSharedPreferences(this)
        val nombre = shareprefs.getString("nombre_key", "0")!!
        val empresa = shareprefs.getString("empresa_key", "0")!!
        val rol = shareprefs.getString("rol_key", "0")!!
        val token = shareprefs.getString("token_key", "0")!!
        pasarFragment(FragmentInicial())                     //---------------Iniciar fragmento--------
        binding.textUser.setText(nombre)
        binding.textUbicacion.setText("$rol de $empresa")
        //----------------------Menu acciones------------------------------
        binding.btnCentral.setOnClickListener {
            pasarFragment(FragmentInicial())
            binding.linearLayoutMain.visibility= View.VISIBLE
            binding.contenedorUbi.visibility= View.VISIBLE
            binding.textUser.setText(nombre)
            binding.textUser.gravity = Gravity.NO_GRAVITY
            binding.textUser.setTextColor(ContextCompat.getColor(this, R.color.white))
            binding.imagInicial.setImageResource(R.drawable.ic_baseline_person_pin_24)
            binding.btnCentral.background.setTint(ContextCompat.getColor(this, R.color.naranja))
            binding.btnDerecho.background.setTint(ContextCompat.getColor(this, R.color.white))
            binding.btnIzquierdo.background.setTint(ContextCompat.getColor(this, R.color.white))
        }
        binding.btnIzquierdo.setOnClickListener {
            pasarFragment(FragmentNotificaciones())
            binding.linearLayoutMain.visibility= View.VISIBLE
            binding.contenedorUbi.visibility= View.GONE
            binding.textUser.setText("Notificaciones")
            binding.textUser.gravity = Gravity.CENTER_HORIZONTAL
            binding.textUser.setTextColor(ContextCompat.getColor(this, R.color.naranja))
            binding.imagInicial.setImageResource(R.drawable.baseline_circle_notifications)
            binding.btnCentral.background.setTint(ContextCompat.getColor(this, R.color.white))
            binding.btnDerecho.background.setTint(ContextCompat.getColor(this, R.color.white))
            binding.btnIzquierdo.background.setTint(ContextCompat.getColor(this, R.color.naranja))

        }
        binding.btnDerecho.setOnClickListener {
            pasarFragment(FragmentAjustes())
            binding.linearLayoutMain.visibility= View.GONE
            val params = binding.linearLayoutMain.layoutParams as ConstraintLayout.LayoutParams
            params.topToBottom = binding.constraintLayoutMain.id
            binding.linearLayoutMain.layoutParams = params
            binding.btnCentral.background.setTint(ContextCompat.getColor(this, R.color.white))
            binding.btnDerecho.background.setTint(ContextCompat.getColor(this, R.color.naranja))
            binding.btnIzquierdo.background.setTint(ContextCompat.getColor(this, R.color.white))
        }
        //----------------prueba notififaciones-------------------------------
        binding.textUser.setOnClickListener {
            var dataBase: baseD = Room
                .databaseBuilder(this, baseD::class.java, baseD.DATABASE_NAME)
                .build()
            CoroutineScope(Dispatchers.IO).launch {
                dataBase.notificacionesDao().insertNotificaciones(
                    notificaciones(
                        mensaje = "La incubadora de La republica tiene la temperatura muy alta",
                        estado = "0",
                        tipo = "1",
                        fecha = "15/4/23"
                    )
                )
                withContext(Dispatchers.Main) {
                    Snackbar.make(it, "Notificacion agregada", Snackbar.LENGTH_LONG).show()
                    }
                }
            }

    }
    //---------------------Metodos abtractos compartidos con los fragmentos----------
    override fun pasarFragment(fragment: Fragment) {
        val fragmnetManger = supportFragmentManager
        val fragmentTransaction = fragmnetManger.beginTransaction()
        fragmentTransaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
        fragmentTransaction.replace(R.id.fragmenContainer, fragment)
        fragmentTransaction.commit()
    }

    //---------------Presionar la tecla atras para salir---------------------------
    override fun onBackPressed() {
        AlertDialog.Builder(this)
            .setMessage("¿Desea salir de la aplicación?")
            .setCancelable(false)
            .setPositiveButton("Si"){
                    dialog,whichButton ->
                finishAffinity()
            }
            .setNegativeButton("No"){
                    dialog,whichButton ->

            }
            .show()
    }
}