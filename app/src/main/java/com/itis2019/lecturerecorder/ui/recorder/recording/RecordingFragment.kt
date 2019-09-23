package com.itis2019.lecturerecorder.ui.recorder.recording

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.graphics.Color
import android.os.Bundle
import android.os.IBinder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.itis2019.lecturerecorder.R
import com.itis2019.lecturerecorder.entities.Mark
import com.itis2019.lecturerecorder.entities.Record
import com.itis2019.lecturerecorder.service.audioRecording.AudioRecordService
import com.itis2019.lecturerecorder.ui.adapters.MarkAdapter
import com.itis2019.lecturerecorder.ui.base.BaseFragment
import com.itis2019.lecturerecorder.utils.dagger.injectViewModel
import com.itis2019.lecturerecorder.utils.getTimeInFormatWithSeconds
import com.yalantis.waves.util.Horizon
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_recording.*
import java.util.*

class RecordingFragment : BaseFragment() {

    private lateinit var service: AudioRecordService
    private var bound: Boolean = false
    private var isInitialState = true
    private var previousStatusBarColor = 0

    private val connection = object : ServiceConnection {

        override fun onServiceConnected(className: ComponentName, _service: IBinder) {
            val binder = _service as AudioRecordService.AudioRecordBinder
            service = binder.getService()
            bound = true
            initObservers()
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
    ): View? = inflater.inflate(R.layout.fragment_recording, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        horizon = Horizon(visualizer, resources.getColor(R.color.colorText), 44100, 1, 16)
        disableButtons()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Intent(activity, AudioRecordService::class.java).also { intent ->
            activity?.run { bindService(intent, connection, Context.BIND_AUTO_CREATE) }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            if (bound) unbindService()
            findNavController(this@RecordingFragment).popBackStack()
        }
    }

    override fun onStart() {
        super.onStart()
        activity?.window?.apply {
            previousStatusBarColor = statusBarColor
            decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            statusBarColor = Color.TRANSPARENT
        }
    }

    override fun onStop() {
        super.onStop()
        activity?.window?.apply {
            decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            statusBarColor = previousStatusBarColor
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (bound) unbindService()
    }

    private fun unbindService() {
        activity?.run {
            unbindService(connection)
            stopService(Intent(activity, AudioRecordService::class.java))
        }
        bound = false
    }

    override fun initObservers() {
        if (!bound) return
        observeIsPlaying()
        observeNavigateToLectureConfig()
        observeMarkRename()
        observeMarkList()
        btn_play_pause.setOnClickListener { viewModel.playPauseBtnClicked() }
        btn_stop.setOnClickListener { viewModel.stopBtnClicked() }
        btn_mark.setOnClickListener { viewModel.insertMark() }
        initRecycler()
    }

    private fun observeIsPlaying() =
        viewModel.isPlaying().observe(viewLifecycleOwner, Observer {
            btn_play_pause.setImageDrawable(
                if (it) activity?.getDrawable(R.drawable.ic_pause_24dp)
                else activity?.getDrawable(R.drawable.ic_record)
            )
            if (isInitialState) {
                initFlowableData()
                service.startRecord()
                enableButtons()
                isInitialState = false
                return@Observer
            }
            if (it) service.resumeRecord()
            else service.pauseRecord()
        })

    private fun initFlowableData() {
        viewModel.setDataSource(service.getRawBytes(), service.getTime())
        activity?.startService(Intent(activity, AudioRecordService::class.java))
        observeFetchingRawBytesData()
        observeFetchingChronometerData()
    }

    private fun observeNavigateToLectureConfig() =
        viewModel.navigateToLectureConfig.observe(viewLifecycleOwner, Observer {
            val path = service.finishRecordWithSaving()
            val lecture = Record(
                id = 0,
                duration = viewModel.getChronometerData().value ?: 0L,
                marks = viewModel.getMarks().value ?: listOf(),
                creationDate = Calendar.getInstance().time,
                filePath = path
            )
            val action =
                RecordingFragmentDirections.actionRecordingFragmentToLectureConfigFragment(lecture)
            unbindService()
            findNavController(this).navigate(action)
        })

    private fun observeMarkRename() =
        viewModel.showMarkRenameDialog.observe(viewLifecycleOwner, Observer { mark ->
            mark?.let {
                fragmentManager?.let {
                    MarkRenameDialog.newInstance(mark)
                        .show(childFragmentManager, getString(R.string.mark_name_edit))
                }
            }
        })

    private fun observeMarkList() =
        viewModel.getMarks().observe(viewLifecycleOwner, Observer {
            (rv_marks.adapter as MarkAdapter).run {
                submitList(it.reversed())
            }
        })

    private fun observeFetchingRawBytesData() =
        viewModel.getRawBytes().observe(viewLifecycleOwner, Observer { horizon.updateView(it) })

    private fun observeFetchingChronometerData() =
        viewModel.getChronometerData().observe(viewLifecycleOwner, Observer {
            tv_chronometer.text = getTimeInFormatWithSeconds(it)
        })

    private fun initRecycler() {
        rv_marks.layoutManager = LinearLayoutManager(activity)
        val editListener = { mark: Mark -> viewModel.markEditClicked(mark) }
        val deleteListener = { mark: Mark -> viewModel.deleteMark(mark) }
        rv_marks.adapter = MarkAdapter(editListener = editListener, deleteListener = deleteListener)
    }

    private fun enableButtons() {
        btn_stop.run {
            isEnabled = true
            alpha = 1f
        }
        btn_mark.run {
            isEnabled = true
            alpha = 1f
        }
    }

    private fun disableButtons() {
        btn_stop.run {
            isEnabled = false
            alpha = 0.5f
        }
        btn_mark.run {
            isEnabled = false
            alpha = 0.5f
        }
    }
}
