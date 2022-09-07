package com.andreirookie.essentialcookings

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.andreirookie.essentialcookings.data.Recipe
import com.andreirookie.essentialcookings.databinding.FragmentNewEditRecipeBinding
import com.andreirookie.essentialcookings.util.AppUtils.setCursorAtEndWithFocusAndShowKeyboard
import com.andreirookie.essentialcookings.util.RecipeArg
import com.andreirookie.essentialcookings.viewModel.RecipeViewModel
import com.google.android.material.snackbar.Snackbar

class NewRecipeFragment : Fragment() {

    private val viewModel by viewModels<RecipeViewModel>(ownerProducer = ::requireParentFragment)


    private var _binding: FragmentNewEditRecipeBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
     //): View? {
     //   val binding =  FragmentNewEditRecipeBinding.inflate(inflater,container, false)

        _binding =  FragmentNewEditRecipeBinding.inflate(inflater,container, false)

        // Categories dropdown menu
        val categories = resources.getStringArray(R.array.categories)
        val arrayAdapter = ArrayAdapter(requireContext(),
            R.layout.category_dropdown_item,
            categories)
        binding.autoCompleteTextView.setAdapter(arrayAdapter)


        arguments?.recipeArg?.let {
            binding.editTitle.setText(it.title)
          //  binding.editCategory.setText(it.category)
            binding.autoCompleteTextView.setText(it.category)
            binding.editAuthor.setText(it.author)
            binding.imageEditView.setImageURI(it.image)
        }
        val id = arguments?.recipeArg?.id
        println("val id =  ${arguments?.recipeArg?.id}")


        binding.editTitle.setCursorAtEndWithFocusAndShowKeyboard()

        binding.floatingButtonSaveRecipe.setOnClickListener {
            if (!binding.editTitle.text.isNullOrBlank() &&
                !binding.autoCompleteTextView.text.isNullOrBlank() &&
            //    !binding.editCategory.text.isNullOrBlank() &&
                !binding.editAuthor.text.isNullOrBlank()
            ) {
                val title = binding.editTitle.text.toString()
                val author =binding.editAuthor.text.toString()
                val category = binding.autoCompleteTextView.text.toString()
//                val category = binding.editCategory.text.toString()


                binding.editTitle.setText("")
             //   binding.editCategory.setText("")
                binding.editAuthor.setText("")

                val recipe = Recipe(
                    id = id ?: 0L,
                    title = title,
                    category = category,
                    author = author)

                viewModel.changeAndSaveRecipe(recipe)
            }
            findNavController().navigateUp()
        }




        return binding.root
    }


    //  to prevent caTEGORYdropdownmenu disappearing after fragment changing
    override fun onResume() {
        super.onResume()
        val categories = resources.getStringArray(R.array.categories)
        val arrayAdapter = ArrayAdapter(requireContext(),
            R.layout.category_dropdown_item,
            categories)

        binding.autoCompleteTextView.setAdapter(arrayAdapter)
    }

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        val categories = resources.getStringArray(R.array.categories)
//        val arrayAdapter = ArrayAdapter(requireContext(),
//            R.layout.category_dropdown_item,
//            categories)
//
//    binding.autoCompleteTextView.setAdapter(arrayAdapter)
//    }

    //TODO Draft (via class Draft, Shared prefs??)

    companion object {

        var Bundle.recipeArg: Recipe by RecipeArg
    }
}