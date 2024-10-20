package com.example.trendify.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.template.interceptor.AuthInterceptor
import com.example.trendify.data.model.LoginRequest
import com.example.trendify.data.model.LoginResponse
import com.example.trendify.data.networking.ApiServices
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(val apiServices: ApiServices , val authInterceptor: AuthInterceptor) :ViewModel() {

    private val _loginRespose = MutableStateFlow<LoginResponse?>(null)
    val loginRespose = _loginRespose
    val loginResponse : StateFlow<LoginResponse?> get() = _loginRespose

     fun login(email: String, password: String , onLoginSuccess:()->Unit, onLoginFalier:()->Unit) {
       viewModelScope.launch {
           val response = apiServices.login(LoginRequest(email = email, password = password))
          if (response.isSuccessful) {
              _loginRespose.value = response.body()
              authInterceptor.setToken(response.body()?.data?.token?:"")
              response.body()?.data?.token
              onLoginSuccess()
              onLoginFalier()

          }
       }
    }


}