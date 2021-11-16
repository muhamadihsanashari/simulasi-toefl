package com.fastwork.toefl.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import com.fastwork.toefl.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onBackPressed() {
        when (Navigation.findNavController(this, R.id.navHostFragment).currentDestination?.id) {
            R.id.loginFragment -> finish()
            R.id.mainFragment -> finish()
            else -> super.onBackPressed()
        }
    }

}