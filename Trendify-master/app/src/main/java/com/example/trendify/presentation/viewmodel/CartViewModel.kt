package com.example.trendify.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.template.interceptor.AuthInterceptor
import com.example.trendify.data.model.AddOrDeleteCartRequest
import com.example.trendify.data.model.AddOrDeleteCartResponse
import com.example.trendify.data.model.GetCartsResponse
import com.example.trendify.data.networking.ApiServices
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject






@HiltViewModel
class CartViewModel @Inject constructor(val apiServices: ApiServices,val authInterceptor: AuthInterceptor)  : ViewModel() {

    private val _getCartResponse = MutableStateFlow<GetCartsResponse?>(null)
    val getCartResponse: StateFlow<GetCartsResponse?> get() = _getCartResponse


    private val _AddOrDeleteCartsResponse = MutableStateFlow<AddOrDeleteCartResponse?>(null)
    val AddOrDeleteCartsResponse: StateFlow<AddOrDeleteCartResponse?> get() = _AddOrDeleteCartsResponse





    fun getCarts() {
        viewModelScope.launch {
            val response = apiServices.getCarts()
            if (response.isSuccessful) {
                _getCartResponse.value = response.body()
            }
        }
    }
    fun addOrDeleteCarts(request: AddOrDeleteCartRequest,) {
        viewModelScope.launch {
            val response = apiServices.addOrDeleteCarts(request)
            if (response.isSuccessful) {
                _AddOrDeleteCartsResponse.value = response.body()
            }
        }
    }

}