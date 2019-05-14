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
import com.itis2019.lecturerecorder.R
import com.itis2019.lecturerecorder.model.Lecture
import com.itis2019.lecturerecorder.service.AudioRecordService
import com.itis2019.lecturerecorder.ui.base.BaseFragment
import com.itis2019.lecturerecorder.utils.dagger.injectViewModel
import dagger.android.support.AndroidSupportInjection
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.recording_fragment.*
import kotlinx.coroutines.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import java.util.*

class RecordingFragment : BaseFragment(), CoroutineScope by MainScope() {

    private lateinit var service: AudioRecordService
    private var bound: Boolean = false

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

    override fun initViewModel() {
        AndroidSupportInjection.inject(this)
        viewModel = injectViewModel(viewModelFactory)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.recording_fragment, container, false)

    //логика пока не вынесена во viewModel
    private val disposable = CompositeDisposable()

    override fun initObservers(view: View) {
        observeIsPlaying()
        observeNavigateToLectureConfig()
        btn_play_pause.setOnClickListener { viewModel.playPauseBtnClicked(btn_play_pause.isPlay) }
        btn_stop.setOnClickListener { viewModel.stopBtnClicked() }
    }

    private fun observeIsPlaying() =
        viewModel.isPlaying().observe(this, Observer {
            btn_play_pause.change(it, true)
            activity?.startService(Intent(activity, AudioRecordService::class.java))
            service.startRecord()

            disposable.add(service.getRawBytes()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { visualizer.setRawAudioBytes(it) })
        })

    private fun observeNavigateToLectureConfig() =
        viewModel.navigateToLectureConfig.observe(this, Observer {
            disposable.clear()
            val path = service.finishRecord()
            activity?.run {
                unbindService(connection)
                stopService(Intent(activity, AudioRecordService::class.java))
            }
            val lecture = Lecture(0, "", 0, Calendar.getInstance().time, path, "default", 0, 0)
            val action = RecordingFragmentDirections.actionRecordingFragmentToLectureConfigFragment()
            findNavController(this).navigate(action)
        })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Intent(activity, AudioRecordService::class.java).also { intent ->
            activity?.run {
                bindService(intent, connection, Context.BIND_AUTO_CREATE)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.dispose()
        activity?.run {
            unbindService(connection)
            stopService(Intent(activity, AudioRecordService::class.java))
        }
        bound = false
    }
}
