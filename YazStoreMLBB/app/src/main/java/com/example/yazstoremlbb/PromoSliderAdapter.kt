package com.example.yazstoremlbb

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView

class PromoSliderAdapter(
    private val images: List<Int>
) : RecyclerView.Adapter<PromoSliderAdapter.PromoViewHolder>() {

    // ViewHolder memegang referensi ImageView di item_promo_slider.xml
    class PromoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.imagePromo)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PromoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_promo_slider, parent, false)
        return PromoViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: PromoViewHolder,
        position: Int
    ) {
        val imageResId = images[position % images.size]
        holder.imageView.setImageResource(imageResId)
    }

    override fun getItemCount(): Int {
        return if (images.isNotEmpty()) Int.MAX_VALUE else 0
    }
}
