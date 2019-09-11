package com.itis2019.lecturerecorder.router

import androidx.fragment.app.Fragment
import com.itis2019.lecturerecorder.entities.Record

interface Router {

    fun openRecordingFragment(fragment: Fragment)

    fun openRecord(fragment: Fragment, id: Long)

    fun openFolder(fragment: Fragment, id: Long)

    fun openRecordFromFolder(fragment: Fragment, id: Long)

    fun openLectureConfiguration(fragment: Fragment, lecture: Record)
}
