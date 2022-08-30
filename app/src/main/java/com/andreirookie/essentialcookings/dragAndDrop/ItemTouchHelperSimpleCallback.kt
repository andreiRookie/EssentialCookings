package com.andreirookie.essentialcookings.dragAndDrop

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.andreirookie.essentialcookings.adapter.RecipesAdapter
import com.andreirookie.essentialcookings.viewModel.RecipeViewModel


// Drag & drop
internal class ItemTouchHelperSimpleCallback(viewModel: RecipeViewModel, adapter: RecipesAdapter) : ItemTouchHelper.SimpleCallback(
    ItemTouchHelper.UP.or(ItemTouchHelper.DOWN), 0)

{
    private val localViewModel : RecipeViewModel = viewModel
    private val localAdapter : RecipesAdapter = adapter

        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            var fromPosition = viewHolder.absoluteAdapterPosition
            var toPosition = target.absoluteAdapterPosition

            localViewModel.swap(fromPosition,toPosition)
            localAdapter.notifyItemMoved(fromPosition,toPosition)
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            TODO("Not yet implemented")
        }


}