package com.example.storysnap.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.storysnap.databinding.FragmentHomeBinding
import com.example.storysnap.ui.adapter.ReloadAdapter
import com.example.storysnap.ui.adapter.StoriesAdapter
import com.example.storysnap.ui.auth.AuthViewModel
import com.example.storysnap.ui.auth.LoginActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val homeViewModel: HomeViewModel by viewModel()
    private val authViewModel: AuthViewModel by viewModel()
    private val adapter = StoriesAdapter()
    private val reloadAdapter = ReloadAdapter { adapter.retry() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.rvStory.layoutManager = LinearLayoutManager(requireContext())
        binding.rvStory.adapter = adapter.withLoadStateFooter(footer = reloadAdapter)

        authViewModel.getSession().observe(viewLifecycleOwner) { user ->
            if (user.isLogin) {
                getData(user.token)
                binding.tvName.text = "Hello, ${user.email}"
            } else {
                navigateLogin()
            }
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun navigateLogin() {
        val intent = Intent(requireContext(), LoginActivity::class.java)
        startActivity(intent)
        requireActivity().finish()
    }

    private fun getData(token: String) {
        binding.progressBar.visibility = View.VISIBLE
        homeViewModel.getStories(token).observe(viewLifecycleOwner) { pagingData ->
            adapter.submitData(lifecycle, pagingData)
            binding.progressBar.visibility = View.GONE

        }
    }
}
