package com.itis2019.lecturerecorder.ui

import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.itis2019.lecturerecorder.R
import com.itis2019.lecturerecorder.utils.dagger.DiActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : DiActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.MarkRecorderTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        this.window?.apply {
            setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                decorView.systemUiVisibility =
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            } else decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }

        navController = Navigation.findNavController(this, R.id.host_fragment)
        NavigationUI.setupWithNavController(bottom_nav, navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.navigation_lectures -> showBottomNav()
                R.id.navigation_folders -> showBottomNav()
                R.id.navigation_settings -> showBottomNav()
                else -> hideBottomNav()
            }
        }
    }

    private fun showBottomNav() = with(bottom_nav) { visibility = View.VISIBLE }

    private fun hideBottomNav() = with(bottom_nav) { visibility = View.GONE }
}
