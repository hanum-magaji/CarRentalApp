package com.example.myapplication

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CarDao {
    @Query("SELECT * FROM cars")
    suspend fun getAllCars(): List<Car>

    @Query("SELECT * FROM cars WHERE id = :id")
    suspend fun getCarById(id: Int): Car?

    // Existing query
    @Query("SELECT * FROM cars WHERE type = :type")
    suspend fun getCarsByType(type: String): List<Car>

    // *** NEW QUERY ***
    @Query("SELECT DISTINCT type FROM cars")
    suspend fun getDistinctTypes(): List<String>

    @Insert
    suspend fun insertCars(cars: List<Car>)

    @Query("DELETE FROM cars")
    suspend fun deleteAll()
}