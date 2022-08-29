package com.andreirookie.essentialcookings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.andreirookie.essentialcookings.data.Recipe
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

        binding.floatingButtonSaveRecipe.setOnClickListener {
            if (!binding.editTitle.text.isNullOrBlank() &&
                !binding.editCategory.text.isNullOrBlank() &&
                !binding.editAuthor.text.isNullOrBlank()
            ) {
                val title = binding.editTitle.text.toString()
                val author =binding.editAuthor.text.toString()
                val category = binding.editCategory.text.toString()

                binding.editTitle.setText("")
                binding.editCategory.setText("")
                binding.editAuthor.setText("")

                val recipe = Recipe(id = 0L, title = title, category = category,author = author)

                viewModel.changeAndSaveRecipe(recipe)
            }
            findNavController().navigateUp()
        }


        return binding.root
    }

    //TODO Draft (via class Draft, Shared prefs??)
}