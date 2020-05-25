package com.example.routes.di

import com.example.routes.model.RoutesServices
import dagger.Component

@Component(modules = [ApiModule::class])
interface ApiComponent {
    fun inject(Service: RoutesServices)
}