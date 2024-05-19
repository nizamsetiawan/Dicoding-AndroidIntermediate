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
import com.example.storysnap.customview.UsernameCustomView
import com.example.storysnap.data.remote.network.Resource
import com.example.storysnap.data.remote.request.RegisterRequest
import com.example.storysnap.databinding.ActivityRegisterBinding
import com.example.storysnap.utils.DialogHelper
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegisterActivity : AppCompatActivity() {
    private  lateinit var binding: ActivityRegisterBinding
    private val viewModel: AuthViewModel by viewModel()
    private lateinit var userNameText : UsernameCustomView
    private lateinit var passwordText : PasswordCustomView
    private lateinit var emailText : EmailCustomView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        userNameText = binding.edRegisterName
        passwordText = binding.edRegisterPassword
        emailText = binding.edRegisterEmail

        emailText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                setMyButton()
            }
            override fun afterTextChanged(s: Editable) {}
        })

        passwordText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                setMyButton()
            }
            override fun afterTextChanged(s: Editable) {}
        })
        emailText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                setMyButton()
            }
            override fun afterTextChanged(s: Editable) {}
        })

        userNameText.addTextChangedListener(object : TextWatcher {
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
    private fun playAnimation() {

        val title = ObjectAnimator.ofFloat(binding.textView, View.ALPHA, 1f).setDuration(100)
        val message =
            ObjectAnimator.ofFloat(binding.textView3, View.ALPHA, 1f).setDuration(100)
        val usernameTextView = ObjectAnimator.ofFloat(binding.nameTextView, View.ALPHA, 1f).setDuration(100)
        val usernameEditTextLayout = ObjectAnimator.ofFloat(binding.edRegisterNameLayout, View.ALPHA, 1f).setDuration(100)
        val emailTextView =
            ObjectAnimator.ofFloat(binding.emailTextView, View.ALPHA, 1f).setDuration(100)
        val emailEditTextLayout =
            ObjectAnimator.ofFloat(binding.edRegisterEmailLayout, View.ALPHA, 1f).setDuration(100)
        val passwordTextView =
            ObjectAnimator.ofFloat(binding.passwordTextView, View.ALPHA, 1f).setDuration(100)
        val passwordEditTextLayout =
            ObjectAnimator.ofFloat(binding.edRegisterPasswordLayout, View.ALPHA, 1f).setDuration(100)
        val register = ObjectAnimator.ofFloat(binding.btnRegister, View.ALPHA, 1f).setDuration(100)

        AnimatorSet().apply {
            playSequentially(
                title,
                message,
                usernameTextView,
                usernameEditTextLayout,
                emailTextView,
                emailEditTextLayout,
                passwordTextView,
                passwordEditTextLayout,
                register
            )
            startDelay = 100
        }.start()
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

    private fun setMyButton() {
       val resultUsername = userNameText.text.toString().isNotEmpty()
        val resultEmail = emailText.text.toString().isNotEmpty()
        val resultPassword = passwordText.text.toString().isNotEmpty()
        binding.btnRegister.isEnabled = resultUsername && resultEmail && resultPassword
        binding.apply {
            btnRegister.setOnClickListener {
                registerUser()
            }
            btnLogin.setOnClickListener {
                val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                startActivity(intent)
            }
        }
    }
    private fun registerUser() {
        val username = userNameText.text.toString()
        val email = emailText.text.toString()
        val password = passwordText.text.toString()
        val registerRequest = RegisterRequest(username, email, password)
        viewModel.register(registerRequest).observe(this) {
            result -> if (result !=null) when (result){
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
            is Resource.Success -> {
                binding.progressBar.visibility = View.GONE
                setupAction()
            }
            is Resource.Error -> {
                binding.progressBar.visibility = View.GONE
                Resource.Error(result.error)

            }
            }
        }
    }
    private fun setupAction() {
        DialogHelper.showSuccessDialog(
            this,
            "Welcome!!",
            "Registration Successful! Let's start exploring and creating captivating stories."
        )
        val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
        startActivity(intent)
    }
}