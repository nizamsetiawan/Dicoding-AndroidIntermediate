package com.example.storysnap.ui.auth

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.storysnap.databinding.ActivityWelcomeBinding

class WelcomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWelcomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupView()
        setupAction()
        playAnimation()
    }

    private fun playAnimation() {
        ObjectAnimator.ofFloat(binding.imageView, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        val login = ObjectAnimator.ofFloat(binding.btnLogin, View.ALPHA, 0f, 1f).apply {
            duration = 500
        }

        val signup = ObjectAnimator.ofFloat(binding.btnRegister, View.ALPHA, 0f, 1f).apply {
            duration = 500
        }

        val title = ObjectAnimator.ofFloat(binding.textView5, View.ALPHA, 0f, 1f).apply {
            duration = 500
        }

        val desc = ObjectAnimator.ofFloat(binding.textView6, View.ALPHA, 0f, 1f).apply {
            duration = 500
        }

        val desc2 = ObjectAnimator.ofFloat(binding.textView7, View.ALPHA, 0f, 1f).apply {
            duration = 500
        }

        val together = AnimatorSet().apply {
            playTogether(login, signup)
        }

        AnimatorSet().apply {
            playSequentially(title, desc, together, desc2)
            start()
        }
    }

    private fun setupAction() {
        binding.btnLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        binding.btnRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }


}