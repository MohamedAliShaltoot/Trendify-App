package com.example.template.interceptor

import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(var authToken:String?=null) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()
        authToken?.let {
            requestBuilder.addHeader("Authorization","Bearer $it")
        }
        return chain.proceed(requestBuilder.build())
    }

    fun setToken(token:String){
        authToken = token
    }

}