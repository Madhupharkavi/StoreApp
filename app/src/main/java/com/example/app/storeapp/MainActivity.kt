package com.example.app.storeapp

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.app.storeapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var adapter: ProductAdapter
    private lateinit var viewModel: ProductViewModel
    private var fullProductList: List<Product> = listOf<Product>()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //set the toolbar

        val toolbar = findViewById<Toolbar>(R.id.toolbar_main)
        setSupportActionBar(toolbar)

        //pass the parameter inside adapter
        adapter = ProductAdapter(emptyList()) { it ->
            val intent = Intent(this, DetailedActivity::class.java)
            intent.putExtra("product", it)
            startActivity(intent)
        }
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        viewModel = ViewModelProvider(this)[ProductViewModel::class.java]

        viewModel.fetchProducts() // fetch the product list from api through viewmodel

        viewModel.allProductList.observe(this) {
            it     //getting response from api
            fullProductList = it
            adapter.updateList(it)      //updated the list
            if (it.isEmpty()) {
                Toast.makeText(this@MainActivity, "No Data Found...", Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.errorMessage.observe(this) { msg ->       //show error message
            if (msg != null) {
                Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
                binding.errorText.text = msg
                binding.errorText.visibility = View.VISIBLE
                binding.errorText1.visibility = View.VISIBLE
                binding.btnRetry.visibility = View.VISIBLE
            } else {
                binding.errorText.visibility = View.GONE
                binding.btnRetry.visibility = View.GONE
                binding.errorText1.visibility = View.GONE
            }
        }

        binding.btnRetry.setOnClickListener {       //retry when list not loading/network issue
            viewModel.fetchProducts()
        }

        binding.swipeRefreshLayout.setOnRefreshListener {       //refresh the layout
            viewModel.fetchProducts()
        }
        viewModel.isLoading.observe(this) { isLoading ->
            val isUserRefreshing = binding.swipeRefreshLayout.isRefreshing
            // Only show ProgressBar during app launch&retry, if not triggered by swipe-to-refresh
            binding.progressBar.visibility = if (isLoading && !isUserRefreshing) View.VISIBLE else View.GONE
            // Update swipe refresh state
            binding.swipeRefreshLayout.isRefreshing = isLoading
        }

        val increase = binding.customToolbar.increasePrice
        val decrease = binding.customToolbar.decreasePrice

        increase.setOnClickListener {
            viewModel.sortByPriceAsc()      //sort the list low to high by price
            Toast.makeText(this, "Sorted by price : Low to High", Toast.LENGTH_SHORT).show()
        }
        decrease.setOnClickListener {
            viewModel.sortByPriceDesc()     //sort the list high to low by price
            Toast.makeText(this, "Sorted by price : High to Low", Toast.LENGTH_SHORT).show()
        }

        //search view  ui arrangement
        val searchView = binding.customToolbar.searchView
        searchView.isIconified = false
        searchView.clearFocus()
        searchView.requestFocusFromTouch()
        searchView.queryHint = "Search here..."
        val searchIcon = searchView.findViewById<ImageView>(androidx.appcompat.R.id.search_mag_icon)
        searchIcon?.layoutParams?.width = 0
        searchIcon?.visibility = View.GONE

        //edittext of search view
        val editText = searchView.findViewById<EditText>(androidx.appcompat.R.id.search_src_text)
        editText.setTextColor(Color.BLACK)
        editText.setHint("Search here...")
        editText.setHintTextColor(Color.GRAY)
        editText.textSize = 16f
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.searchFilter(newText ?: "")       //filter text by typing
                return true
            }
        })
    }

}