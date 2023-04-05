package com.incuba.app.API

import com.elrosal.app.api.respuestaRegistroApi
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    //---------Registro de usuarios con metodo Post-----
    @Headers(
        "accept: application/json",
        "X-Parse-Application-Id: 8YUoulxckhk5MxlKfG4vK2P9GNOShPAUM0ZEG991",
        "X-Parse-REST-API-Key: GBc1zmcJWs9iyCzq8AsOcjlWJJnpTrhDdOPzKfUy",
        "X-Parse-Revocable-Session: 1"
    )
    @POST("users")
    suspend fun postRegistroUsuarios(@Body Login: jsonRegistro): Response<respuestaRegistro>
    //----->>>>>>GET-------Login-------------------------<<<<<<<<
    @Headers(
        "accept: application/json",
        "X-Parse-Application-Id: 8YUoulxckhk5MxlKfG4vK2P9GNOShPAUM0ZEG991",
        "X-Parse-REST-API-Key: GBc1zmcJWs9iyCzq8AsOcjlWJJnpTrhDdOPzKfUy",
        "X-Parse-Revocable-Session: 1"
    )
    @GET("login?")
    suspend fun getLogin(
        @Query("username") email: String,
        @Query("password") pageNumber: String
    ): Response<respuestaLogin>
    //----->>>>>>GET-------Obtener datos de las incubadoras-------------------------<<<<<<<<
    @Headers(
        "accept: application/json",
        "X-Parse-Application-Id: 8YUoulxckhk5MxlKfG4vK2P9GNOShPAUM0ZEG991",
        "X-Parse-REST-API-Key: GBc1zmcJWs9iyCzq8AsOcjlWJJnpTrhDdOPzKfUy",
        "X-Parse-Revocable-Session: 1"
    )
    @GET("classes/incubadora")
    suspend fun getObtenerIncubadoras (): Response<respuestaIncubadora>
    //----->>>>>>POST-------Agregar incubadoras al sistema-------------------------<<<<<<<<
   @Headers(
        "accept: application/json",
        "X-Parse-Application-Id: 8YUoulxckhk5MxlKfG4vK2P9GNOShPAUM0ZEG991",
        "X-Parse-REST-API-Key: GBc1zmcJWs9iyCzq8AsOcjlWJJnpTrhDdOPzKfUy",
        "X-Parse-Revocable-Session: 1"
    )
    @POST("classes/menu")
    suspend fun postRegistrarIncubadoras(@Body Registrar: envioDatoIncubadora): Response<respuestaRegistroApi>
    //----->>>>>>GET-------Obtener datos de los parametros-------------------------<<<<<<<<
    @Headers(
        "accept: application/json",
        "X-Parse-Application-Id: 8YUoulxckhk5MxlKfG4vK2P9GNOShPAUM0ZEG991",
        "X-Parse-REST-API-Key: GBc1zmcJWs9iyCzq8AsOcjlWJJnpTrhDdOPzKfUy",
        "X-Parse-Revocable-Session: 1"
    )
   // @GET("classes/parametros?")
   // suspend fun getObtenerParametros(@Query("where") id_incubadora: String): Response<respuestaParametros>
    @GET
    suspend fun getObtenerParametros(@Url url: String): Response<respuestaParametros>
}