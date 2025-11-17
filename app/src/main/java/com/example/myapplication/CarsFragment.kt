package com.example.myapplication

import android.content.Intent
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

class CarsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_list, container, false)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)

        // 1. Check if we were sent a specific type to filter by (e.g., "SUV")
        val filterType = arguments?.getString("FILTER_TYPE")

        CoroutineScope(Dispatchers.IO).launch {
            val db = AppDatabase.getDatabase(requireContext())

            // 2. Decide which query to run
            val cars = if (filterType != null) {
                db.carDao().getCarsByType(filterType) // Only specific type
            } else {
                db.carDao().getAllCars() // Show everything
            }

            withContext(Dispatchers.Main) {
                recyclerView.adapter = CarAdapter(cars) { selectedCar ->
                    val intent = Intent(context, CarDetailActivity::class.java)
                    intent.putExtra("CAR_ID", selectedCar.id)
                    startActivity(intent)
                }
            }
        }
        return view
    }

    // Helper to create this fragment with a filter easily
    companion object {
        fun newInstance(filterType: String): CarsFragment {
            val fragment = CarsFragment()
            val args = Bundle()
            args.putString("FILTER_TYPE", filterType)
            fragment.arguments = args
            return fragment
        }
    }
}