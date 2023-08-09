package com.example.product

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.product.utils.GlobalFuntion
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
        mActivityMain.relativeListProduct.setOnClickListener{
            GlobalFuntion.startActivity(this,ListProductActivity::class.java)
        }
    }
    fun goToCreateOrder(){
        mActivityMain.layoutAddOrder.setOnClickListener{
            GlobalFuntion.startActivity(this,CreateOrderActivity::class.java)
        }
    }
}