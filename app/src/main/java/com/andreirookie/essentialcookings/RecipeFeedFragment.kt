package com.andreirookie.essentialcookings

import android.app.Activity
import android.content.Intent
import android.content.Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION
import android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION
import android.os.Bundle
import android.view.*
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.SimpleItemAnimator
import com.andreirookie.essentialcookings.NewRecipeFragment.Companion.recipeArg
import com.andreirookie.essentialcookings.adapter.OnInteractionListener
import com.andreirookie.essentialcookings.adapter.RecipesAdapter
import com.andreirookie.essentialcookings.data.Recipe
import com.andreirookie.essentialcookings.databinding.FragmentFeedBinding
import com.andreirookie.essentialcookings.dragAndDrop.ItemTouchHelperSimpleCallback
import com.andreirookie.essentialcookings.viewModel.RecipeViewModel
import com.google.android.material.snackbar.Snackbar

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
            override fun onAddImage(recipe: Recipe) {
                viewModel.startAddingImage(recipe)
            }

            override fun onBinding(recipe: Recipe) {
                viewModel.navigateToSingleRecipeFragment(recipe)
            }
        })

        binding.recipesRecyclerView.adapter = adapter
        viewModel.data.observe(viewLifecycleOwner) { recipes ->
            adapter.submitList(recipes)
        }


        // Prevent recyclerView's Item blinking
        val simpleItemAnimator = binding.recipesRecyclerView.itemAnimator as SimpleItemAnimator
        simpleItemAnimator.supportsChangeAnimations = false

        // Drag & drop
        val itemTouchHelper = ItemTouchHelper(ItemTouchHelperSimpleCallback(viewModel, adapter))
        itemTouchHelper.attachToRecyclerView(binding.recipesRecyclerView)


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
            println("viewModel.editRecipe($it)")
            viewModel.editRecipe(it)
        }
        viewModel.navigateToEditRecipeFragEvent.observe(viewLifecycleOwner) {
            println(" viewModel.navigateToEditRecipeFragEvent.observe")
            findNavController().navigate(
                R.id.action_fragmentFeed_to_fragmentNewEditRecipe,
                Bundle().apply { recipeArg = it }
            )

//            val navHostFragment =
//                activity?.supportFragmentManager?.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
//
//            navHostFragment.navController.navigate(
//                R.id.action_fragmentFeed_to_fragmentNewEditRecipe,
//                Bundle().apply { recipeArg = it }
//            )
        }

        // Add image
        // !?!?Caused by: java.lang.SecurityException: Permission Denial: opening provider com.android.providers.downloads.DownloadStorageProvider from ProcessRecord{7ea4b52 21522:com.andreirookie.essentialcookings/u0a157} (pid=21522, uid=10157) requires that you obtain access using ACTION_OPEN_DOCUMENT or related APIs
        val addingImageLauncher = registerForActivityResult(ActivityResultContracts.OpenDocument()) { uri ->

            // !?!? -> solution
            if (uri != null) {
                requireActivity().contentResolver.takePersistableUriPermission(
                    uri,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION
                )
            }
            // !?!?

            uri ?: return@registerForActivityResult
            Snackbar.make(binding.root, "Image added", Snackbar.LENGTH_LONG).show()

            viewModel.addImageUri(uri.toString())
        }
        var mRecipe : Recipe? = null
        viewModel.addingImageEvent.observe(viewLifecycleOwner) {recipe ->
            mRecipe = recipe
            println("addingImageEvent.observe $recipe")

            // не подсвечивает/не дает выбрать картинкаи? толькл если */*
//            addingImageLauncher.launch(arrayOf("*/jpeg", "*/jpg","*/png"))
//            addingImageLauncher.launch(arrayOf("images/*"))
            // в самом OpenDocument() стоит .setType("*/*")

            // - надо ("image/")!

            addingImageLauncher.launch(arrayOf("image/*"))
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
                R.id.action_fragmentFeed_to_singleRecipeFragment,
                Bundle().apply { recipeArg = it }
                )
        }


        return binding.root

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // The usage of an interface lets you inject your own implementation
        val menuHost: MenuHost = requireActivity()
        // Add menu items without using the Fragment Menu APIs
        // Note how we can tie the MenuProvider to the viewLifecycleOwner
        // and an optional Lifecycle.State (here, RESUMED) to indicate when
        // the menu should be visible
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                // Add menu items here
                menuInflater.inflate(R.menu.menu_search_button, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                // Handle the menu selection
                return when (menuItem.itemId) {
                    R.id.search_button -> {

                        val searchView = menuItem.actionView as SearchView

                        searchView.isSubmitButtonEnabled = false

                        //через разметку не работает почему-то
                        searchView.queryHint = getString(R.string.search_hint)

                        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                            override fun onQueryTextSubmit(query: String?): Boolean {
                                searchView.clearFocus()
                                viewModel.search(query)
                                return false
                            }

                            override fun onQueryTextChange(newText: String?): Boolean {
                                viewModel.search(newText)
                                return false
                            }

                        })

                        // clearCompletedTasks()
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

//      нерабочая схема - BottomNavBar should be in AppActivity
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        val bottomNav = activity?.findViewById<BottomNavigationView>(R.id.bottom_nav)
//        bottomNav?.setOnItemSelectedListener{
//            when (it.itemId) {
//                R.id.fragmentFeed -> true
//                R.id.fragmentFavorites -> {
//                    viewModel.showFavoriteRecipes() >> (repository.showFavorite,  SingleLiveEvent... etc)
//                 true
//                }
//                R.id.fragmentFilter -> true
//                else -> false
//            }
//        }
//
//    }


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