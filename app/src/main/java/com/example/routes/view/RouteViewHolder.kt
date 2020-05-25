package com.example.routes.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.routes.R
import com.example.routes.model.Routes
import kotlinx.android.synthetic.main.item_route.view.*

class RouteListAdapter(var routes: ArrayList<Routes>) :
    RecyclerView.Adapter<RouteListAdapter.RouteViewHolder>() {

    fun updateRoutes(newRoutes: List<Routes>) {
        routes.clear()
        routes.addAll(newRoutes)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = RouteViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_route, parent, false)
    )

    override fun getItemCount() = routes.size

    override fun onBindViewHolder(holder: RouteViewHolder, position: Int) {
        holder.bind(routes[position])
    }

    class RouteViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val routeName = view.nameTextViewItem
        private val latitud = view.latitudTextView
        private val longitud = view.longitudTextView

        fun bind(route: Routes) {
            routeName.text = "Ruta: " + route.routeName
            latitud.text =   "Latitud: " + route.latitude
            longitud.text =  "Longitud: " + route.longitude
        }
    }

}