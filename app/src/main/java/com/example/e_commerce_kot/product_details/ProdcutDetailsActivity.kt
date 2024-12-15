package com.example.e_commerce_kot.product_details

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.e_commerce_kot.R
import com.example.e_commerce_kot.cart_manager.CartManager
import com.example.e_commerce_kot.data.Product
import com.google.android.material.button.MaterialButton

class ProdcutDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set the content view to the XML layout
        setContentView(R.layout.product_details_layout)

        // Handle intent extras if needed (e.g., product details passed from previous screen)
        val productTitle = intent.getStringExtra("productTitle")
        val productId = intent.getIntExtra("productId",0)
        val productDescription = intent.getStringExtra("productDescription")
        val productPrice = intent.getStringExtra("productPrice")
        val productImageResId = intent.getIntExtra("productImageResId", 0)
        val addToCartButton = findViewById<MaterialButton>(R.id.addToCartButton)

        addToCartButton.setOnClickListener {
            // Add product to the cart
            val cartItem = Product(
                quantity = 1,
                productId = productId,
                isLiked = false,
                name = productTitle ?: "No title",
                description = productDescription ?: "No description",
                price = productPrice ?: "0",
                image = productImageResId
            )

            CartManager.addToCart(cartItem)

            // Show confirmation message
            Toast.makeText(this, "${cartItem.name} added to cart!", Toast.LENGTH_SHORT).show()
        }
        // Update UI with the passed product data
        findViewById<TextView>(R.id.productTitle).text = productTitle
        findViewById<TextView>(R.id.productDescription).text = productDescription
        findViewById<TextView>(R.id.productPrice).text = productPrice
        findViewById<ImageView>(R.id.imageView3).setImageResource(productImageResId)
    }
}
