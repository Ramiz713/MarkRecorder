package com.itis2019.lecturerecorder.ui.recorder.recording

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.itis2019.lecturerecorder.R
import com.itis2019.lecturerecorder.entities.Lecture
import com.itis2019.lecturerecorder.service.AudioRecording.AudioRecordService
import com.itis2019.lecturerecorder.ui.adapters.MarkAdapter
import com.itis2019.lecturerecorder.ui.base.BaseFragment
import com.itis2019.lecturerecorder.utils.dagger.injectViewModel
import com.itis2019.lecturerecorder.utils.getTimeInFormatWithSeconds
import com.yalantis.waves.util.Horizon
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.recording_fragment.*
import java.util.Calendar

class RecordingFragment : BaseFragment() {

    private lateinit var service: AudioRecordService
    private var bound: Boolean = false
    private var isInitialState = true
    private var totalTime: Long = 0
    private var lectureId: Long = 0

    private val connection = object : ServiceConnection {

        override fun onServiceConnected(className: ComponentName, _service: IBinder) {
            val binder = _service as AudioRecordService.AudioRecordBinder
            service = binder.getService()
            bound = true
        }

        override fun onServiceDisconnected(arg0: ComponentName) {
            bound = false
        }
    }

    override lateinit var viewModel: RecordingViewModel
    private lateinit var horizon: Horizon

    override fun initViewModel() {
        AndroidSupportInjection.inject(this)
        viewModel = injectViewModel(viewModelFactory)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.recording_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        horizon = Horizon(visualizer, resources.getColor(R.color.black), 44100, 1, 16)
        btn_stop.isEnabled = false
        btn_mark.isEnabled = false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Intent(activity, AudioRecordService::class.java).also { intent ->
            activity?.run { bindService(intent, connection, Context.BIND_AUTO_CREATE) }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        activity?.run {
            unbindService(connection)
            stopService(Intent(activity, AudioRecordService::class.java))
        }
        bound = false
    }

    override fun initObservers(view: View) {
        observeIsPlaying()
        observeNavigateToLectureConfig()
        observeInsertLecture()
        observeMarkCreation()
        observeMarkList()
        btn_play_pause.setOnClickListener { viewModel.playPauseBtnClicked(btn_play_pause.isPlay) }
        btn_stop.setOnClickListener { viewModel.stopBtnClicked() }
        btn_mark.setOnClickListener { viewModel.markBtnClicked() }
        initRecycler()
    }

    private fun observeIsPlaying() =
        viewModel.isPlaying().observe(this, Observer {
            btn_play_pause.change(it, true)
            if (isInitialState) {
                initFlowableData()
                service.startRecord()
                btn_stop.isEnabled = true
                btn_mark.isEnabled = true
                isInitialState = false
                return@Observer
            }
            if (it) service.pauseRecord()
            else service.resumeRecord()
        })

    private fun initFlowableData() {
        viewModel.setDataSource(service.getRawBytes(), service.getTime())
        activity?.startService(Intent(activity, AudioRecordService::class.java))
        observeFetchingRawBytesData()
        observeFetchingChronometerData()
    }

    private fun observeNavigateToLectureConfig() =
        viewModel.navigateToLectureConfig.observe(this, Observer {
            val path = service.finishRecordWithSaving()
            activity?.run {
                unbindService(connection)
                stopService(Intent(activity, AudioRecordService::class.java))
            }
            val lecture = Lecture(
                id = lectureId,
                duration = totalTime,
                creationDate = Calendar.getInstance().time,
                filePath = path)
            val action = RecordingFragmentDirections.actionRecordingFragmentToLectureConfigFragment(lecture)
            findNavController(this).navigate(action)
        })

    private fun observeInsertLecture() {
        viewModel.insertLecture().observe(this, Observer { lectureId = it })
    }

    private fun observeMarkCreation() =
        viewModel.showMarkCreationDialog.observe(this, Observer {
            fragmentManager?.let {
                MarkCreationDialog.newInstance(totalTime, lectureId).show(it, "MarkCreationDialog")
            }
        })

    private fun observeMarkList() =
        viewModel.fetchMarks().observe(viewLifecycleOwner, Observer {
            (rv_marks.adapter as MarkAdapter).submitList(it) }
        )

    private fun observeFetchingRawBytesData() =
        viewModel.fetchRawBytes().observe(this, Observer { horizon.updateView(it) })

    private fun observeFetchingChronometerData() =
        viewModel.fetchChronometerData().observe(this, Observer {
            tv_chronometer.text = getTimeInFormatWithSeconds(it)
            totalTime = it
        })

    private fun initRecycler() {
        rv_marks.layoutManager = LinearLayoutManager(activity)
        rv_marks.adapter = MarkAdapter {}
        val itemDecoration = DividerItemDecoration(activity, DividerItemDecoration.VERTICAL)
        rv_marks.addItemDecoration(itemDecoration)
    }
}
