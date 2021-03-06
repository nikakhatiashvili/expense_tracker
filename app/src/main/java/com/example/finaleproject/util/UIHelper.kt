package com.example.finaleproject.util

import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.finaleproject.R

object UIHelper {

    fun checkLogin(email:String,password:String):Boolean{
        return !(email.isEmpty() || password.isEmpty())
    }

    fun checkStrings(name:String,pass:String,email:String):Boolean{
        if(name.isNullOrEmpty() || pass.isNullOrEmpty() || email.isNullOrEmpty()){
            return false
        }
        return true
    }

    fun showChangePercent(textView: TextView, _change: Double?) {
        val changePercent = "%.2f %%".format(_change).trimParanthesis()
        textView.text = changePercent
        val context = textView.context
        if (changePercent.contains("-")) {
            textView.setTextColor(
                ContextCompat.getColor(context, R.color.red)
            )
            textView.setCompoundDrawablesWithIntrinsicBounds(
                null, null,
                ContextCompat.getDrawable(context, R.drawable.ic_baseline_arrow_downward),
                null
            )
        } else {
            textView.setTextColor(
                ContextCompat.getColor(context, R.color.green)
            )
            textView.setCompoundDrawablesWithIntrinsicBounds(
                null, null,
                ContextCompat.getDrawable(context, R.drawable.ic_baseline_arrow_upward),
                null
            )
        }
    }


}