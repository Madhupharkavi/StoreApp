package com.example.app.storeapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.app.storeapp.databinding.ItemListBinding

class ProductAdapter(
    private var productList: List<Product>,
    private val onItemClick : (Product) -> Unit
) : RecyclerView.Adapter<ProductAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
//        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list,parent,false)
        val view = ItemListBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bindAllViews(productList[position])
    }

   inner class MyViewHolder(private val binding: ItemListBinding) : RecyclerView.ViewHolder(binding.root){

        fun bindAllViews(product: Product){
            binding.titleText.text = product.title
            binding.categoryText.text = product.category
            binding.priceText.text = "$${product.price}"
            Glide.with(itemView.context)
                .load(product.image)
                .into(binding.imageList)
            binding.root.setOnClickListener {
                onItemClick(product)
            }
        }
    }

    fun updateList(newList : List<Product>){
        productList = newList
        notifyDataSetChanged()
    }

}
