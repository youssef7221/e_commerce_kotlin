package com.example.e_commerce_kot.data

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.e_commerce_kot.R

class ProductAdapter(
    private val productList: List<Product>
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

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
        holder.productPrice.text = product.price.toString();

        // Heart button logic
        holder.likeButton.setImageResource(
            if (product.isLiked) R.drawable.baseline_favorite_border_24
            else R.drawable.baseline_favorite_border_24
        )

        holder.likeButton.setOnClickListener {
            product.isLiked = !product.isLiked
            notifyItemChanged(position)
        }
    }

    override fun getItemCount() = productList.size
}
