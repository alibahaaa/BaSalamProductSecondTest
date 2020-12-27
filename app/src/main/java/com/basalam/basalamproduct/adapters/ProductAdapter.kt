package com.basalam.basalamproduct.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.basalam.basalamproduct.R
import com.basalam.basalamproduct.databinding.ProductsLayoutBinding
import com.basalam.basalamproduct.model.Product
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.products_layout.view.*
import java.text.DecimalFormat
import java.text.NumberFormat

/*
*********
* //this is our product recycler view , I use data binding !
*********
 */

class ProductAdapter : RecyclerView.Adapter<ProductAdapter.ProductsViewHolder>() {


    inner class ProductsViewHolder(val binding: ProductsLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)


    //here we check if any data changed , replace it !
    private val differCallback = object : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }
    }

    //new data (Our Product list)
    val differ = AsyncListDiffer(this, differCallback)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ProductsLayoutBinding.inflate(layoutInflater)

        return ProductsViewHolder(binding)


    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: ProductsViewHolder, position: Int) {


        val product = differ.currentList[position]

        holder.binding.product = product
        holder.binding.executePendingBindings()


    }


}