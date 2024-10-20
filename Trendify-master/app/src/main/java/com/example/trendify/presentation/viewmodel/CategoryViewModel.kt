package com.example.trendify.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trendify.data.model.DataXXXX
import com.example.trendify.data.model.DataXXXXXX
import com.example.template.interceptor.AuthInterceptor
import com.example.trendify.data.networking.ApiServices
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel@Inject constructor(val  apiServices: ApiServices, val authInterceptor: AuthInterceptor): ViewModel() {
    private val _CategoryResponse = MutableStateFlow<List<DataXXXX>?>(null)
    val categories: StateFlow<List<DataXXXX>?> get() = _CategoryResponse

    private val _DetailsCategoryResponse = MutableStateFlow< List<DataXXXXXX>?>(null)
    val detailscategories: StateFlow<List<DataXXXXXX>?> get() = _DetailsCategoryResponse




    fun getCategoryById(id: String) {
        viewModelScope.launch {
            val response = apiServices.getDetailsCategory(id)
            if (response.isSuccessful) {
                _DetailsCategoryResponse.value = response.body()?.data?.data
            }
        }
    }


    fun fetchCategories(page: Int = 1) {
        viewModelScope.launch {
            val response = apiServices.getCategories(page)
            if (response.isSuccessful) {
                _CategoryResponse.value = response.body()?.data?.data
            }
        }
    }
}