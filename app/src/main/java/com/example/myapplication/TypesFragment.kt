package com.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TypesFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_list, container, false)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)

        CoroutineScope(Dispatchers.IO).launch {
            val db = AppDatabase.getDatabase(requireContext())

            // Fetch unique types
            val distinctTypes = db.carDao().getDistinctTypes()

            val typeList = distinctTypes.map { type ->
                val icon = getIconForType(type)
                Car(
                    id = 0,
                    brand = "",
                    model = "${type}s",
                    year = 0,
                    type = type,
                    imageResId = icon,
                    pricePerDay = 0.0,
                    description = "",
                    latitude = 0.0,
                    longitude = 0.0
                )
            }

            withContext(Dispatchers.Main) {
                recyclerView.adapter = CarAdapter(typeList) { clickedType ->
                    // *** LOGIC CHANGE HERE ***
                    // Instead of a Toast, we open the CarsFragment with the filter
                    val fragment = CarsFragment.newInstance(clickedType.type)

                    parentFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, fragment)
                        .addToBackStack(null) // Allows user to press "Back" to return to Types list
                        .commit()
                }
            }
        }
        return view
    }

    private fun getIconForType(type: String): Int {
        return when (type) {
            "Sedan" -> R.drawable.sedan
            "SUV" -> R.drawable.suv
            "Sports" -> R.drawable.sports_car
            "Hatchback" -> R.drawable.hatchback
            else -> R.drawable.sedan
        }
    }
}