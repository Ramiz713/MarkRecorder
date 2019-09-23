package com.itis2019.markrecorder.router

import androidx.fragment.app.Fragment

interface Router {

    fun openRecordingFragment(fragment: Fragment)

    fun openRecord(fragment: Fragment, id: Long)

    fun openFolder(fragment: Fragment, id: Long)

    fun openRecordFromFolder(fragment: Fragment, id: Long)
}
