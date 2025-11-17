package com.example.myapplication

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cars")
data class Car(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val brand: String,
    val model: String,
    val year: Int,
    val type: String, // New Identifier
    val imageResId: Int,
    val pricePerDay: Double,
    val description: String,
    val latitude: Double,
    val longitude: Double
)