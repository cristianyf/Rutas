package com.example.maps.ui

import android.app.Activity
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.routes.R
import com.example.routes.model.Routes
import com.example.routes.viewmodel.ListViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_main.*

//GoogleMap.OnMarkerClickListener no lo utiliza forms
class MapsActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private lateinit var lastLocation: Location

    private lateinit var map: GoogleMap

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }

    lateinit var viewModel: ListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        viewModel = ViewModelProviders.of(this).get(ListViewModel::class.java)
        viewModel.refresh()
    }

    fun observeViewModel() {
        viewModel.routesVm.observe(this, Observer { routes ->
            routes?.let {
                placeMarkerDos(it)
            }
        })
    }

    //Agregar marcadores en el mapa
    private fun placeMarkerDos(listRoutes: List<Routes>) {
        for (route in listRoutes) {

            val latitude = route.latitude
            val longitude = route.longitude

            val latitud: Double? = latitude?.toDouble()
            val longitud: Double? = longitude?.toDouble()

            val currenLatLong = latitud?.let { longitud?.let { it1 -> LatLng(it, it1) } }

            val markerOptions = currenLatLong?.let { MarkerOptions().position(it) }
            //cambiar de color el icono
           // markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW))
            map.addMarker(markerOptions)
        }
    }

    //Agregar marcadores en el mapa
    private fun placeMarker(location: LatLng) {
        val markerOptions = MarkerOptions().position(location)
        //cambiar de color el icono
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW))
        map.addMarker(markerOptions)
    }

    //permisos en tiempo de ejecucion
    private fun setUpMap() {
        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
            return
        }

        //para poner el punto de ubicacion en el mapa
        map.isMyLocationEnabled = true

        //Para agregar los tipos de mapas
       // map.mapType = GoogleMap.MAP_TYPE_HYBRID

        //para obtener la ultima localizacion
        fusedLocationClient.lastLocation.addOnSuccessListener(this) { location ->

            if (location != null) {
                lastLocation = location

                //para hacercar el mapa
                val currenLatLong = LatLng(location.latitude, location.longitude)
                //placeMarker(currenLatLong)
                observeViewModel()
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(currenLatLong, 13f))
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        map.setOnMarkerClickListener(this)

        //Para acercarnos o alejarnos en el mapa
        map.uiSettings.isZoomControlsEnabled = true

        setUpMap()
    }

    override fun onMarkerClick(p0: Marker?) = false
}

