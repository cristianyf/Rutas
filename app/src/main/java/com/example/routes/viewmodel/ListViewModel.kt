package com.example.routes.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.routes.model.Routes
import com.example.routes.model.RoutesServices
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class ListViewModel : ViewModel() {

    private val routesServices = RoutesServices()
    private val disposable = CompositeDisposable()

    val routesVm = MutableLiveData<List<Routes>>()
    val routesLoadErrorVm = MutableLiveData<Boolean>()
    val loadingVm = MutableLiveData<Boolean>()

    fun refresh() {
        fetchRoutes()
    }

    private fun fetchRoutes() {
        loadingVm.value = true
        disposable.add(
            routesServices.getRoutes()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<Routes>>() {
                    override fun onSuccess(value: List<Routes>?) {
                        routesVm.value = value
                        routesLoadErrorVm.value = false
                        loadingVm.value = false
                    }

                    override fun onError(e: Throwable?) {
                        routesLoadErrorVm.value = false
                        loadingVm.value = false
                    }

                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}