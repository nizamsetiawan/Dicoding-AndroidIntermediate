package com.example.storysnap

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.storysnap.databinding.ActivityMainBinding
import com.example.storysnap.ui.auth.AuthViewModel
import com.example.storysnap.ui.auth.WelcomeActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: AuthViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Thread.sleep(3000)
        installSplashScreen()


        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel.getSession().observe(this) { user ->
            if (user.isLogin) {
                val navController = findNavController(R.id.nav_host_fragment_activity_main)
                navController.navigate(R.id.navigation_home)
            } else {
                startActivity(Intent(this, WelcomeActivity::class.java))
                finish()
            }
        }

        val navView: BottomNavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_story, R.id.navigation_setting
            )
        )
        navView.setupWithNavController(navController)
    }
}
