package com.example.product.ui.createorder

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.product.adapter.OrderAdapter
import com.example.product.databinding.ActivityCreateOrderBinding
import com.example.product.listense.IClickItemOrder
import com.example.product.model.Variants
import com.example.product.ui.selectvariant.SelectVariantActivity
import com.example.product.utils.GlobalFuntion
import kotlin.math.roundToInt

class CreateOrderActivity : AppCompatActivity() {
    private var listVariant: MutableList<Variants>? = null
    private lateinit var binding: ActivityCreateOrderBinding
    val startForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                val intent = result.data
                listVariant = intent?.getSerializableExtra("list_variant") as? ArrayList<Variants>
                disPlayListOrder()
                showInfoOrder()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)
        goToSelectVariant()
        backPressed()
    }

    fun goToSelectVariant() {
        binding.layoutAddOrder.setOnClickListener {
            var intent = Intent(this, SelectVariantActivity::class.java)
            startForResult.launch(intent)
        }
    }

    fun disPlayListOrder() {
        binding.layoutAddOrder.visibility = View.GONE
        var linearLayoutManager = LinearLayoutManager(this)
        binding.rcvOrder.layoutManager = linearLayoutManager
        var dividerItemDecoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        binding.rcvOrder.addItemDecoration(dividerItemDecoration)
        var adapter = listVariant?.let {
            OrderAdapter(it, object : IClickItemOrder {
                override fun onClickItemOrder(variant: Variants) {
                    variant.total = variant.total!! + 1
                    binding.tvTotal.text =
                        (binding.tvTotal.text.toString().toFloat() + 1).toString()
                    showInfoOrder()
                }

            },this)
        }
        binding.rcvOrder.adapter = adapter

    }

    fun showInfoOrder() {
        var countTax = 0
        var countTotal = 0f
        var countTotalAmount = 0f
        for (i in 0 until listVariant!!.size) {
            countTotal += listVariant!![i].total!!
            for (j in 0 until listVariant!![i].variant_prices.size) {
                if (listVariant!![i].variant_prices[j].price_list.code.equals("BANLE")) {
                    countTax += ((listVariant!![i].output_vat_rate / 100) * (listVariant!![i].variant_prices[j].value * listVariant!![i].total!!)).roundToInt()
                    countTotalAmount += (listVariant!![i].variant_prices[j].value * listVariant!![i].total!!)
                    break
                }
            }
        }
        binding.tvTax.text = countTax.toString()
        binding.tvTotal.text = GlobalFuntion.removeDecimal(countTotal)
        binding.tvTotalAmount.text = GlobalFuntion.removeDecimal(countTotalAmount)
    }

    fun backPressed() {
        binding.imgLeft.setOnClickListener {
            finish()
        }
    }
}
