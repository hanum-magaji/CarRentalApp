package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MapsFragment : Fragment(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private lateinit var mMap: GoogleMap

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_maps, container, false)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        return view
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Enable the marker click listener
        mMap.setOnMarkerClickListener(this)

        // Load cars from DB and add markers
        CoroutineScope(Dispatchers.IO).launch {
            val db = AppDatabase.getDatabase(requireContext())
            val cars = db.carDao().getAllCars()

            withContext(Dispatchers.Main) {
                if (cars.isNotEmpty()) {
                    for (car in cars) {
                        val location = LatLng(car.latitude, car.longitude)
                        val marker = mMap.addMarker(
                            MarkerOptions()
                                .position(location)
                                .title("${car.brand} ${car.model}")
                                .snippet("Click for details")
                        )
                        // Store the Car ID in the marker so we can retrieve it later
                        marker?.tag = car.id
                    }

                    // Move camera to the first car (Toronto area)
                    val firstCar = LatLng(cars[0].latitude, cars[0].longitude)
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(firstCar, 10f))
                }
            }
        }
    }

    // Handle Marker Click
    override fun onMarkerClick(marker: Marker): Boolean {
        // Retrieve the ID we stored in the tag
        val carId = marker.tag as? Int

        if (carId != null) {
            val intent = Intent(context, CarDetailActivity::class.java)
            intent.putExtra("CAR_ID", carId)
            startActivity(intent)
        }

        // Return false to allow the default behavior (centering camera and showing info window)
        // Return true if you want to consume the event (disable default behavior)
        return false
    }
}