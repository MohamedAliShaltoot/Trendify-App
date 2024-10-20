package com.example.trendify.data.networking

import com.example.trendify.data.model.AddOrDeleteCartRequest
import com.example.trendify.data.model.AddOrDeleteCartResponse
import com.example.trendify.data.model.AddOrDeleteFavRequest
import com.example.trendify.data.model.AddOrDeleteFavResponse
import com.example.trendify.data.model.AllProductsResponse
import com.example.trendify.data.model.CategoryProductResponse
import com.example.trendify.data.model.CategoryResponse
import com.example.trendify.data.model.DetailsCategoryResponse
import com.example.trendify.data.model.GetCartsResponse
import com.example.trendify.data.model.GetFavoritesResponse
import com.example.trendify.data.model.Home
import com.example.trendify.data.model.LoginRequest
import com.example.trendify.data.model.LoginResponse
import com.example.trendify.data.model.ProductDetailsResponse
import com.example.trendify.data.model.ProductResponse
import com.example.trendify.data.model.ProductsDetailsResponse
import com.example.trendify.data.model.RegisterRequest
import com.example.trendify.data.model.RegisterResponse
import com.example.trendify.data.model.proudcts
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiServices {
    //implementation network function
    @POST("register")
    suspend fun register(@Body request: RegisterRequest): Response<RegisterResponse>

    @POST("login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    @GET("home")
    suspend fun getHome(): Response<Home>
    @GET("categories")
    suspend fun getCategories(@Query("page") page: Int): Response<CategoryResponse>
    @GET("categories/{id}")
    suspend fun getDetailsCategory(@Path("id")  id: String): Response<DetailsCategoryResponse>
    @GET("products")
    suspend fun getProducts(@Query("category_id") categoryId: Int): Response<ProductResponse>
    @GET("products")
    suspend fun getAllProducts(): Response<AllProductsResponse>
    @GET("products/{id}")
    suspend fun getProductDetails(@Path("id") id: String): Response<ProductsDetailsResponse>
    @GET("favorites")
    suspend fun getFavorites():Response<GetFavoritesResponse>
    @POST("favorites")
    suspend fun addOrDeleteFavorite(@Body request: AddOrDeleteFavRequest):Response<AddOrDeleteFavResponse>
    @GET("carts")
    suspend fun getCarts():Response<GetCartsResponse>
    @POST("carts")
    suspend fun addOrDeleteCarts(@Body request: AddOrDeleteCartRequest):Response<AddOrDeleteCartResponse>

}