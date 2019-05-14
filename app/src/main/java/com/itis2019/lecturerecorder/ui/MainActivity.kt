package com.itis2019.lecturerecorder.ui

import android.Manifest
import android.os.Bundle
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.itis2019.lecturerecorder.R
import com.itis2019.lecturerecorder.utils.dagger.DiActivity
import com.livinglifetechway.quickpermissions_kotlin.runWithPermissions
import kotlinx.android.synthetic.main.activity_main.*
//import pub.devrel.easypermissions.AfterPermissionGranted
//import pub.devrel.easypermissions.AppSettingsDialog
//import pub.devrel.easypermissions.EasyPermissions
//import pub.devrel.easypermissions.PermissionRequest

class MainActivity : DiActivity() {

    companion object {
        private const val RC_AUDIO_AND_STORAGE = 123
    }

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.LightTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        checkNecassaryPermissions()
        navController = Navigation.findNavController(this, R.id.host_fragment)
        NavigationUI.setupWithNavController(bnv_navigation, navController)
    }

//    @AfterPermissionGranted(RC_AUDIO_AND_STORAGE)
//    private fun checkNecassaryPermissions() {
//        val perms = arrayOf(Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE)
//        if (EasyPermissions.hasPermissions(this, *perms)) {
//            return
//        } else {
//            EasyPermissions.requestPermissions(
//                PermissionRequest.Builder(this, RC_AUDIO_AND_STORAGE, *perms)
//                    .setRationale(R.string.permissons_required)
//                    .setPositiveButtonText(R.string.ok)
//                    .setNegativeButtonText(R.string.cancel)
//                    .setTheme(R.style.DialogTheme)
//                    .build())
//        }
//    }

//    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
//    }
//
//    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
//        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms))
//            AppSettingsDialog.Builder(this).build().show()
//    }
//
//    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
//        Toast.makeText(this@MainActivity, "Succes!", Toast.LENGTH_SHORT ).show()
//    }
}
