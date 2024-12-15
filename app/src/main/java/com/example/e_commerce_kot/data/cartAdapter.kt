package com.example.e_commerce_kot.data

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.e_commerce_kot.R

class CartAdapter(
    private var cartItems: MutableList<Product>, // Ensure cartItems is mutable for updates
    private val onIncreaseQuantity: (Int) -> Unit,
    private val onDecreaseQuantity: (Int) -> Unit,
    private val onDeleteItem: (Int) -> Unit
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    inner class CartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val productImage: ImageView = itemView.findViewById(R.id.cartImage)
        val productName: TextView = itemView.findViewById(R.id.cartProductName)
        val productPrice: TextView = itemView.findViewById(R.id.cartProductPrice)
        val productQuantity: TextView = itemView.findViewById(R.id.quantityText)
        val increaseButton: ImageButton = itemView.findViewById(R.id.buttonIncrease)
        val decreaseButton: ImageButton = itemView.findViewById(R.id.buttonDecrease)
        val deleteButton: ImageButton = itemView.findViewById(R.id.deleteButton)

        fun bind(product: Product) {
            productImage.setImageResource(product.image)
            productName.text = product.name
            productPrice.text = product.price
            productQuantity.text = product.quantity.toString()

            // Set up button click listeners
            increaseButton.setOnClickListener { onIncreaseQuantity(product.productId) }
            decreaseButton.setOnClickListener { onDecreaseQuantity(product.productId) }
            deleteButton.setOnClickListener { onDeleteItem(product.productId) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.cart_item_layoout, parent, false)
        return CartViewHolder(view)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val product = cartItems[position]
        holder.bind(product)
    }

    override fun getItemCount(): Int = cartItems.size

    // Function to update the cart items after a change
    fun updateCartItems(newCartItems: List<Product>) {
        cartItems = newCartItems.toMutableList()
        notifyDataSetChanged() // Refresh the entire list
    }

    // Function to notify an item changed after quantity is updated
    fun notifyItemUpdated(productId: Int) {
        val index = cartItems.indexOfFirst { it.productId == productId }
        if (index != -1) {
            notifyItemChanged(index)
        }
    }

    // Function to notify an item removed after deletion
    fun notifyItemDeleted(productId: Int) {
        val index = cartItems.indexOfFirst { it.productId == productId }
        if (index != -1) {
            cartItems.removeAt(index)
            notifyItemRemoved(index)
        }
    }
}
