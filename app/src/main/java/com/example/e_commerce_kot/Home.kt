package com.example.e_commerce_kot

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.example.e_commerce_kot.data.ImageAdapter
import com.example.e_commerce_kot.data.Product
import com.example.e_commerce_kot.data.ProductAdapter
import com.example.e_commerce_kot.data.categories
import com.example.e_commerce_kot.data.categoriesAdapter
import kotlin.math.abs

class Home : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var categoriesList: ArrayList<categories>
    private lateinit var categoriesAdapter: categoriesAdapter
    private lateinit var viewPager2: ViewPager2
    private lateinit var handler : Handler
    private lateinit var imageList : ArrayList<Int>
    private lateinit var imageAdapter: ImageAdapter
    private lateinit var productsRecycleViewer: RecyclerView
    private lateinit var productAdapter: ProductAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout and return the root view
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        // Initialize views and set up RecyclerView
        init(view)

        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                handler.removeCallbacks(runnable)
                handler.postDelayed(runnable , 2000)
            }
        })

        return view


    }

    override fun onPause() {
        super.onPause()

        handler.removeCallbacks(runnable)
    }

    override fun onResume() {
        super.onResume()

        handler.postDelayed(runnable , 2000)
    }

    private val runnable = Runnable {
        viewPager2.currentItem += 1
    }

    private fun setUpTransformer(){
        val transformer = CompositePageTransformer()
        transformer.addTransformer(MarginPageTransformer(40))
        transformer.addTransformer { page, position ->
            val r = 1 - abs(position)
            page.scaleY = 0.85f + r * 0.14f
        }

        viewPager2.setPageTransformer(transformer)
    }


    private fun init(view: View) {
        // Initialize RecyclerView
        recyclerView = view.findViewById(R.id.recycle_view)
        recyclerView.setHasFixedSize(true)
        recyclerView.setItemViewCacheSize(20)
        recyclerView.setItemAnimator(null)
        val snapHelper: SnapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(recyclerView)

        // Set layout manager
        recyclerView.layoutManager = LinearLayoutManager(
            requireContext(),
            RecyclerView.HORIZONTAL,
            false
        )

        // Initialize data list
        categoriesList = ArrayList()
        addDataToList()

        // Set up adapter
        categoriesAdapter = categoriesAdapter(categoriesList)
        recyclerView.adapter = categoriesAdapter

        // Initialize ViewPager2
        viewPager2 = view.findViewById(R.id.viewPager2)  // Corrected Line
        handler = Handler(Looper.myLooper()!!)
        imageList = ArrayList()
        imageList.add(R.drawable.latest1)
        imageList.add(R.drawable.baseline_shopping_cart_24)
        imageList.add(R.drawable.baseline_shopping_cart_24)
        imageList.add(R.drawable.baseline_shopping_cart_24)
        imageAdapter = ImageAdapter(imageList, viewPager2)

        viewPager2.adapter = imageAdapter
        viewPager2.offscreenPageLimit = 3
        viewPager2.clipToPadding = false
        viewPager2.clipChildren = false
        viewPager2.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER

        // products

        // Product data list
        val productList = listOf(
            Product("Nike Air Jordan", "Retro fashion shoes", "126.0 $", false, R.drawable.product1),
            Product("Classic Glasses", "Stylish new glasses", "8.5 $", false, R.drawable.quickmart),
            Product("Smart Watch", "Latest technology", "59.99 $", false, R.drawable.product2),
            Product("Wireless Headphones", "High-quality sound", "32.99 $", false, R.drawable.product3)
        )

// Initialize RecyclerView for products
        productsRecycleViewer = view.findViewById(R.id.gridRecyclerView)
        productsRecycleViewer.layoutManager = GridLayoutManager(requireContext(), 2)

// Initialize ProductAdapter
        productAdapter = ProductAdapter(productList)

// Set the adapter to the RecyclerView
        productsRecycleViewer.adapter = productAdapter


        setUpTransformer()
    }


    private fun addDataToList() {
        // Add dummy data to the categories list
        categoriesList.add(categories(categoryImage = R.drawable.fashoin, name = "Fashion"))
        categoriesList.add(categories(categoryImage = R.drawable.product2, name = "Electronics"))
        categoriesList.add(categories(categoryImage = R.drawable.quickmart, name = "Industrial"))
        categoriesList.add(categories(categoryImage = R.drawable.parcel, name = "Parcel"))
        categoriesList.add(categories(categoryImage = R.drawable.grocery, name = "Grocery"))
//        categoriesList.add(categories(categoryImage = R.drawable.industrial, name = "Toys"))
//        categoriesList.add(categories(categoryImage = R.drawable.furniture, name = "Home Decor"))
    }
}