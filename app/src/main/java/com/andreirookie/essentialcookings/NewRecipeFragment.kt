package com.andreirookie.essentialcookings

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.andreirookie.essentialcookings.data.Recipe
import com.andreirookie.essentialcookings.databinding.FragmentNewEditRecipeBinding
import com.andreirookie.essentialcookings.steps.NewStepFragment.Companion.longArg
import com.andreirookie.essentialcookings.steps.StepsAdapter
import com.andreirookie.essentialcookings.util.AppUtils.hideKeyboard
import com.andreirookie.essentialcookings.util.RecipeArg
import com.andreirookie.essentialcookings.viewModel.RecipeViewModel
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar

class NewRecipeFragment : Fragment() {

    private val viewModel by viewModels<RecipeViewModel>(ownerProducer = ::requireParentFragment)

    private var _binding: FragmentNewEditRecipeBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
     //): View? {
     //   val binding =  FragmentNewEditRecipeBinding.inflate(inflater,container, false)

        _binding =  FragmentNewEditRecipeBinding.inflate(inflater,container, false)

        // Categories dropdown menu
        val categories = resources.getStringArray(R.array.categories)
        val arrayAdapter = ArrayAdapter(requireContext(),
            R.layout.category_dropdown_item,
            categories)
        binding.autoCompleteTextView.setAdapter(arrayAdapter)


        arguments?.recipeArg?.let {
            binding.editTitle.setText(it.title)
          //  binding.editCategory.setText(it.category)
            binding.autoCompleteTextView.setText(it.category)
            binding.editAuthor.setText(it.author)
            binding.imageEditView.setImageURI(it.image?.toUri())
        }
        val id = arguments?.recipeArg?.id ?: 0L
        val isFavorite = arguments?.recipeArg?.isFavorite ?: false
        var image= arguments?.recipeArg?.image


        //Steps
        val adapter = StepsAdapter()
        binding.stepsRecyclerView.recyclerView.adapter = adapter
        viewModel.stepsData(id).observe(viewLifecycleOwner) { thisRecipeStepsList ->
            adapter.stepsList = thisRecipeStepsList
        }


        // add step
        binding.floatingButtonAddStep.setOnClickListener {
            viewModel.startAddingStep(id)
        }
        viewModel.navigateToNewStepFragEvent.observe(viewLifecycleOwner) {
            findNavController().navigate(
                R.id.action_fragmentNewEditRecipe_to_fragmentNewStep,
            Bundle().apply { longArg = it} )
        }


//        binding.editTitle.setCursorAtEndWithFocusAndShowKeyboard()

        binding.floatingButtonSaveRecipe.setOnClickListener {
            if (!binding.editTitle.text.isNullOrBlank() &&
                !binding.autoCompleteTextView.text.isNullOrBlank() &&
            //    !binding.editCategory.text.isNullOrBlank() &&
                !binding.editAuthor.text.isNullOrBlank()
            ) {
                val title = binding.editTitle.text.toString()
                val author =binding.editAuthor.text.toString()
                val category = binding.autoCompleteTextView.text.toString()
//                val category = binding.editCategory.text.toString()

                val recipe = Recipe(
                    id = id,
                    title = title,
                    category = category,
                    author = author,
                    isFavorite = isFavorite,
                    image = image
                )
//                 binding.editTitle.setText("")
//                   binding.editCategory.setText("")
//                  binding.editAuthor.setText("")
                viewModel.changeAndSaveRecipe(recipe)
                binding.editTitle.clearFocus()
                hideKeyboard(binding.editTitle)
            }
            findNavController().navigateUp()
        }

        // Back pressed
        requireActivity().onBackPressedDispatcher
            .addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {

                override fun handleOnBackPressed() {
                    viewModel.cancelEditing()
                    findNavController().navigateUp()
                }
        })

        //Add main image
        val addingMainImageLauncher = registerForActivityResult(ActivityResultContracts.OpenDocument()) { uri ->

            // solution(Caused by: java.lang.SecurityException: Permission Denial)
            if (uri != null) {
                requireActivity().contentResolver.takePersistableUriPermission(
                    uri,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION
                )
            }

            uri ?: return@registerForActivityResult
            Snackbar.make(binding.root, "Image added", Snackbar.LENGTH_LONG).show()

            Glide
                .with(this)
                .load(uri)
                .into(binding.imageEditView)

            image = uri.toString()
        }
        binding.imageEditButton.setOnClickListener {
            addingMainImageLauncher.launch(arrayOf("*/*"))
        }


        return binding.root
    }


    //  to prevent caTEGORYdropdownmenu disappearing after fragment changing
    override fun onResume() {
        super.onResume()
        val categories = resources.getStringArray(R.array.categories)
        val arrayAdapter = ArrayAdapter(requireContext(),
            R.layout.category_dropdown_item,
            categories)

        binding.autoCompleteTextView.setAdapter(arrayAdapter)
    }

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        val categories = resources.getStringArray(R.array.categories)
//        val arrayAdapter = ArrayAdapter(requireContext(),
//            R.layout.category_dropdown_item,
//            categories)
//
//    binding.autoCompleteTextView.setAdapter(arrayAdapter)
//    }

    //TODO Draft (via class Draft, Shared prefs??)

    companion object {

        var Bundle.recipeArg: Recipe by RecipeArg
    }
}