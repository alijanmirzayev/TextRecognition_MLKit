package com.alijan.textrecognition_mlkit.ui.home

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.alijan.textrecognition_mlkit.databinding.FragmentHomeBinding
import com.alijan.textrecognition_mlkit.ui.text.TextViewModel

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel by activityViewModels<TextViewModel>()
    private var imageUri: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        buttonClick()

    }

    private fun buttonClick() {
        // Click event get image from gallery
        binding.buttonOpenGallery.setOnClickListener {
            galleryLauncher.launch("image/*")
        }

        // Click event open camera
        binding.buttonOpenCamera.setOnClickListener {

        }

        // Click event select image
        binding.buttonSelectImage.setOnClickListener {
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToTextFragment()
            )
        }
    }

    private val galleryLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) {
        imageUri = it
        viewModel.textRecognitionFromURI(requireContext(), it!!)
        handleImageUI()
    }

    private fun handleImageUI() {
        binding.apply {
            imageView.visibility = View.VISIBLE
            imageView.setImageURI(imageUri)
            buttonSelectImage.visibility = View.VISIBLE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
