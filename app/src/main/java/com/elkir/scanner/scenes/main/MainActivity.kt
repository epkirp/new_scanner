package com.elkir.scanner.scenes.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.elkir.scanner.R
import com.elkir.scanner.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private val currentNavController by lazy { findNavController(R.id.mainNavContainer) }
    private val containers = hashMapOf(
        BOTTOM_NAVIGATION_CONTAINER to R.id.bottomNavigationContainer
    )
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onSupportNavigateUp(): Boolean {
        return currentNavController.navigateUp()
    }

    fun openBottomNavigationFragment() {
        containers.forEach {
            currentNavController.popBackStack(it.value, true)
        }

        currentNavController.navigate(R.id.bottomNavigationContainer)
    }


    companion object {
        private const val BOTTOM_NAVIGATION_CONTAINER = "BottomNavigationContainer"
    }
}