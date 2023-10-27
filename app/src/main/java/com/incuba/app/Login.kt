package com.incuba.app

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.preference.PreferenceManager
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.incuba.app.API.ApiService
import com.incuba.app.API.respuestaLogin
import com.incuba.app.databinding.ActivityLoginBinding
import com.incuba.app.databinding.ActivitySplashScreenBinding
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class Login : AppCompatActivity() {
    private lateinit var binding:ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        binding= ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var versionName : String = applicationContext.packageManager.getPackageInfo(packageName, 0).versionName
        binding.txtVersion.setText("Versión $versionName")
        //---------------------------------------------------------------
        binding.btnEntrar.setOnClickListener {
            val connectivityManager =
                getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = connectivityManager.activeNetworkInfo

            //--------verifica la conexion de internet para continuar-----------------------------
            if (networkInfo != null && networkInfo.isConnected) {
                var correo = binding.entradaUsuario.text.toString()
                if (binding.entradaUsuario.text.isEmpty() || binding.entradaPws.text.isEmpty()) {
                    Toast.makeText(this,"Entre los datos requeridos",Toast.LENGTH_SHORT).show()
                } else {
                        establecer_espera()
                    autenticarUsuario()
                }
            } else {
                Toast.makeText(this,"Active el Internet para seguir",Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun establecer_espera() {
        binding.animationEspera2.visibility=View.VISIBLE
        binding.btnEntrar.background.setTint(ContextCompat.getColor(this, R.color.grisOscuro))
        binding.btnEntrar.isEnabled=false
    }
    private fun restaurar_espera() {
        binding.animationEspera2.visibility=View.GONE
        binding.btnEntrar.isEnabled=true
        binding.btnEntrar.background.setTint(ContextCompat.getColor(this, R.color.naranja))
    }

    ////------------------API-RESET-Retrofit------------------------------------------/////
    private fun getRetrofit(): Retrofit {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)

        val client: OkHttpClient = OkHttpClient.Builder()
            .connectTimeout(1, TimeUnit.MINUTES)
            .writeTimeout(1, TimeUnit.MINUTES) // write timeout
            .readTimeout(1, TimeUnit.MINUTES) // read timeout
            .addInterceptor(logging)
            .build()
        return Retrofit.Builder()
            .baseUrl("https://parseapi.back4app.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }
    private fun autenticarUsuario() {
        try {
            CoroutineScope(Dispatchers.IO).launch {
                val call: Response<respuestaLogin> = getRetrofit().create(ApiService::class.java)
                    .getLogin(
                        binding.entradaUsuario.text.toString(),
                        binding.entradaPws.text.toString()
                    )
                val tokenObtenido: respuestaLogin? = call.body()
                //--------------------Pasar a hilo principal--------------------------
                if (call.isSuccessful) {
                    withContext(Dispatchers.Main) {
                        TokenEnviado(tokenObtenido)
                    }
                } else {//---------------si falla la interaccion con la api-------------------
                    if (call.code() == 400 || call.code() == 404 ) {
                        var jsonObject: JSONObject? = null
                        try {
                            jsonObject = JSONObject(call.errorBody()?.string())
                            val Codigo = jsonObject!!.getString("code")
                            val Errors = jsonObject!!.getString("error")
                            withContext(Dispatchers.Main) {
                                ToasDeError(Codigo,Errors)
                            }
                        } catch (e: JSONException) {
                            e.printStackTrace()
                        }
                    }
                }
            }
        } catch (e: Exception) {
            Toast.makeText(this,"Error de conexión con el servidor",Toast.LENGTH_SHORT).show()
        }
    }
    fun TokenEnviado(tokenObtenido: respuestaLogin?) {
        ///--------------Solo en modo depuracion---------------------------------------------//
        if (BuildConfig.BUILD_TYPE == "debug") {
            Logger.addLogAdapter(AndroidLogAdapter())
            var userID: String = tokenObtenido!!.objectId
            Logger.d(userID)
            Logger.d(tokenObtenido?.sessionToken)
        }
//------------------------------------Fin depuracion--------------------------------//
        val shareprefs = PreferenceManager.getDefaultSharedPreferences(this)
        val editor = shareprefs.edit()
        editor.putString("token_key", tokenObtenido?.sessionToken)
        editor.putString("empresa_key", tokenObtenido?.n_Empresa)
        editor.putString("rol_key", tokenObtenido!!.rol.toString())
        editor.putString("nombre_key", tokenObtenido!!.nombre)
        editor.putString("usuario_key", binding.entradaUsuario.text.toString())
        editor.putString("pws_key", binding.entradaPws.text.toString())
        editor.apply()
        restaurar_espera()
        val intento1 = Intent(this, MainActivity::class.java)
        startActivity(intento1)
    }

    //----------------Error con Login -- codigo 400-------------------------
    fun ToasDeError(code: String, error: String) {
        Logger.addLogAdapter(AndroidLogAdapter())
        Logger.d("$code $error")
        restaurar_espera()
        if(error.equals("Invalid username/password.")){
          binding.txtError.setText("$code usuario o contraseña incorrectos")
        }else{
            binding.txtError.setText("$code $error")
        }
    }
}