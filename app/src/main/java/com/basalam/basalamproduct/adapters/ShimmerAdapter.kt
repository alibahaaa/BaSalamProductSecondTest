package com.basalam.basalamproduct.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.basalam.basalamproduct.R
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ShimmerAdapter @Inject constructor() :
    RecyclerView.Adapter<ShimmerAdapter.ProductsViewHolder>() {
    inner class ProductsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsViewHolder {
        return ProductsViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.shimmer_layout, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return 8
    }

    override fun onBindViewHolder(holder: ProductsViewHolder, position: Int) {
        holder.itemView.apply {
        }
    }
}