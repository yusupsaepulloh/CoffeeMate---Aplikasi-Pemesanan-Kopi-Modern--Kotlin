package com.example.kopiku.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kopiku.Activity.DetailActivity
import com.example.kopiku.Domain.ItemsModel
import com.example.kopiku.databinding.ViewholderPopularBinding
import com.example.project1762.Helper.ManagmentCart

class PopularAdapter(
    private val items: MutableList<ItemsModel>,
    private val context: Context,
    private val managmentCart: ManagmentCart
) : RecyclerView.Adapter<PopularAdapter.Viewholder>() {

    inner class Viewholder(val binding: ViewholderPopularBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder {
        val binding = ViewholderPopularBinding.inflate(LayoutInflater.from(context), parent, false)
        return Viewholder(binding)
    }

    override fun onBindViewHolder(holder: Viewholder, position: Int) {
        val item = items[position]
        holder.binding.titleTxt.text = item.title
        holder.binding.priceTxt.text = "$" + item.price.toString()

        Glide.with(context)
            .load(item.picUrl[0])
            .into(holder.binding.pic)

        // Aksi saat item diklik → buka DetailActivity
        holder.itemView.setOnClickListener {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra("object", item)
            context.startActivity(intent)
        }

        // Aksi saat tombol Add to Cart diklik
        holder.binding.addToCartBtn.setOnClickListener {
            item.numberInCart = 1
            managmentCart.insertItems(item)
        }
    }

    override fun getItemCount(): Int = items.size
}
