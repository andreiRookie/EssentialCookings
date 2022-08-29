package com.andreirookie.essentialcookings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.andreirookie.essentialcookings.databinding.FragmentNewEditRecipeBinding
import com.andreirookie.essentialcookings.util.AppUtils.setCursorAtEndWithFocusAndShowKeyboard
import com.andreirookie.essentialcookings.viewModel.RecipeViewModel

class NewRecipeFragment : Fragment() {

    private val viewModel by viewModels<RecipeViewModel>(ownerProducer = ::requireParentFragment)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding =  FragmentNewEditRecipeBinding.inflate(inflater,container, false)


        binding.editTitle.setCursorAtEndWithFocusAndShowKeyboard()


        return binding.root
    }
}