package com.incuba.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.preference.PreferenceManager
import com.incuba.app.API.ApiService
import com.incuba.app.API.respuestaIncubadora
import com.incuba.app.API.respuestaParametros
import com.incuba.app.databinding.ActivityLoginBinding
import com.incuba.app.databinding.ActivityParametrosBinding
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

class ParametrosActivity : AppCompatActivity() {

    private lateinit var binding:ActivityParametrosBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityParametrosBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //-------------------------------------------------------------
        obtenerListaMenu()
        //------------------------------------------------------------
        binding.btnAtraas.setOnClickListener {
            onBackPressed()
        }

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
            .addInterceptor(logging)
            .build()
        return Retrofit.Builder()
            .baseUrl("https://parseapi.back4app.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }
    private fun obtenerListaMenu() {
        val shareprefs = PreferenceManager.getDefaultSharedPreferences(this)
        val idIncubadora = shareprefs.getString("idIncu_key", "0")!!
        try {
            CoroutineScope(Dispatchers.IO).launch {
                val call: Response<respuestaParametros> = getRetrofit().create(ApiService::class.java)
                    .getObtenerParametros("classes/parametros?where=%7B%20%22id_incubadora%22%3A%20%22${idIncubadora}%22%20%7D")
                val listaPara: respuestaParametros? = call.body()
                if (call.isSuccessful) {
                    withContext(Dispatchers.Main) {
                        listaParametros(listaPara!!)  //-------------Pasar Respuesta obtenido a metodo
                    }
                } else { //--------------------Respuesta Error------------------------------
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
            //toastExt("Error de conexión con el servidor")
        }
    }

    private fun listaParametros(listaPara: respuestaParametros) {
        Logger.addLogAdapter(AndroidLogAdapter())
        var userID: String = listaPara!!.results[0].objectId
        Logger.d(userID)
        Logger.d(listaPara?.results)
        binding.textId.setText("Id de incubadora: $userID")
        binding.textTemperatura.setText(listaPara!!.results[0].temperatura)
        binding.textHumedad.setText(listaPara!!.results[0].humedad)
        binding.textVolteo.setText(listaPara!!.results[0].volteos)
        binding.textReloj.setText(listaPara!!.results[0].fecha)
        binding.textRelojHu.setText(listaPara!!.results[0].fecha)
        binding.textRelojVolteo.setText(listaPara!!.results[0].fecha)
    }

    //----------Mostrar errores en las respuestas de API------------------------
    fun ToasDeError(code: String, error: String) {
        Logger.addLogAdapter(AndroidLogAdapter())
        Logger.d("$code $error")
    }
}