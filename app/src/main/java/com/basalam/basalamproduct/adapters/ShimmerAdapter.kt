package com.basalam.basalamproduct.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.basalam.basalamproduct.R
import com.basalam.basalamproduct.model.Product
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.products_layout.view.*
import java.text.DecimalFormat
import java.text.NumberFormat


/*
*********
* here we use shimmer adapter , just for better user experience , they want to something instead of nothing! !
*********
 */

class ShimmerAdapter : RecyclerView.Adapter<ShimmerAdapter.ProductsViewHolder>() {


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