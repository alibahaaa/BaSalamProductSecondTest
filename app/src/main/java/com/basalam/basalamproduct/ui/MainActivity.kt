package com.basalam.basalamproduct.ui

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.TextView
import android.widget.Toast
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
import com.basalam.basalamproduct.util.Resource
import com.basalam.basalamproduct.viewmodel.ProductViewModel
import com.basalam.basalamproduct.viewmodel.ProductViewModelProviderFactory
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    lateinit var viewModel: ProductViewModel
    lateinit var productAdapter: ProductAdapter
    lateinit var shimmerAdapter: ShimmerAdapter
    private val query: String =
        "{productSearch(size: 2) {products {id name photo(size: LARGE) { url } vendor { name } weight price rating { rating count: signals } } } }"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val repository = ProductRepository(ProductDatabase(this), ThreadExecutor())
        val viewModelProviderFactory = ProductViewModelProviderFactory(repository, query)
        viewModel = ViewModelProvider(this, viewModelProviderFactory).get(
            ProductViewModel::class.java
        )
        setUpShimmerRecyclerView()
        subscribeObservers()
    }

    private fun subscribeObservers() {
        viewModel.resource.observe(this, Observer { resource ->
            when (resource) {
                is Resource.Success -> {
                    println("Success log")
                    setUpRecyclerView()
                    hideShimmer()
                    resource.data?.observe(this, Observer { productList ->
                        productAdapter.differ.submitList(productList)
                    })
                }
                is Resource.Loading -> {
                    println("loading log")
                    showShimmer()
                }
                is Resource.Error -> {
                    println("error log ${resource.message}")
                    showRec()
                    setUpRecyclerView()
                    hideShimmer()
                    showDialog("خطا : ${resource.message!!}")
                    resource.data?.observe(this, Observer { productList ->
                        productAdapter.differ.submitList(productList)
                    })
                }
                is Resource.Empty -> {
                    hideShimmer()
                    showDialog("ایتمی برای نمایش وجود ندارد")
                    println("empty log")
                }
            }
        })
    }

    private fun hideShimmer() {
        recyclerview_shimmer.visibility = View.GONE
    }

    private fun showShimmer() {
        recyclerview_shimmer.visibility = View.VISIBLE
    }

    private fun showRec() {
        recyclerview_product.visibility = View.VISIBLE
    }

    private fun showDialog(title: String) {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog)
        val body = dialog.findViewById(R.id.dialog_text) as TextView
        body.text = title
        dialog.show()
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