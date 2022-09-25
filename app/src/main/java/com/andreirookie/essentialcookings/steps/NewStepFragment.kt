package com.andreirookie.essentialcookings.steps

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.andreirookie.essentialcookings.databinding.FragmentNewStepLayoutBinding
import com.andreirookie.essentialcookings.util.LongArg
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar

class NewStepFragment : Fragment() {

    private val stepViewModel by viewModels<StepViewModel>(ownerProducer = ::requireParentFragment)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //): View? {
        val binding = FragmentNewStepLayoutBinding.inflate(inflater, container, false)

        // id рецепта, если в step(...val recipeId:Long,..)
        val recipeId = arguments?.longArg ?: 0L
        var image = ""


        //Add step image
        val addingMainImageLauncher = registerForActivityResult(ActivityResultContracts.OpenDocument()) { uri ->

            // solution(Caused by: java.lang.SecurityException: Permission Denial)
            if (uri != null) {
                requireActivity().contentResolver.takePersistableUriPermission(
                    uri,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION
                )
            }
            uri ?: return@registerForActivityResult
            Snackbar.make(binding.root, "Step image added", Snackbar.LENGTH_LONG).show()

            Glide
                .with(this)
                .load(uri)
                .into(binding.imageEditView)

            image = uri.toString()
        }
        binding.imageEditButton.setOnClickListener {
            addingMainImageLauncher.launch(arrayOf("image/*"))
        }

        binding.floatingButtonSaveStep.setOnClickListener {
            if (!binding.editStepContent.text.isNullOrBlank()) {
                val content = binding.editStepContent.text.toString()

                val step = Step(
//                    val recipeId:Long,
                    image = image,
                    content = content
                )

                stepViewModel.saveStep(step)
            }
            findNavController().navigateUp()
        }

        return binding.root
    }

    companion object {
        var Bundle.longArg: Long by LongArg
    }
}