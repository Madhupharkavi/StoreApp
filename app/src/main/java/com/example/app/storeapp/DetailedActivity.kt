package com.example.app.storeapp

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.app.storeapp.databinding.ActivityDetailedBinding
import com.example.app.storeapp.databinding.ItemListBinding

class DetailedActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailedBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailedBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)       //enable back arrow
//        toolbar.setNavigationIcon(R.drawable.arrow_back)


        // set the data from list activity for detailed view
        val product = intent.getParcelableExtra<Product>("product")
        binding.detailTitle.text = product?.title
        binding.detailPrice.text = "$${product?.price}"
        binding.detailRating.text = "${product?.rating?.rate} Rating"
        binding.detailCount.text = "${product?.rating?.count} Reviews"
        binding.detailDescription.text = product?.description
        Glide.with(this).load(product?.image).into(binding.productDetailImage)
    }
    //handle back arrow
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressedDispatcher.onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    //for toolbar icon, only show UI
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }


}