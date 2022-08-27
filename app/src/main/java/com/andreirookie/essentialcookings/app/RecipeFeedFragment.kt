package com.andreirookie.essentialcookings.app

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.andreirookie.essentialcookings.app.adapter.RecipesAdapter
import com.andreirookie.essentialcookings.app.adapter.onIneractionListener
import com.andreirookie.essentialcookings.app.viewModel.RecipeViewModel
import com.andreirookie.essentialcookings.databinding.FragmentFeedBinding

class RecipeFeedFragment : Fragment() {

    private val viewModel by viewModels<RecipeViewModel>(ownerProducer = ::requireParentFragment)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View {

        val binding = FragmentFeedBinding.inflate(inflater, container, false)

      //  setContentView(binding.root)

        val adapter = RecipesAdapter(object : onIneractionListener {})
        binding.recipesRecyclerView.adapter = adapter


        viewModel.data.observe(viewLifecycleOwner) { recipes ->
            println("viewModel.data.observe")
            adapter.submitList(recipes)

        }

        return binding.root



    }
}