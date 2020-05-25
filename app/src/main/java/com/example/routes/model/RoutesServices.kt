package com.example.routes.model

import com.example.routes.di.DaggerApiComponent
import io.reactivex.Single
import javax.inject.Inject

class RoutesServices {

    @Inject
    lateinit var api: RoutesApi

    init {
        DaggerApiComponent.create().inject(this)
    }

    fun getRoutes(): Single<List<Routes>> {
        return api.getRoutes()
    }
}