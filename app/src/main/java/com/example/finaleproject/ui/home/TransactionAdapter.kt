package com.example.finaleproject.ui.home

import android.graphics.Color
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.finaleproject.R
import com.example.finaleproject.databinding.TransactionItemBinding
import com.example.finaleproject.model.transaction.Transaction

class TransactionAdapter:RecyclerView.Adapter<TransactionAdapter.ViewHolder>() {

    private val income = "+ $"
    private val expense = "- $"
    private val INCOME_CATEGORY = "Income"
    private val shopping = "Shopping"
    private val food = "Food"
    private val Transportation = "Transportation"
    private val Subscription = "Subscription"
    private val health = "Health care"
    private val rent = "Rent"
    private val Education = "Education"
    private val Salary = "Salary"
    private val gift = "Gift"
    private val Passive_Incom = "Passive income"


    var data: List<Transaction> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionAdapter.ViewHolder {
        return ViewHolder(
            TransactionItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onBindViewHolder(holder: TransactionAdapter.ViewHolder, position: Int) {
        holder.bind()
    }
    private val limit = 10


    override fun getItemCount(): Int {
        return if (data.size > limit) {
            limit
        } else {
            data.size
        }
    }


    inner class ViewHolder(private val binding: TransactionItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private lateinit var currentData: Transaction
        @RequiresApi(Build.VERSION_CODES.M)
        fun bind() {
            currentData = data[adapterPosition]
            setImages(binding.coinsItemImageView,currentData.transaction_Category!!)
            binding.coinsItemDescriptionTXTView.text = currentData.description
            binding.transactionItemNameTextView.text = currentData.transaction_Category
            setAmount(currentData.amount!!, currentData.category!!,binding.coinItemPriceTextView)
            binding.ItemTimeTextView.text = currentData.time

        }
    }
    @RequiresApi(Build.VERSION_CODES.M)
    fun setImages(imageButton: ImageView, category:String){
        with(imageButton){
            if (category == shopping){
                setImageResource(R.drawable.ic_shopping_bag_svgrepo_com)
                setBackgroundColor(imageButton.context.getColor(R.color.transaction_yellow))
            }else if (category == Education){
                setImageResource(R.drawable.ic_education_svgrepo_com)
                setBackgroundColor(imageButton.context.getColor(R.color.white))
            }else if (category == rent){
                setImageResource(R.drawable.ic_real_estate_rent_svgrepo_com)
                setBackgroundColor(imageButton.context.getColor(R.color.white))
            }else if (category == health){
                setImageResource(R.drawable.ic_health_care_add_svgrepo_com)
                setBackgroundColor(imageButton.context.getColor(R.color.transaction_health_red))
            }else if (category == gift){
                setImageResource(R.drawable.ic_gift_svgrepo_com)
                setBackgroundColor(imageButton.context.getColor(R.color.transaction_gift_pink))
            }else if (category == food){
                setImageResource(R.drawable.ic_fork_and_spoon_silhouettes_of_the_tools_to_eat_svgrepo_com)
                setBackgroundColor(imageButton.context.getColor(R.color.transaction_red))
            }else if (category == Salary){
                setImageResource(R.drawable.ic_money_bag_svgrepo_com__3_)
                setBackgroundColor(imageButton.context.getColor(R.color.transaction_green))
            }else if (category == Passive_Incom){
                setImageResource(R.drawable.ic_bag_svgrepo_com)
                setBackgroundColor(imageButton.context.getColor(R.color.white))
            }else if (category == Transportation){
                setImageResource(R.drawable.ic_car_svgrepo_com__1_)
                setBackgroundColor(imageButton.context.getColor(R.color.transaction_blue))
            }else if (category == Subscription){
                setImageResource(R.drawable.ic_note_svgrepo_com)
                setBackgroundColor(imageButton.context.getColor(R.color.transaction_gray))
            }
        }
    }
    fun setAmount(double: Double, string: String, textView: TextView){
        if (string == INCOME_CATEGORY){
            textView.setTextColor(Color.parseColor("#45FF00"))
            textView.text = income.plus(double.toString())
        }else{
            textView.setTextColor(Color.parseColor("#FF0000"))
            textView.text = expense.plus(double.toString())
        }
    }
}