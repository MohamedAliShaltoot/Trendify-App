package com.example.trendify.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.template.interceptor.AuthInterceptor
import com.example.trendify.data.model.Home
import com.example.trendify.data.networking.ApiServices
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel

class HomeViewModel @Inject constructor(val  apiServices: ApiServices, val authInterceptor: AuthInterceptor):ViewModel() {

    private val _HomeResponse = MutableStateFlow<Home?>(null)
    val HomeResponse : StateFlow<Home?>get() = _HomeResponse

    init {
        getHome()
    }
    fun getHome(){
        viewModelScope.launch {
            val response = apiServices.getHome()
            if (response.isSuccessful) {
                _HomeResponse.value = response.body()
            }

        }
    }

}