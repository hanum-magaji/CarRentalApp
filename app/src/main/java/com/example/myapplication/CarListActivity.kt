package com.example.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CarListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_car_list)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val carRecyclerView = findViewById<RecyclerView>(R.id.carRecyclerView)
        carRecyclerView.layoutManager = LinearLayoutManager(this)

        val cars = listOf(
            Car("Sedan", R.drawable.sedan),
            Car("SUV", R.drawable.suv),
            Car("Hatchback", R.drawable.hatchback),
            Car("Sports Car", R.drawable.sports_car)
        )

        val adapter = CarAdapter(cars)
        carRecyclerView.adapter = adapter
    }
}