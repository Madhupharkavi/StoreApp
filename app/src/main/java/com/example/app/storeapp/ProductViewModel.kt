package com.example.app.storeapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class ProductViewModel : ViewModel() {

    private val repository = ProductRepository()

    private var originalList = listOf<Product>()

    // holds the all list of products from API
    private val _allProductList = MutableLiveData<List<Product>>()
    val allProductList: LiveData<List<Product>> = _allProductList

    // for load the progress dialogue
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    //for showing error message
    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    // fetch the all list of products from API
    fun fetchProducts() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = repository.getAllProducts()
                if (response.isSuccessful && response.body() != null) {
                    originalList = response.body()!!
                    _allProductList.value = response.body()
                    _errorMessage.value = null
                } else {
                    _errorMessage.value = "Failed to fetch the products list"
                }
            } catch (e: Exception) {
                if (e is java.net.UnknownHostException){
                    _errorMessage.value = "No internet connection"
                }else{
                    _errorMessage.value = "Error : ${e.message}"
                }
            } finally {
                _isLoading.value = false
            }
        }
    }

    //search the product list by title
    fun searchFilter(title:String){
        viewModelScope.launch {
        val filtered = if(title.isBlank()) originalList
        else{
            originalList.filter {
                it.title.contains(title, ignoreCase = true)
            }
        }
        _allProductList.value = filtered
        }
    }

    //sort the list of product in ascending by price
    fun sortByPriceAsc(){
        _allProductList.value = _allProductList.value?.sortedBy { it.price }
    }
    //sort the list of product in descending by price
    fun sortByPriceDesc(){
        _allProductList.value = _allProductList.value?.sortedByDescending {  it.price }
    }
}