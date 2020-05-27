package com.example.menu.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Switch
import com.example.maps.ui.MapsActivity
import com.example.routes.R
import com.example.routes.view.MainActivity
import kotlinx.android.synthetic.main.activity_menu.*

class Menu : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        btnMaps.setOnClickListener {
            sendMaps()
        }

        btnListRoutes.setOnClickListener {
            sendListRoutes()
        }
    }

    fun sendMaps() {
        val intent = Intent(this, MapsActivity::class.java)
        startActivity(intent)
    }

    fun sendListRoutes() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}
