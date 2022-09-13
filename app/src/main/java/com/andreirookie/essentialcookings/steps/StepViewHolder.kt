package com.andreirookie.essentialcookings.steps

import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.andreirookie.essentialcookings.databinding.FragmentFilterBinding
import com.andreirookie.essentialcookings.databinding.StepItemLayoutBinding

class StepViewHolder(
    private val binding: StepItemLayoutBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(step: Step) {
        binding.apply {
            stepImage.setImageURI(step.image?.toUri())
            stepContent.text = step.content
        }
    }
}