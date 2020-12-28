package com.basalam.basalamproduct.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.basalam.basalamproduct.R
import com.basalam.basalamproduct.adapters.ProductAdapter
import com.basalam.basalamproduct.adapters.ShimmerAdapter
import com.basalam.basalamproduct.db.ProductDatabase
import com.basalam.basalamproduct.model.Product
import com.basalam.basalamproduct.repository.ProductRepository
import com.basalam.basalamproduct.util.DataState
import com.basalam.basalamproduct.viewmodel.MainStateEvents
import com.basalam.basalamproduct.viewmodel.ProductViewModel
import com.basalam.basalamproduct.viewmodel.ProductViewModelProviderFactory
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    lateinit var viewModel: ProductViewModel
    lateinit var productAdapter: ProductAdapter
    lateinit var shimmerAdapter: ShimmerAdapter
    private val query: String =
        "{productSearch(size: 20) {products {id name photo(size: LARGE) { url } vendor { name } weight price rating { rating count: signals } } } }"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val repository = ProductRepository(ProductDatabase(this))
        val viewModelProviderFactory = ProductViewModelProviderFactory(repository)
        viewModel = ViewModelProvider(this, viewModelProviderFactory).get(
            ProductViewModel::class.java
        )

        setUpShimmerRecyclerView()
        subscribeObservers()
        viewModel.setStateEvent(MainStateEvents.GetProductEvents, query)
    }

    private fun subscribeObservers() {
        viewModel.product.observe(this, Observer { product ->
            when (product) {
                is DataState.Success<List<Product>> -> {
                    setUpRecyclerView()
                    hideProgressBar()
                    productAdapter.differ.submitList(product.data)
                }
                is DataState.Error -> {
                    hideProgressBar()
                    Log.d("ERROR", product.exception.toString())
                }
                is DataState.Loading -> {
                    showProgressBar()
                }
            }
        })
    }

    private fun hideProgressBar() {
        recyclerview_shimmer.visibility = View.GONE
    }

    private fun showProgressBar() {
        recyclerview_shimmer.visibility = View.VISIBLE
    }

    private fun setUpRecyclerView() {
        productAdapter = ProductAdapter()
        recyclerview_product.apply {
            adapter = productAdapter
            layoutManager = GridLayoutManager(context, 2)
        }
    }

    private fun setUpShimmerRecyclerView() {
        shimmerAdapter = ShimmerAdapter()
        recyclerview_shimmer.apply {
            adapter = shimmerAdapter
            layoutManager = GridLayoutManager(context, 2)
        }
    }
}