package com.example.trendify.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.template.interceptor.AuthInterceptor
import com.example.trendify.data.model.ProductResponse
import com.example.trendify.data.model.ProductsDetailsResponse
import com.example.trendify.data.networking.ApiServices
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel@Inject constructor(val  apiServices: ApiServices, val authInterceptor: AuthInterceptor):ViewModel()  {
    private val _productResponse = MutableStateFlow<ProductResponse?>(null)
    val productResponse: StateFlow<ProductResponse?> get() = _productResponse

    private val _DetailsproductResponse = MutableStateFlow< ProductsDetailsResponse?>(null)
    val detailsproductResponse: StateFlow<ProductsDetailsResponse?> get() = _DetailsproductResponse


    fun getDetailsProducts(id: String) {
        viewModelScope.launch {
            val response = apiServices.getProductDetails(id)
            if (response.isSuccessful) {
                _DetailsproductResponse.value = response.body()
            }
        }
    }


    fun getProducts(categoryId: Int) {
        viewModelScope.launch {
            val response = apiServices.getProducts(categoryId)
            if (response.isSuccessful) {
                _productResponse.value = response.body()
            }
        }
    }
}