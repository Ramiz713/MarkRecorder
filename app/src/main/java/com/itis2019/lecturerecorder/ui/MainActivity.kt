package com.itis2019.lecturerecorder.ui

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.itis2019.lecturerecorder.R
import com.itis2019.lecturerecorder.utils.dagger.DiActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : DiActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.LightTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navController = Navigation.findNavController(this, R.id.host_fragment)
        NavigationUI.setupWithNavController(bnv_navigation, navController)
    }
}
