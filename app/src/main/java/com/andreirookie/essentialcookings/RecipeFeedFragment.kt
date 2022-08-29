package com.andreirookie.essentialcookings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.andreirookie.essentialcookings.adapter.RecipesAdapter
import com.andreirookie.essentialcookings.adapter.OnInteractionListener
import com.andreirookie.essentialcookings.viewModel.RecipeViewModel
import com.andreirookie.essentialcookings.databinding.FragmentFeedBinding

class RecipeFeedFragment : Fragment() {

    private val viewModel by viewModels<RecipeViewModel>(ownerProducer = ::requireParentFragment)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View {

        val binding = FragmentFeedBinding.inflate(inflater, container, false)

        val adapter = RecipesAdapter(object : OnInteractionListener {})
        binding.recipesRecyclerView.adapter = adapter

        binding.addRecipeFab.setOnClickListener {
            viewModel.addRecipe()
        }

        viewModel.navigateToNewRecipeFragEvent.observe(viewLifecycleOwner) {
            findNavController().navigate(R.id.action_fragmentFeed_to_fragmentNewEditRecipe)
        }


        viewModel.data.observe(viewLifecycleOwner) { recipes ->
            println("viewModel.data.observe called")
            adapter.submitList(recipes)

        }

        return binding.root

    }
}