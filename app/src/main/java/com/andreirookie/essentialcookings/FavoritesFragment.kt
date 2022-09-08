package com.andreirookie.essentialcookings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.SimpleItemAnimator
import com.andreirookie.essentialcookings.NewRecipeFragment.Companion.recipeArg
import com.andreirookie.essentialcookings.adapter.OnInteractionListener
import com.andreirookie.essentialcookings.adapter.RecipesAdapter
import com.andreirookie.essentialcookings.data.Recipe
import com.andreirookie.essentialcookings.databinding.FragmentFavoritesBinding
import com.andreirookie.essentialcookings.dragAndDrop.ItemTouchHelperSimpleCallback
import com.andreirookie.essentialcookings.viewModel.RecipeViewModel
import com.google.android.material.snackbar.Snackbar

class FavoritesFragment : Fragment() {

    private val viewModel by viewModels<RecipeViewModel>(ownerProducer = ::requireParentFragment)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View {

        val binding = FragmentFavoritesBinding.inflate(inflater, container, false)

        val adapter = RecipesAdapter(object : OnInteractionListener {
            override fun onRemove(recipe: Recipe) {
                viewModel.remove(recipeId =recipe.id)
            }
            override fun onEdit(recipe: Recipe) {
                viewModel.edit(recipe)
            }
            override fun onFavorite(recipe: Recipe) {
                viewModel.favorite(recipe.id)
            }
            override fun onAddImage(recipe: Recipe) {
                viewModel.startAddingImage(recipe)
            }

            override fun onBinding(recipe: Recipe) {
                viewModel.navigateToSingleRecipeFragment(recipe)            }
        })
        binding.fragmentFavoritesLayout.feedBackground.setImageResource(R.drawable.favorites_feed_background)
        binding.fragmentFavoritesLayout.addRecipeFab.visibility = View.GONE

        binding.fragmentFavoritesLayout.recipesRecyclerView.adapter = adapter

        // Show only favorites
        viewModel.favoriteRecipesData.observe(viewLifecycleOwner) { favoriteRecipes ->
            println("viewModel.favoriteRecipesData.observe: $favoriteRecipes")
            adapter.submitList(favoriteRecipes)
        }

        // Prevent recyclerView's Item blinking
        val simpleItemAnimator = binding.fragmentFavoritesLayout.recipesRecyclerView.itemAnimator as SimpleItemAnimator
        simpleItemAnimator.supportsChangeAnimations = false

        // Drag & drop
        val itemTouchHelper = ItemTouchHelper(ItemTouchHelperSimpleCallback(viewModel, adapter))
        itemTouchHelper.attachToRecyclerView(binding.fragmentFavoritesLayout.recipesRecyclerView)


        // Edit recipe
        viewModel.editedRecipe.observe(viewLifecycleOwner) {
            if(it.id == 0L) return@observe
            viewModel.editRecipe(it)
        }
        viewModel.navigateToEditRecipeFragEvent.observe(viewLifecycleOwner) {

            val navHostFragment =
                activity?.supportFragmentManager?.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment

            navHostFragment.navController.navigate(
                R.id.action_fragmentFavorites_to_fragmentNewEditRecipe,
                Bundle().apply { recipeArg = it }
            )
        }

        // Add image
        val addingImageLauncher = registerForActivityResult(ActivityResultContracts.OpenDocument()) { uri ->
            uri ?: return@registerForActivityResult
            Snackbar.make(binding.root, "Image added", Snackbar.LENGTH_SHORT).show()

            viewModel.addImageUri(uri)
        }
        var mRecipe : Recipe? = null
        viewModel.addingImageEvent.observe(viewLifecycleOwner) {recipe ->
            mRecipe = recipe
            println("addingImageEvent.observe $recipe")
//            addingImageLauncher.launch(arrayOf("*/jpeg", "*/jpg","*/png"))
//            addingImageLauncher.launch(arrayOf("images/*"))
            addingImageLauncher.launch(arrayOf("*/*"))
        }
        viewModel.addingImageUriEvent.observe(viewLifecycleOwner) {uri ->
            println("viewModel.addImage(${mRecipe?.id}, $uri)")
            mRecipe?.let { viewModel.addImage(it.id, uri) }
        }

        //Single Recipe Fragment
        viewModel.navigateToSingleRecipeFragmentEvent.observe(viewLifecycleOwner) {
            val navHostFragment =
                activity?.supportFragmentManager?.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment

            navHostFragment.navController.navigate(
                R.id.action_fragmentFavorites_to_singleRecipeFragment,
                Bundle().apply { recipeArg = it }
            )
        }

        return binding.root

    }
}