# StoreApp

An android shopping app built using kotlin,Retrofit,Recyclerview,MVVM Architecture(ViewModel,LiveData,Repository)

# Features

1.fetch the products from given API
2.Display them in a recyclerview list with image,title,category and price
    (product description long text, so displayed in DetailedActivity screen)
3.Search products by its title
4.Sort by price (low to high and high to low)
5.Added the SwipeRefresh for refreshing list
6.Hide/Show progress bar when rising error/network issue
7.Hide/Show text & retry button when rising error/network issue
8.View full details of product when list item  clicking
Note : Add internet permission in AndroidManifest file

## Skills
1.Language : Kotlin
2.Architecture : MVVM Architecture
3.Networking : Retrofit (API Calls)
4.Data(bg): ViewModel, LiveData and Coroutines
5.UI : Recyclerview,Constraint,Linear,Relative layout,SearchView,ProgressBar,SwipeRefresh
6.Image Loading : Glide

## How to run
1.Clone/download the project
2.Open it in Android Studio
3.Let Gradle sync/download all dependencies
4.Run the app on emulator or physical device
## Note
Internet connection is required to load product data from the API and also during the initial Gradle sync to download libraries.


## API
Using: [https://fakestoreapi.com/products]