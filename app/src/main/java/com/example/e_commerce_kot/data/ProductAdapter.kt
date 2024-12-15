package com.example.e_commerce_kot.data

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.e_commerce_kot.R
import com.example.e_commerce_kot.cart_manager.CartManager
import com.google.android.material.button.MaterialButton

class ProductAdapter(
    private val productList: List<Product>
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    var onItemClicked : ((Product) -> Unit) ? = null

    class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val productImage: ImageView = itemView.findViewById(R.id.imageViewProduct)
        val productName: TextView = itemView.findViewById(R.id.textViewName)
        val productDescription: TextView = itemView.findViewById(R.id.textViewDescription)
        val productPrice : TextView = itemView.findViewById(R.id.textViewPrice)
        val likeButton: ImageButton = itemView.findViewById(R.id.btnLike)




    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.product_item_layout, parent, false)

        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = productList[position]

        // Bind data to views
        holder.productImage.setImageResource(product.image)
        holder.productName.text = product.name
        holder.productDescription.text = product.description
        holder.productPrice.text = product.price;




        // Heart button logic
        holder.likeButton.setImageResource(
            if (product.isLiked) R.drawable.baseline_favorite_border_24
            else R.drawable.baseline_favorite_border_24
        )

//        holder.addToCartButton.setOnClickListener {
//            CartManager.addToCart(product)
//            Toast.makeText(holder.addToCartButton.context, "Added to Cart", Toast.LENGTH_LONG).show()
//        }

        holder.likeButton.setOnClickListener {
            product.isLiked = !product.isLiked
            notifyItemChanged(position)
        }
        holder.itemView.setOnClickListener(){
            onItemClicked?.invoke(product)
        }
    }

    override fun getItemCount() = productList.size
}
