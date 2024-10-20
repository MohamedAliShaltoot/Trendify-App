package com.example.trendify.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.template.interceptor.AuthInterceptor
import com.example.trendify.data.model.AddOrDeleteCartRequest
import com.example.trendify.data.model.AddOrDeleteCartResponse
import com.example.trendify.data.model.AddOrDeleteFavRequest
import com.example.trendify.data.model.AddOrDeleteFavResponse
import com.example.trendify.data.model.GetCartsResponse
import com.example.trendify.data.model.GetFavoritesResponse
import com.example.trendify.data.networking.ApiServices
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject






@HiltViewModel
class FavoritesViewModel @Inject constructor(val apiServices: ApiServices,val authInterceptor: AuthInterceptor)  : ViewModel() {

    private val _getFavouritesResponse = MutableStateFlow<GetFavoritesResponse?>(null)
    val favoritesResponse: StateFlow<GetFavoritesResponse?> get() = _getFavouritesResponse


    private val _AddOrDeleteFavoritesResponse = MutableStateFlow<AddOrDeleteFavResponse?>(null)
    val AddOrDeleteFavoritesResponse: StateFlow<AddOrDeleteFavResponse?> get() = _AddOrDeleteFavoritesResponse


    init {
        getFavorites()
    }

    fun getFavorites() {
        viewModelScope.launch {
            val response = apiServices.getFavorites()
            if (response.isSuccessful) {
                _getFavouritesResponse.value = response.body()
            }
        }
    }
    fun  addOrDeleteFavorite(request: AddOrDeleteFavRequest,) {
        viewModelScope.launch {
            val response = apiServices.addOrDeleteFavorite(request)
            if (response.isSuccessful) {
                _AddOrDeleteFavoritesResponse.value = response.body()
            }
        }
    }

}