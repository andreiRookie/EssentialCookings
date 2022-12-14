package com.andreirookie.essentialcookings.adapter


import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.core.net.toUri
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.andreirookie.essentialcookings.R
import com.andreirookie.essentialcookings.data.Recipe
import com.andreirookie.essentialcookings.databinding.RecipeItemBinding

internal class RecipesAdapter(
    private val interactionListener: OnInteractionListener
) : ListAdapter<Recipe, RecipesAdapter.RecipeViewHolder>(RecipeDiffCallBack()) {



    class RecipeViewHolder(
        private val binding: RecipeItemBinding,
        private val listener: OnInteractionListener
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(recipe: Recipe) = with(binding) {
            recipeTitle.text = recipe.title
            recipeCategory.text = binding.root.context.getString(R.string.category, recipe.category)
            recipeAuthor.text = binding.root.context.getString(R.string.author, recipe.author)
            recipeIcon.setImageURI(recipe.image?.toUri())

            // Make Favorite
            favoriteRecipeButton.isChecked = recipe.isFavorite
            favoriteRecipeButton.setOnClickListener {
                listener.onFavorite(recipe)
            }

            //Single Recipe Fragment
            binding.root.setOnClickListener {
                listener.onBinding(recipe)
            }


            menuButton.setOnClickListener {
                PopupMenu(it.context, it).apply {
                    inflate(R.menu.menu_button_popup)

                    setOnMenuItemClickListener { item ->
                        when (item.itemId) {
                            R.id.remove -> {
                                listener.onRemove(recipe)
                                true
                            }
                            R.id.edit -> {
                                listener.onEdit(recipe)
                                true
                            }
                            R.id.add_image -> {
                                listener.onAddImage(recipe)
                                true
                            }
                            else -> false
                        }
                    }
                }.show()
            }
        }

//        @SuppressLint("SupportAnnotationUsage")
//        @DrawableRes
//        private fun getMakeFavoriteButtonIconResId(isFavorite: Boolean) {
//            if (isFavorite) R.drawable.ic_favorite_32 else R.drawable.ic_not_favorite_32
//        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RecipeItemBinding.inflate(inflater,parent, false)
        return RecipeViewHolder(binding, interactionListener)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val recipe = getItem(position)
        holder.bind(recipe)
    }

}