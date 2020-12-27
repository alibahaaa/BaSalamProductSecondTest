package com.basalam.basalamproduct

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.basalam.basalamproduct.db.ProductDatabase
import com.basalam.basalamproduct.repository.ProductRepository
import com.basalam.basalamproduct.viewmodel.ProductViewModel
import com.basalam.basalamproduct.viewmodel.ProductViewModelProviderFactory



/*
*********
* here we have our main activity
* in this project we use MVVM , Retrofit , Room , Navigation , ViewModel , LiveData , DataBinding
*
 */



class MainActivity : AppCompatActivity() {

    lateinit var viewModel: ProductViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val repository = ProductRepository(ProductDatabase(this))
        val viewModelProviderFactory = ProductViewModelProviderFactory(repository)
        viewModel = ViewModelProvider(this, viewModelProviderFactory).get(
            ProductViewModel::class.java
        )

    }
}