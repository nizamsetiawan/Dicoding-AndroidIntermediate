package com.example.storysnap.ui.story

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.storysnap.R
import com.example.storysnap.data.remote.network.Resource
import com.example.storysnap.databinding.FragmentStoryBinding
import com.example.storysnap.ui.auth.AuthViewModel
import com.example.storysnap.utils.DialogHelper
import com.example.storysnap.utils.getImageUri
import com.example.storysnap.utils.reduceFileImage
import com.example.storysnap.utils.uriToFile
import org.koin.androidx.viewmodel.ext.android.viewModel

class StoryFragment : Fragment() {

    private var _binding: FragmentStoryBinding? = null
    private val binding get() = _binding!!
    private var currentImageUri: Uri? = null
    private val storyViewModel: StoryViewModel by viewModel()
    private val authViewModel: AuthViewModel by viewModel()
    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                Toast.makeText(requireContext(), "Permission request granted", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(requireContext(), "Permission request denied", Toast.LENGTH_LONG).show()
            }
        }

    private fun allPermissionsGranted() =
        ContextCompat.checkSelfPermission(
            requireContext(),
            REQUIRED_PERMISSION
        ) == PackageManager.PERMISSION_GRANTED

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStoryBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.galleryButton.setOnClickListener { startGallery() }
        binding.cameraButton.setOnClickListener { startCamera() }
        binding.buttonAdd.setOnClickListener { uploadImage() }

        if (!allPermissionsGranted()) {
            requestPermissionLauncher.launch(REQUIRED_PERMISSION)
        }

        return root
    }

    private fun startGallery() {
        launcherGallery.launch("image/*")
    }

    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) {
            currentImageUri = uri
            showImage()
        } else {
            Log.d("Photo Picker", "No media selected")
        }
    }

    private fun startCamera() {
        currentImageUri = getImageUri(requireContext())
        launcherIntentCamera.launch(currentImageUri)
    }

    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { isSuccess ->
        if (isSuccess) {
            showImage()
        }
    }

    private fun showImage() {
        currentImageUri?.let {
            Log.d("Image URI", "showImage: $it")
            binding.prevuploadImage.setImageURI(it)
        }
    }


    private fun uploadImage() {
        currentImageUri?.let { uri ->
            val imageFile = uriToFile(uri, requireContext()).reduceFileImage()
            Log.d("Image File", "showImage: ${imageFile.path}")
            val description = binding.edAddDescription.text.toString()

            if (description.isBlank()) {
                Toast.makeText(requireContext(), "Deskripsi tidak boleh kosong", Toast.LENGTH_SHORT).show()
                return
            }

            authViewModel.getSession().observe(viewLifecycleOwner) { story ->
                val token = story.token
                storyViewModel.uploadStory(token, imageFile, description).observe(viewLifecycleOwner) { result ->
                    if (result != null) {
                        when (result) {
                            is Resource.Loading -> {
                                binding.progressBar.visibility = View.VISIBLE
                            }
                            is Resource.Success -> {
                                DialogHelper.showSuccessDialog(
                                    requireContext(),
                                    "Success",
                                    "Your story has been uploaded"
                                )
                                binding.progressBar.visibility = View.GONE
                                findNavController().navigate(R.id.navigation_home)
                            }
                            is Resource.Error -> {
                                DialogHelper.showErrorDialog(
                                    requireContext(),
                                    "Error",
                                    result.error
                                )
                                binding.progressBar.visibility = View.GONE
                            }
                        }
                    }
                }
            }
        } ?: run {
            Toast.makeText(requireContext(), "Silakan Pilih Gambar Dahulu", Toast.LENGTH_SHORT).show()
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val REQUIRED_PERMISSION = Manifest.permission.CAMERA
    }
}
