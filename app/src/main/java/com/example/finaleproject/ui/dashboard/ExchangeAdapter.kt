package com.example.finaleproject.ui.dashboard

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.RecyclerView
import com.example.finaleproject.databinding.ExchangeItemBinding
import com.example.finaleproject.listener.SelectedClickListener

import com.example.finaleproject.model.exchange.CommercialRates
import com.example.finaleproject.model.exchange.Converter
import com.example.finaleproject.model.exchange.OfficialRatesItem
import com.example.finaleproject.util.dollarString
import com.example.finaleproject.util.gellString

class ExchangeAdapter() : RecyclerView.Adapter<ExchangeAdapter.ViewHolder>() {


    var data: List<CommercialRates> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    var double:Double? = null



    var officialData: List<OfficialRatesItem> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExchangeAdapter.ViewHolder {
        return ViewHolder(
            ExchangeItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ExchangeAdapter.ViewHolder, position: Int) {
        holder.bind()
    }
    override fun getItemCount() = data.size


    inner class ViewHolder(private val binding: ExchangeItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private lateinit var currentData: CommercialRates
        fun bind() {
            currentData = data[adapterPosition]
            binding.buyPrice.text = currentData.buy.gellString()
            binding.sellPrice.text = currentData.sell.gellString()
            binding.name.text = currentData.currency
        }
    }
}