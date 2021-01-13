package com.basalam.basalamproduct.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.basalam.basalamproduct.databinding.ProductLayBinding
import com.basalam.basalamproduct.model.Product
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductAdapter @Inject constructor() :
    RecyclerView.Adapter<ProductAdapter.ProductsViewHolder>() {
    inner class ProductsViewHolder(val binding: ProductLayBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val differCallback = object : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ProductLayBinding.inflate(layoutInflater)
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