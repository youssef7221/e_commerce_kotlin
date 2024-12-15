package com.example.e_commerce_kot

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.e_commerce_kot.cart_manager.CartManager
import com.example.e_commerce_kot.data.CartAdapter

class Cart : Fragment() {

    // UI Elements
    private lateinit var cartRecyclerView: RecyclerView
    private lateinit var emptyCartLayout: LinearLayout
    private lateinit var cartAdapter: CartAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_cart, container, false)

        // Initialize UI elements
        emptyCartLayout = view.findViewById(R.id.emptyCartLayout)
        cartRecyclerView = view.findViewById(R.id.cartRecyclerView)

        // Initialize RecyclerView and Adapter
        setupRecyclerView()

        // Toggle between empty cart layout and RecyclerView
        toggleCartView()

        return view
    }

    private fun setupRecyclerView() {
        cartRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Pass the callbacks for increase, decrease, and delete operations
        cartAdapter = CartAdapter(
            CartManager.getCartItems().toMutableList(),
            onIncreaseQuantity = { productId -> handleIncreaseQuantity(productId) },
            onDecreaseQuantity = { productId -> handleDecreaseQuantity(productId) },
            onDeleteItem = { productId -> handleDeleteItem(productId) }
        )

        cartRecyclerView.adapter = cartAdapter
    }

    private fun handleIncreaseQuantity(productId: Int) {
        // Increase product quantity
        CartManager.increaseQuantity(productId)
        cartAdapter.updateCartItems(CartManager.getCartItems())
        toggleCartView()
    }

    private fun handleDecreaseQuantity(productId: Int) {
        // Decrease product quantity
        CartManager.decreaseQuantity(productId)

        // Refresh adapter data
        cartAdapter.updateCartItems(CartManager.getCartItems())
        toggleCartView()
    }

    private fun handleDeleteItem(productId: Int) {
        // Delete product from cart
        CartManager.deleteProduct(productId)

        // Refresh adapter data
        cartAdapter.updateCartItems(CartManager.getCartItems())
        toggleCartView()
    }

    private fun toggleCartView() {
        if (CartManager.getCartItems().isEmpty()) {
            // Show empty cart layout
            emptyCartLayout.visibility = View.VISIBLE
            cartRecyclerView.visibility = View.GONE
        } else {
            // Show RecyclerView
            emptyCartLayout.visibility = View.GONE
            cartRecyclerView.visibility = View.VISIBLE
        }
    }
}
