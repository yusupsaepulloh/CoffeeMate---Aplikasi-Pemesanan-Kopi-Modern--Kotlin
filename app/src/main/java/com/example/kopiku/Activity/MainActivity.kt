package com.example.kopiku.Activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.kopiku.Adapter.CategoryAdapter
import com.example.kopiku.Adapter.PopularAdapter
import com.example.kopiku.ViewModel.MainViewModel
import com.example.kopiku.databinding.ActivityMainBinding
import com.example.project1762.Helper.ManagmentCart

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private val viewModel=MainViewModel()
    private lateinit var managmentCart: ManagmentCart



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initBanner()
        initCategory()
        initPopular()
        initBottomMenu()
        managmentCart = ManagmentCart(this)
    }

    private fun initBottomMenu() {
        binding.cartBtn.setOnClickListener {
            startActivity(Intent(this,CartActivity::class.java))
        }
    }

    private fun initBanner() {
        binding.progressBarBanner.visibility= View.VISIBLE
        viewModel.loadBanner().observeForever {
            Glide.with(this@MainActivity)
                .load(it[0].url)
                .into(binding.banner)
            binding.progressBarBanner.visibility=View.GONE
        }
        viewModel.loadBanner()
    }

    private fun initCategory() {
        binding.progressBarCategory.visibility = View.VISIBLE
        viewModel.loadCategory().observeForever {
            binding.recyclerViewCat.layoutManager =
                LinearLayoutManager(
                    this@MainActivity, LinearLayoutManager.HORIZONTAL,
                    false
                )
            binding.recyclerViewCat.adapter=CategoryAdapter(it)
            binding.progressBarCategory.visibility=View.GONE
        }
    }

    private fun initPopular() {
        binding.progressBarPopular.visibility = View.VISIBLE
        viewModel.loadPopular().observeForever {
            binding.recyclerViewPolular.layoutManager = GridLayoutManager(this, 2)
            binding.recyclerViewPolular.adapter = PopularAdapter(it.toMutableList(), this, managmentCart)
            binding.progressBarPopular.visibility = View.GONE
        }
    }


}