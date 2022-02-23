package com.example.finaleproject.ui.notifications

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.finaleproject.databinding.CryptoItemBinding
import com.example.finaleproject.listener.ClickListener
import com.example.finaleproject.model.CryptoItem
import com.example.finaleproject.util.UIHelper
import com.example.finaleproject.util.dollarString
import com.example.finaleproject.util.smalldollarString

class CryptoAdapter(val listener:ClickListener):RecyclerView.Adapter<CryptoAdapter.ViewHolder>() {

    var data: List<CryptoItem> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CryptoAdapter.ViewHolder {
        return ViewHolder(
            CryptoItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CryptoAdapter.ViewHolder, position: Int) {
        holder as ViewHolder
        holder.bind()
    }

    override fun getItemCount()= data.size

    inner class ViewHolder(private val binding: CryptoItemBinding):RecyclerView.ViewHolder(binding.root),
        View.OnClickListener{
        private lateinit var currentData:CryptoItem
        @SuppressLint("ResourceAsColor")
        fun bind(){
            binding.root.setOnClickListener(this)
            currentData = data[adapterPosition]
            Glide.with(itemView.context).load(currentData.image).into(binding.coinsItemImageView)

            binding.coinsItemSymbolTextView.text = currentData.symbol
            if(currentData.current_price.toString().first().toString() == "0"){
                binding.coinsItemPriceTextView.text = currentData.current_price.smalldollarString()
            }else{
                binding.coinsItemPriceTextView.text = currentData.current_price.dollarString()
            }

            binding.coinsItemNameTextView.text = currentData.name

            UIHelper.showChangePercent(binding.coinsItemChangeTextView,currentData.price_change_percentage_24h)
        }

        override fun onClick(p0: View?) {
           listener.onClick(currentData)
        }
    }
}