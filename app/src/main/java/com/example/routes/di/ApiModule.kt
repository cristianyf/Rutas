package com.example.routes.di

import com.example.routes.model.RoutesApi
import com.example.routes.model.RoutesServices
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
class ApiModule {

    private val BASE_URL = "https://my-json-server.typicode.com/cristianyf/demo/"

    @Provides
    fun provideRoutesApi(): RoutesApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(RoutesApi::class.java)
    }

    @Provides
    fun provideRoutesService(): RoutesServices {
        return RoutesServices()
    }
}