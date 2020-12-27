package com.basalam.basalamproduct.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.basalam.basalamproduct.MainActivity
import com.basalam.basalamproduct.R
import com.basalam.basalamproduct.adapters.ProductAdapter
import com.basalam.basalamproduct.adapters.ShimmerAdapter
import com.basalam.basalamproduct.db.ProductDatabase
import com.basalam.basalamproduct.repository.ProductRepository
import com.basalam.basalamproduct.util.Resource
import com.basalam.basalamproduct.viewmodel.ProductViewModel
import com.basalam.basalamproduct.viewmodel.ProductViewModelProviderFactory
import kotlinx.android.synthetic.main.fragment_product.*
import javax.xml.transform.Source

class ProductFragment : Fragment(R.layout.fragment_product) {

    //defining our view model and adapters
    lateinit var viewModel: ProductViewModel
    lateinit var productAdapter: ProductAdapter
    lateinit var shimmerAdapter: ShimmerAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // I don't use data binding for recycler view in fragment
        // just for recyclerView Items for Product model


        //viewModel that we Defined in activity main
        viewModel = (activity as MainActivity).viewModel


        setUpShimmerRecyclerView()
        setUpRecyclerView()

        viewModel.products.observe(viewLifecycleOwner, Observer {response->
            when(response){
                // Success which mean every thing works great , wow !
                is Resource.Success ->{
                    hideProgressBar()
                    // lets get data and put it in adapter
                    response.data?.let {productResponse ->
                        productAdapter.differ.submitList(productResponse.data.productSearch.products)
                    }
                }
                // Error which means we have some problem
                is Resource.Error->{
                    hideProgressBar()
                    response.message?.let {message->
                        Log.e("ERROR" , message)
                    }
                }
                // Loading which means its loading the data (Product list) that can work well or fail !
                is Resource.Loading ->{
                    showProgressBar()
                    Log.e("Loading" , "Loading")
                }
            }
        })

    }

    //hide progress bar
    private fun hideProgressBar() {
        recyclerview_shimmer.visibility = View.GONE
    }
    //show progress bar
    private fun showProgressBar() {
        recyclerview_shimmer.visibility = View.VISIBLE

    }


    // set up product recycler view
    private fun setUpRecyclerView(){
        productAdapter = ProductAdapter()
        recyclerview_product.apply {
            adapter = productAdapter
            layoutManager = GridLayoutManager(activity,2)
        }

    }
    // set up shimmer recycler view
    private fun setUpShimmerRecyclerView(){
        shimmerAdapter = ShimmerAdapter()
        recyclerview_shimmer.apply {
            adapter = shimmerAdapter
            layoutManager = GridLayoutManager(activity,2)
        }

    }

}