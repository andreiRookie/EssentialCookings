package com.andreirookie.essentialcookings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.andreirookie.essentialcookings.NewRecipeFragment.Companion.recipeArg
import com.andreirookie.essentialcookings.adapter.OnInteractionListener
import com.andreirookie.essentialcookings.adapter.RecipesAdapter
import com.andreirookie.essentialcookings.data.Recipe
import com.andreirookie.essentialcookings.databinding.FragmentFeedBinding
import com.andreirookie.essentialcookings.dragAndDrop.ItemTouchHelperSimpleCallback
import com.andreirookie.essentialcookings.viewModel.RecipeViewModel

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

            override fun onFavorite(recipe: Recipe) {
                viewModel.favorite(recipe.id)
            }
        })

        binding.recipesRecyclerView.adapter = adapter

        // Prevent recyclerView's Item blinking
        val simpleItemAnimator = binding.recipesRecyclerView.itemAnimator as SimpleItemAnimator
        simpleItemAnimator.supportsChangeAnimations = false

        // Drag & drop
        val itemTouchHelper = ItemTouchHelper(ItemTouchHelperSimpleCallback(viewModel, adapter))
        itemTouchHelper.attachToRecyclerView(binding.recipesRecyclerView)

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


    // Drag & drop
//    private val simpleCallback = object : ItemTouchHelper.SimpleCallback(
//        ItemTouchHelper.UP.or(ItemTouchHelper.DOWN), 0
//    ) {
//        override fun onMove(
//            recyclerView: RecyclerView,
//            viewHolder: RecyclerView.ViewHolder,
//            target: RecyclerView.ViewHolder
//        ): Boolean {
//            var fromPosition = viewHolder.absoluteAdapterPosition
//            var toPosition = target.absoluteAdapterPosition
//
//            viewModel.swap(fromPosition,toPosition)
//            recyclerView.adapter?.notifyItemMoved(fromPosition,toPosition)
//            return true
//        }
//
//        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
//            TODO("Not yet implemented")
//        }
//
//    }

}