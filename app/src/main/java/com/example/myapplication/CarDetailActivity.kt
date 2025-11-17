package com.example.myapplication

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CarDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_car_detail)

        // 1. Find Views
        val backButton = findViewById<MaterialButton>(R.id.backButton)
        val imageView = findViewById<ImageView>(R.id.detailImageView)
        val nameView = findViewById<TextView>(R.id.detailNameTextView)
        val priceView = findViewById<TextView>(R.id.detailPriceTextView)
        val descView = findViewById<TextView>(R.id.detailDescriptionTextView)
        val bookButton = findViewById<MaterialButton>(R.id.bookButton)

        // 2. Back Button Logic
        backButton.setOnClickListener {
            finish()
        }

        // 3. Get Car Data
        val carId = intent.getIntExtra("CAR_ID", -1)

        if (carId != -1) {
            CoroutineScope(Dispatchers.IO).launch {
                val db = AppDatabase.getDatabase(applicationContext)
                val car = db.carDao().getCarById(carId)

                withContext(Dispatchers.Main) {
                    if (car != null) {
                        imageView.setImageResource(car.imageResId)

                        // *** THIS WAS THE FIX ***
                        // We now combine brand, model, and year instead of using 'name'
                        nameView.text = "${car.brand} ${car.model} ${car.year}"

                        priceView.text = "$${car.pricePerDay}/day"
                        descView.text = car.description
                    }
                }
            }
        }

        bookButton.setOnClickListener {
            Toast.makeText(this, "Booking feature coming soon!", Toast.LENGTH_SHORT).show()
        }
    }
}