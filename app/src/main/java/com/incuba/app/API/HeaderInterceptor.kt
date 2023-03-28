package com.incuba.app.API

import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader("accept", "application/json")
            .addHeader("X-Parse-Application-Id", "8YUoulxckhk5MxlKfG4vK2P9GNOShPAUM0ZEG991")
            .addHeader("X-Parse-REST-API-Key", "GBc1zmcJWs9iyCzq8AsOcjlWJJnpTrhDdOPzKfUy")
            .addHeader("X-Parse-Revocable-Session", "1")
            .build()
        return chain.proceed(request)
    }
}