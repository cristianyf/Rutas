package com.example.routes.model

import io.reactivex.Single
import retrofit2.http.GET

interface RoutesApi {
    @GET("Rutas")
    fun getRoutes(): Single<List<Routes>>
}