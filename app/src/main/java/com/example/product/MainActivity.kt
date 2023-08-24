package com.example.product

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.product.databinding.ActivityMainBinding
import com.example.product.ui.createorder.CreateOrderActivity
import com.example.product.ui.listproduct.ListProductActivity

class MainActivity : AppCompatActivity() {
    private lateinit var mActivityMain : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivityMain = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mActivityMain.root)
        goToListProduct()
        goToCreateOrder()

    }
    fun goToListProduct(){
        mActivityMain.rlMainListProduct.setOnClickListener{
            val intent =Intent(this,ListProductActivity::class.java)
            startActivity(intent)
        }
    }
    fun goToCreateOrder(){
        mActivityMain.llMainAddOrder.setOnClickListener{
            val intent =Intent(this,CreateOrderActivity::class.java)
            startActivity(intent)
        }
    }
}