package com.itis2019.lecturerecorder.ui

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
        setTheme(R.style.LectureRecorderTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        this.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)

        navController = Navigation.findNavController(this, R.id.host_fragment)
        NavigationUI.setupWithNavController(bottom_nav, navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.navigation_lectures -> {
                    showBottomNav()
                    hidePlus()
                    showExtend()
                }
                R.id.navigation_folders -> {
                    showBottomNav()
                    hideExtend()
                    showPlus()
                }
                R.id.navigation_settings -> {
                    showBottomNav()
                    hideExtend()
                    hidePlus()
                }
                else -> {
                    hideBottomNav()
                    hideExtend()
                    hidePlus()
                }
            }
        }
    }

    private fun showBottomNav() = with(bottom_nav) { visibility = View.VISIBLE }

    private fun hideBottomNav() = with(bottom_nav) { visibility = View.GONE }

    private fun showExtend() = with(record_lecture_button) {
        show(true)
        extend(true)
    }

    private fun hideExtend() = with(record_lecture_button) {
        shrink(true)
        hide(true)
    }

    private fun showPlus() = with(add_folder_button) { show() }
    private fun hidePlus() = with(add_folder_button) { hide() }

    fun setOnClickListenerToRecordLectureButton(function: () -> Unit) =
        record_lecture_button.setOnClickListener { function() }

    fun setOnClickListenerToAddFolderButton(function: () -> Unit) =
        add_folder_button.setOnClickListener { function() }

    fun shrinkRecordLectureButton() = record_lecture_button.shrink(true)

    fun extendRecordLectureButton() = record_lecture_button.extend(true)
}
