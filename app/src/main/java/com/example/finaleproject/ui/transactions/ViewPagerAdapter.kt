package com.example.finaleproject.ui.transactions

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.finaleproject.model.transaction.Transaction


class ViewPagerAdapter(fragmentActivity: FragmentManager,lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentActivity, lifecycle) {

//    var fragments: ArrayList<Fragment> = ArrayList()
    var data: List<Fragment> = emptyList()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    override fun getItemCount(): Int {
        return data.size
    }

    override fun createFragment(position: Int): Fragment {
        return data[position]
    }

}
