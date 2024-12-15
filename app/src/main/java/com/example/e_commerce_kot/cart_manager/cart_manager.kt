package com.example.e_commerce_kot.cart_manager

import com.example.e_commerce_kot.data.Product

object CartManager {
    private val cartItems = mutableListOf<Product>()

    // Add a product to the cart or increase its quantity if it already exists
    fun addToCart(product: Product) {
        val existingProduct = cartItems.find { it.productId == product.productId }
        if (existingProduct != null) {
            existingProduct.quantity += 1
            println("Updated quantity for ${existingProduct.productId} to ${existingProduct.quantity}")
        } else {
            cartItems.add(product)
            println("Added new product ${product.name} to cart")
        }
    }

    // Remove a product entirely from the cart
    fun deleteProduct(productId: Int) {
        val product = cartItems.find { it.productId == productId }
        if (product != null) {
            cartItems.remove(product)
            println("Removed product ${product.name} from the cart")
        } else {
            println("Product with ID $productId not found in the cart")
        }
    }

    // Increase the quantity of a specific product
    fun increaseQuantity(productId: Int) {
        val product = cartItems.find { it.productId == productId }
        if (product != null) {
            product.quantity += 1
            println("Increased quantity for ${product.name} to ${product.quantity}")
        } else {
            println("Product with ID $productId not found")
        }
    }

    // Decrease the quantity of a specific product, remove it if quantity becomes 1
    fun decreaseQuantity(productId: Int) {
        val product = cartItems.find { it.productId == productId }
        if (product != null) {
            if (product.quantity > 1) {
                product.quantity -= 1
                println("Decreased quantity for ${product.name} to ${product.quantity}")
            } else {
                // Remove product if quantity is 1
                cartItems.remove(product)
                println("Removed ${product.name} from the cart as quantity reached 0")
            }
        } else {
            println("Product with ID $productId not found")
        }
    }

    // Retrieve all cart items
    fun getCartItems(): List<Product> {
        return cartItems
    }

    // Clear all items in the cart
    fun clearCart() {
        cartItems.clear()
        println("Cart cleared successfully")
    }
}
