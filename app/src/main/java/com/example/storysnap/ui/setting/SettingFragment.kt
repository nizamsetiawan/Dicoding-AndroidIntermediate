package com.example.storysnap.ui.setting

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.storysnap.databinding.FragmentSettingBinding
import com.example.storysnap.ui.auth.AuthViewModel
import com.example.storysnap.ui.home.HomeViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class SettingFragment : Fragment() {

    private var _binding: FragmentSettingBinding? = null
    private val authViewModel: AuthViewModel by viewModel()
    private val homeViewModel: HomeViewModel by viewModel()

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        _binding = FragmentSettingBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Handle logout
        binding.actionLogout.setOnClickListener {
            logout()
        }
        authViewModel.getSession().observe(viewLifecycleOwner) { user ->
            if (user.isLogin) {
                homeViewModel.getStories(user.token)
                binding.etEmail.text = user.email
                binding.profileImage.text = getEmailInitial(user.email)
            }
        }
        mainButton()


        return root
    }

    private fun logout() {
        authViewModel.logout()
    }
    private fun mainButton() {
        binding.btnMaps.setOnClickListener {
            val intent = Intent(requireContext(), MapsActivity::class.java)
            startActivity(intent)
        }
        binding.btnLanguage.setOnClickListener {
            startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
        }
    }



    private fun getEmailInitial(email: String): String {
        val emailParts = email.split("@").toTypedArray()
        if (emailParts.isNotEmpty()) {
            val username = emailParts[0]
            return if (username.length >= 2) {
                username.substring(0, 2).toUpperCase()
            } else {
                username.toUpperCase()
            }
        }
        return ""
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
