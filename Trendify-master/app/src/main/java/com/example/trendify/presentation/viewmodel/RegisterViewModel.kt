package com.example.trendify.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.template.interceptor.AuthInterceptor
import com.example.trendify.data.model.RegisterRequest
import com.example.trendify.data.model.RegisterResponse
import com.example.trendify.data.networking.ApiServices
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel

class RegisterViewModel @Inject constructor(val  apiServices: ApiServices ,val  authInterceptor: AuthInterceptor) :ViewModel() {

    val _regitserResponse = MutableStateFlow<RegisterResponse?>(null)
    val regitserResponse = _regitserResponse
    val registerResponse : StateFlow<RegisterResponse?> get() = _regitserResponse

     fun register(Name: String, Email: String, Password: String, Phone: Int, onRegiserterSuccess:()->Unit ) {
        viewModelScope.launch {
            val response = apiServices.register(RegisterRequest(Name = Name, Email = Email, Password = Password , Phone = Phone))
            if (response.isSuccessful){
                _regitserResponse.value = response.body()
                authInterceptor.setToken(response.body()?.data?.token?:"")
                onRegiserterSuccess()
            }
        }

    }
}