package com.profplay.financialinvestmentresources.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.util.ArrayMap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.profplay.financialinvestmentresources.R
import com.profplay.financialinvestmentresources.databinding.RowLayoutBinding
import com.profplay.financialinvestmentresources.model.CurrencyModel

class RecyclerViewAdapter (private val currencyList : ArrayMap<String, CurrencyModel >) : RecyclerView.Adapter<RecyclerViewAdapter.RowHolder>() {

    class RowHolder(val binding: RowLayoutBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowHolder {
        val binding = RowLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return RowHolder(binding)
    }

    override fun getItemCount(): Int {
        return currencyList.size
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: RowHolder, position: Int) {
        currencyList.keyAt(position).let {
            holder.binding.currencyName.text = it
        }
        currencyList.valueAt(position).let {
            holder.binding.alis.text = it.alis
            holder.binding.satis.text = it.satis
            holder.binding.dOran.text = it.d_oran
            holder.binding.degisim.text = it.degisim

            when (it.d_yon) {
                "caret-down" -> {
                    holder.binding.dYon.setBackgroundResource(R.drawable.caret_down)
                    holder.binding.rowParent.setBackgroundColor(Color.parseColor("#CC0000"))

                }

                "caret-up" -> {
                    holder.binding.dYon.setBackgroundResource(R.drawable.caret)
                    holder.binding.rowParent.setBackgroundColor(Color.parseColor("#FF4CAF50"))
                                }
                else -> {
                    holder.binding.dYon.setBackgroundResource(R.drawable.line)
                    holder.binding.rowParent.setBackgroundColor(Color.parseColor("#999999"))

                }
            }

        }
    }

}

