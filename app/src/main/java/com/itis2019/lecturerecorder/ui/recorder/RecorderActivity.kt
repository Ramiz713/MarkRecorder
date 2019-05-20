package com.itis2019.lecturerecorder.ui.recorder

import android.os.Bundle
import com.itis2019.lecturerecorder.R
import com.itis2019.lecturerecorder.ui.recorder.recording.RecordingFragment
import com.itis2019.lecturerecorder.utils.dagger.DiActivity

class RecorderActivity : DiActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recorder)
    }

    override fun onBackPressed() {
        finish()
        super.onBackPressed()
    }
}
