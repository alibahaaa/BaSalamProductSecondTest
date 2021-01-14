package com.basalam.basalamproduct.ui

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.basalam.basalamproduct.AppController
import com.basalam.basalamproduct.R
import com.basalam.basalamproduct.di.AppComponent
import com.basalam.basalamproduct.util.Resource
import com.basalam.basalamproduct.viewmodel.ProductViewModel
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var viewModel: ProductViewModel
    private lateinit var appComponent: AppComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        appComponent = (application as AppController).getComponent()
        appComponent.inject(this)

        println("log ${appComponent.getRepository()}")

        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(ProductViewModel::class.java)

        setUpShimmerRecyclerView()
        subscribeObservers()
    }

    private fun subscribeObservers() {
        viewModel.productData.observe(this, Observer { productResponse ->
            when (productResponse) {
                is Resource.Success -> {
                    println("Success log")
                    setUpRecyclerView()
                    hideShimmer()
                    productResponse.data?.observe(this, Observer { productList ->
                        appComponent.getAdapter().differ.submitList(productList)
                    })
                }

                is Resource.Error.Internal -> {
                    println("Internal log ${productResponse.message!!}")
                    hideShimmer()
                    showDialog("خطا : ${productResponse.message!!}")
                }

                is Resource.Error.Validator -> {
                    println("Validator log ${productResponse.message!!}")
                    hideShimmer()
                    showDialog("خطا : ${productResponse.message!!}")
                }

                is Resource.Error.UnKnownError -> {
                    println("UnknownError log ${productResponse.message!!}")
                    hideShimmer()
                    showDialog("خطا : ${productResponse.message!!}")
                }

                is Resource.Empty -> {
                    hideShimmer()
                    showDialog("ایتمی برای نمایش وجود ندارد")
                    println("empty log")
                }

                is Resource.Loading -> {
                    println("Loading log")
                    showShimmer()
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

    private fun hideRec() {
        recyclerview_product.visibility = View.GONE
    }

    private fun showDialog(title: String) {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog)
        val body = dialog.findViewById(R.id.dialog_text) as TextView
        body.text = title
        val button = dialog.findViewById(R.id.dialog_button) as Button
        button.setOnClickListener {
            viewModel.getData()
            dialog.dismiss()
        }
        dialog.show()
    }

    private fun setUpRecyclerView() {
        recyclerview_product.apply {
            adapter = appComponent.getAdapter()
            layoutManager = GridLayoutManager(context, 2)
        }
    }

    private fun setUpShimmerRecyclerView() {
        recyclerview_shimmer.apply {
            adapter = appComponent.getShimmerAdapter()
            layoutManager = GridLayoutManager(context, 2)
        }
    }
}