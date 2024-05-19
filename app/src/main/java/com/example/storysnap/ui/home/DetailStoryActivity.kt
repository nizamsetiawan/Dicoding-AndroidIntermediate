package com.example.storysnap.ui.home

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.storysnap.R
import com.example.storysnap.databinding.ActivityDetailStoryBinding
import com.example.storysnap.ui.auth.AuthViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailStoryActivity : AppCompatActivity() {
    private val authViewModel: AuthViewModel by viewModel()
    private val homeViewModel: HomeViewModel by viewModel()
    private lateinit var binding: ActivityDetailStoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = resources.getString(R.string.Detail)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val identifier = intent.getStringExtra(EXTRA_IDENTIFIER).toString()
        binding.progressBarDetail.visibility = View.VISIBLE


        authViewModel.getSession().observe(this) { story ->
            val token = story.token
            homeViewModel.getDetail(token, identifier)
        }
        homeViewModel.detail.observe(this) { story ->
            Glide.with(this@DetailStoryActivity)
                .load(story.photoUrl)
                .into(binding.previewImageViewDetail)
            binding.nameDetail.text = story.name
            binding.descriptionDetail.text = story.description
            binding.progressBarDetail.visibility = View.GONE
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }



    companion object {
        const val EXTRA_IDENTIFIER = "extra_identifier"
    }
}
