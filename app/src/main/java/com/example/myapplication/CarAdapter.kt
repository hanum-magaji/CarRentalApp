package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CarAdapter(
    private val cars: List<Car>,
    private val onItemClick: (Car) -> Unit
) : RecyclerView.Adapter<CarAdapter.CarViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.car_list_item, parent, false)
        return CarViewHolder(view)
    }

    override fun onBindViewHolder(holder: CarViewHolder, position: Int) {
        val car = cars[position]

        // Logic Check: If it's a real car (year > 0), show full details.
        // If it's a Category (year == 0), just show the name (stored in model).
        if (car.year > 0) {
            holder.carNameTextView.text = "${car.brand} ${car.model} ${car.year}"
        } else {
            holder.carNameTextView.text = car.model // e.g., "Sedans"
        }

        holder.carImageView.setImageResource(car.imageResId)

        holder.itemView.setOnClickListener {
            onItemClick(car)
        }
    }

    override fun getItemCount() = cars.size

    class CarViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val carImageView: ImageView = itemView.findViewById(R.id.carImageView)
        val carNameTextView: TextView = itemView.findViewById(R.id.carNameTextView)
    }
}