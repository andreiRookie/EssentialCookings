package com.andreirookie.essentialcookings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import com.andreirookie.essentialcookings.NewRecipeFragment.Companion.recipeArg
import com.andreirookie.essentialcookings.adapter.RecipesAdapter
import com.andreirookie.essentialcookings.databinding.FragmentSingleRecipeBinding

class SingleRecipeFragment : Fragment() {


//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentSingleRecipeBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment

        with(binding) {
            arguments?.recipeArg?.let {
                titleTextView.text = it.title
                categoryTextView.text = "(${it.category})"
                authorTextView.text = "by ${it.author}"
                singleRecipeIcon.setImageURI(it.image?.toUri())
            }
        }

        binding.stepsRecyclerView.adapter

        return binding.root
    }


}