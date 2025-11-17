package com.example.myapplication

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

// Updated version to 4
@Database(entities = [User::class, Car::class], version = 4, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun carDao(): CarDao

    private class AppDatabaseCallback(private val context: Context) : Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                CoroutineScope(Dispatchers.IO).launch {
                    populateDatabase(database.carDao())
                }
            }
        }

        suspend fun populateDatabase(carDao: CarDao) {
            carDao.deleteAll()

            // Added 'type' to all entries
            val cars = listOf(
                Car(brand = "Toyota", model = "Camry", year = 2025, type = "Sedan", imageResId = R.drawable.sedan, pricePerDay = 50.0, description = "Reliable sedan.", latitude = 43.6426, longitude = -79.3871),
                Car(brand = "Honda", model = "CR-V", year = 2024, type = "SUV", imageResId = R.drawable.suv, pricePerDay = 85.0, description = "Spacious SUV.", latitude = 43.9458, longitude = -78.8964),
                Car(brand = "Ford", model = "Mustang", year = 2023, type = "Sports", imageResId = R.drawable.sports_car, pricePerDay = 150.0, description = "Muscle car.", latitude = 43.8986, longitude = -78.8824),
                Car(brand = "Honda", model = "Civic", year = 2022, type = "Hatchback", imageResId = R.drawable.hatchback, pricePerDay = 45.0, description = "Efficient hatchback.", latitude = 43.6677, longitude = -79.3948)
            )
            carDao.insertCars(cars)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "car_rental_database"
                )
                    .fallbackToDestructiveMigration()
                    .addCallback(AppDatabaseCallback(context))
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}