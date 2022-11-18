package com.andreirookie.essentialcookings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.andreirookie.essentialcookings.databinding.FragmentFilterBinding
import com.andreirookie.essentialcookings.viewModel.RecipeViewModel

class FilterFragment : Fragment() {


    private val viewModel by viewModels<RecipeViewModel>(ownerProducer = ::requireParentFragment)


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentFilterBinding.inflate(inflater, container, false)

        binding.americanFilterItemCheckbox.setOnCheckedChangeListener { _, isChecked ->
            if (!isAnyFilterChecked(binding)) {
                binding.americanFilterItemCheckbox.isChecked = true
                return@setOnCheckedChangeListener
            }

            val category = binding.americanFilterItemText.text.toString()
            if (!isChecked) {
                viewModel.filter(category = category)
            } else {
                viewModel.cancelFilter(category)
            }
        }
        binding.asianFilterItemCheckbox.setOnCheckedChangeListener { _, isChecked ->
            if (!isAnyFilterChecked(binding)) {
                binding.asianFilterItemCheckbox.isChecked = true
                return@setOnCheckedChangeListener
            }

            val category = binding.asianFilterItemText.text.toString()
            if (!isChecked) {
                viewModel.filter(category = category)
            } else {
                viewModel.cancelFilter(category)
            }
        }
        binding.easternFilterItemCheckbox.setOnCheckedChangeListener { _, isChecked ->
            if (!isAnyFilterChecked(binding)) {
                binding.easternFilterItemCheckbox.isChecked = true
                return@setOnCheckedChangeListener
            }

            val category = binding.easternFilterItemText.text.toString()
            if (!isChecked) {
                viewModel.filter(category = category)
            } else {
                viewModel.cancelFilter(category)
            }
        }
        binding.europeanFilterItemCheckbox.setOnCheckedChangeListener { _, isChecked ->
            if (!isAnyFilterChecked(binding)) {
                binding.europeanFilterItemCheckbox.isChecked = true
                return@setOnCheckedChangeListener
            }

            val category = binding.europeanFilterItemText.text.toString()
            if (!isChecked) {
                viewModel.filter(category = category)
            } else {
                viewModel.cancelFilter(category)
            }
        }
        binding.mediterraneanFilterItemCheckbox.setOnCheckedChangeListener { _, isChecked ->
            if (!isAnyFilterChecked(binding)) {
                binding.mediterraneanFilterItemCheckbox.isChecked = true
                return@setOnCheckedChangeListener
            }

            val category = binding.mediterraneanFilterItemText.text.toString()
            if (!isChecked) {
                viewModel.filter(category = category)
            } else {
                viewModel.cancelFilter(category)
            }
        }
        binding.panAsianFilterItemCheckbox.setOnCheckedChangeListener { _, isChecked ->
            if (!isAnyFilterChecked(binding)) {
                binding.panAsianFilterItemCheckbox.isChecked = true
                return@setOnCheckedChangeListener
            }

            val category = binding.panAsianFilterItemText.text.toString()
            if (!isChecked) {
                viewModel.filter(category = category)
            } else {
                viewModel.cancelFilter(category)
            }
        }
        binding.russianFilterItemCheckbox.setOnCheckedChangeListener { _, isChecked ->
            if (!isAnyFilterChecked(binding)) {
                binding.russianFilterItemCheckbox.isChecked = true
                return@setOnCheckedChangeListener
            }

            val category = binding.russianFilterItemText.text.toString()
            if (!isChecked) {
                viewModel.filter(category = category)
            } else {
                viewModel.cancelFilter(category)
            }
        }

        return binding.root
    }


    private fun isAnyFilterChecked(binding: FragmentFilterBinding): Boolean {
        return binding.americanFilterItemCheckbox.isChecked ||
                binding.asianFilterItemCheckbox.isChecked||
                binding.easternFilterItemCheckbox.isChecked||
                binding.europeanFilterItemCheckbox.isChecked||
                binding.mediterraneanFilterItemCheckbox.isChecked||
                binding.panAsianFilterItemCheckbox.isChecked||
                binding.russianFilterItemCheckbox.isChecked
    }


    // не работает
//    fun CheckBox / CompoundButton.setFil(isChecked: Boolean) {
//        this.setOnCheckedChangeListener { _, isChecked ->
//            val category = this.text.toString()
//            if (!isChecked) {
//                viewModel.filter(category = category)
//            } else {
//                viewModel.cancelFilter(category)
//            }
//        }
//    }

    // не работает
//    private fun setFilter(checkBox: CheckBox, isChecked: Boolean) {
//println(checkBox)
//        val category = checkBox.text.toString()
//println(category)
//        if (!isChecked) {
//            println("viewModel.filter(category = $category)")
//            viewModel.filter(category = category)
//        } else {
//            viewModel.cancelFilter(category)
//        }
//
//    }

}