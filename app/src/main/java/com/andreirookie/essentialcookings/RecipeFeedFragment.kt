package com.andreirookie.essentialcookings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.andreirookie.essentialcookings.NewRecipeFragment.Companion.recipeArg
import com.andreirookie.essentialcookings.adapter.RecipesAdapter
import com.andreirookie.essentialcookings.adapter.OnInteractionListener
import com.andreirookie.essentialcookings.data.Recipe
import com.andreirookie.essentialcookings.viewModel.RecipeViewModel
import com.andreirookie.essentialcookings.databinding.FragmentFeedBinding

class RecipeFeedFragment : Fragment() {

    private val viewModel by viewModels<RecipeViewModel>(ownerProducer = ::requireParentFragment)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View {

        val binding = FragmentFeedBinding.inflate(inflater, container, false)

        val adapter = RecipesAdapter(object : OnInteractionListener {
            override fun onRemove(recipe: Recipe) {
                viewModel.remove(recipeId =recipe.id)

            }

            override fun onEdit(recipe: Recipe) {
                viewModel.edit(recipe)
            }
        })
        binding.recipesRecyclerView.adapter = adapter

        viewModel.data.observe(viewLifecycleOwner) { recipes ->
            println("viewModel.data.observe called")
            adapter.submitList(recipes)

        }

        // Add recipe
        binding.addRecipeFab.setOnClickListener {
            viewModel.addRecipe()
        }
        viewModel.navigateToNewRecipeFragEvent.observe(viewLifecycleOwner) {
            findNavController().navigate(R.id.action_fragmentFeed_to_fragmentNewEditRecipe)
        }


        // Edit recipe
        viewModel.editedRecipe.observe(viewLifecycleOwner) {
            if(it.id == 0L) return@observe
            viewModel.editRecipe(it)
        }
        viewModel.navigateToEditRecipeFragEvent.observe(viewLifecycleOwner) {

            val fragment =
                activity?.supportFragmentManager?.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment

            fragment.navController.navigate(
                R.id.action_fragmentFeed_to_fragmentNewEditRecipe,
                Bundle().apply { recipeArg = it }
            )
        }


        return binding.root

    }
}