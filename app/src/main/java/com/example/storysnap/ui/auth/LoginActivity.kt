// LoginActivity.kt
package com.example.storysnap.ui.auth

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.storysnap.customview.EmailCustomView
import com.example.storysnap.customview.PasswordCustomView
import com.example.storysnap.data.prefs.UserModel
import com.example.storysnap.data.remote.network.Resource
import com.example.storysnap.data.remote.request.LoginRequest
import com.example.storysnap.databinding.ActivityLoginBinding
import com.example.storysnap.utils.DialogHelper
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val viewModel: AuthViewModel by viewModel()
    private lateinit var passwordEditText: PasswordCustomView
    private lateinit var emailEditText: EmailCustomView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        passwordEditText = binding.edLoginPassword
        emailEditText = binding.edLoginEmail

        emailEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                setMyButton()
            }
            override fun afterTextChanged(s: Editable) {}
        })

        passwordEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                setMyButton()
            }
            override fun afterTextChanged(s: Editable) {}
        })
        setupView()
        playAnimation()
        setMyButton()
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

    private fun setupAction() {
        DialogHelper.showSuccessDialog(
            this,
            "Welcome!!",
            "Login Successful! Ready to explore and create captivating stories?"
        )
    }

    private fun playAnimation() {
        ObjectAnimator.ofFloat(binding.imageView, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        val title = ObjectAnimator.ofFloat(binding.textView, View.ALPHA, 1f).setDuration(100)
        val message =
            ObjectAnimator.ofFloat(binding.textView3, View.ALPHA, 1f).setDuration(100)
        val emailTextView =
            ObjectAnimator.ofFloat(binding.emailTextView, View.ALPHA, 1f).setDuration(100)
        val emailEditTextLayout =
            ObjectAnimator.ofFloat(binding.edLoginEmailLayout, View.ALPHA, 1f).setDuration(100)
        val passwordTextView =
            ObjectAnimator.ofFloat(binding.passwordTextView, View.ALPHA, 1f).setDuration(100)
        val passwordEditTextLayout =
            ObjectAnimator.ofFloat(binding.edLoginPasswordLayout, View.ALPHA, 1f).setDuration(100)
        val login = ObjectAnimator.ofFloat(binding.btnLogin, View.ALPHA, 1f).setDuration(100)

        AnimatorSet().apply {
            playSequentially(
                title,
                message,
                emailTextView,
                emailEditTextLayout,
                passwordTextView,
                passwordEditTextLayout,
                login
            )
            startDelay = 100
        }.start()
    }

    private fun login() {
        val email = binding.edLoginEmail.text.toString()
        val password = binding.edLoginPassword.text.toString()
        val loginRequest = LoginRequest(email, password)

        viewModel.login(loginRequest).observe(this) { result ->
            when (result) {
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    val token = result.data ?: ""
                    if (token.isNotEmpty()) {
                        binding.progressBar.visibility = View.GONE
                        viewModel.saveSession(UserModel(email, password, token))
                        setupAction()
                        println("Token: $token")
                    } else {
                        binding.progressBar.visibility = View.GONE
                        DialogHelper.showErrorDialog(
                            this,
                            "Login Failed",
                            "Invalid email format or password. Please check your credentials and try again."
                        )
                    }
                }
                is Resource.Error -> {
                    binding.progressBar.visibility = View.GONE
                    DialogHelper.showErrorDialog(
                        this,
                        "Error",
                        result.error ?: "An error occurred. Please try again later."
                    )
                }
                else -> {
                    binding.progressBar.visibility = View.GONE
                }
            }
        }
    }


    private fun setMyButton() {
        val resultEmail = emailEditText.text.toString().isNotEmpty()
        val resultPassword = passwordEditText.text.toString().isNotEmpty()
        binding.btnLogin.isEnabled = resultEmail && resultPassword
        binding.btnLogin.setOnClickListener {
            login()
        }
        binding.btnRegister.setOnClickListener{
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}
