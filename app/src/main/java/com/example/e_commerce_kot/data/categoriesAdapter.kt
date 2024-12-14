package com.example.e_commerce_kot.data

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.e_commerce_kot.R

class categoriesAdapter(private val categoriesList : List<categories>) : RecyclerView.Adapter<categoriesAdapter.categoriesViewHolder>() {

    class categoriesViewHolder (itemView : View) : RecyclerView.ViewHolder(itemView){
        val categoryImage : ImageView = itemView.findViewById(R.id.imageView)
        val categoryName : TextView = itemView.findViewById(R.id.textView2)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): categoriesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_categoriea_layout,parent,false)
        return categoriesViewHolder(view)
    }

    override fun getItemCount(): Int {
        return categoriesList.size
    }

    override fun onBindViewHolder(holder: categoriesViewHolder, position: Int) {
        val categories = categoriesList[position]
        holder.categoryImage.setImageResource(categories.categoryImage)
        holder.categoryName.text = categories.name
    }
}