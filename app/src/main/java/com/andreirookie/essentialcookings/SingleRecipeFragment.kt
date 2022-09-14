package com.andreirookie.essentialcookings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.viewModels
import com.andreirookie.essentialcookings.NewRecipeFragment.Companion.recipeArg
import com.andreirookie.essentialcookings.adapter.RecipesAdapter
import com.andreirookie.essentialcookings.databinding.FragmentSingleRecipeBinding
import com.andreirookie.essentialcookings.steps.StepsAdapter
import com.andreirookie.essentialcookings.viewModel.RecipeViewModel

class SingleRecipeFragment : Fragment() {


//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//    }
private val viewModel by viewModels<RecipeViewModel>(ownerProducer = ::requireParentFragment)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentSingleRecipeBinding.inflate(inflater, container, false)

        with(binding) {
            arguments?.recipeArg?.let {
                titleTextView.text = it.title
                categoryTextView.text = "(${it.category})"
                authorTextView.text = "by ${it.author}"
                singleRecipeIcon.setImageURI(it.image?.toUri())
            }
        }
        val recipeId = arguments?.recipeArg?.id ?: 0L

        //Steps
        val adapter = StepsAdapter()
        binding.stepsRecyclerView.recyclerView.adapter = adapter
        viewModel.stepsData(recipeId).observe(viewLifecycleOwner) { thisRecipeStepsList ->
            adapter.stepsList = thisRecipeStepsList
        }

        return binding.root
    }


}