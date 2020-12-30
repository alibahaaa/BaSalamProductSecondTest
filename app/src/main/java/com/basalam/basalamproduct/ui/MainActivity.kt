package com.basalam.basalamproduct.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.basalam.basalamproduct.R
import com.basalam.basalamproduct.adapters.ProductAdapter
import com.basalam.basalamproduct.adapters.ShimmerAdapter
import com.basalam.basalamproduct.db.ProductDatabase
import com.basalam.basalamproduct.repository.ProductRepository
import com.basalam.basalamproduct.thread.ThreadExecutor
import com.basalam.basalamproduct.viewmodel.ProductViewModel
import com.basalam.basalamproduct.viewmodel.ProductViewModelProviderFactory
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    lateinit var viewModel: ProductViewModel
    lateinit var productAdapter: ProductAdapter
    lateinit var shimmerAdapter: ShimmerAdapter
    private val isLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    private val query: String =
        "{productSearch(size: 4) {products {id name photo(size: LARGE) { url } vendor { name } weight price rating { rating count: signals } } } }"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val repository = ProductRepository(ProductDatabase(this), ThreadExecutor(), isLoading)
        val viewModelProviderFactory = ProductViewModelProviderFactory(repository, query)
        viewModel = ViewModelProvider(this, viewModelProviderFactory).get(
            ProductViewModel::class.java
        )

        setUpShimmerRecyclerView()

        isLoading.observe(this, Observer { isLoading ->
            if (isLoading) {
                showShimmer()
            } else hideShimmer()
        })

        subscribeObservers()
    }

    private fun subscribeObservers() {
        viewModel.cachedResponse.observe(this, Observer { product ->
            setUpRecyclerView()
            productAdapter.differ.submitList(product)
        })
    }

    private fun hideShimmer() {
        recyclerview_shimmer.visibility = View.GONE
    }

    private fun showShimmer() {
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