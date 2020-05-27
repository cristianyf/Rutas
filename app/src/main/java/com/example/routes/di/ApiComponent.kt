package com.example.routes.di

import com.example.routes.model.RoutesServices
import com.example.routes.viewmodel.ListViewModel
import dagger.Component

@Component(modules = [ApiModule::class])
interface ApiComponent {
    fun inject(Service: RoutesServices)

    fun inject(viewModel: ListViewModel)
}