package com.andreirookie.essentialcookings.steps

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.andreirookie.essentialcookings.databinding.StepItemLayoutBinding

class StepsAdapter() : RecyclerView.Adapter<StepViewHolder>() {

    var stepsList = emptyList<Step>()
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StepViewHolder {
        val binding = StepItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StepViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StepViewHolder, position: Int) {
        val step = stepsList[position]
        holder.bind(step)
    }

    override fun getItemCount(): Int {
        return stepsList.size
    }


}