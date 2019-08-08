package com.itis2019.lecturerecorder.ui.listener

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.itis2019.lecturerecorder.R
import com.itis2019.lecturerecorder.service.AudioPlayer.AudioPlayerService
import com.itis2019.lecturerecorder.ui.adapters.MarkAdapter
import com.itis2019.lecturerecorder.ui.base.BaseFragment
import com.itis2019.lecturerecorder.utils.dagger.injectViewModel
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_listening.*

class ListeningFragment : BaseFragment() {

    private val args: ListeningFragmentArgs by navArgs()

    override lateinit var viewModel: ListeningViewModel

    private lateinit var service: AudioPlayerService
    private var bound: Boolean = false
    private lateinit var filePath: String
    private var isInitialState = true

    private val connection = object : ServiceConnection {

        override fun onServiceConnected(className: ComponentName, _service: IBinder) {
            val binder = _service as AudioPlayerService.AudioPlayerBinder
            service = binder.getService()
            bound = true
            viewModel.setDataSource(service.getCurrentListeningTime())
        }

        override fun onServiceDisconnected(arg0: ComponentName) {
            bound = false
        }
    }

    override fun initViewModel() {
        AndroidSupportInjection.inject(this)
        viewModel = injectViewModel(viewModelFactory)
    }

    override fun onStart() {
        super.onStart()
        activity?.run {
            Intent(this, AudioPlayerService::class.java).also { intent ->
                bindService(intent, connection, Context.BIND_AUTO_CREATE)
            }
        }
    }

    override fun onStop() {
        super.onStop()
        if (bound)
            unbindService()
    }

    private fun unbindService() {
        activity?.run {
            unbindService(connection)
            stopService(Intent(activity, AudioPlayerService::class.java))
        }
        bound = false
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_listening, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = injectViewModel(viewModelFactory)
        val lecture = args.lecture
        filePath = lecture.filePath
        initRecycler()
        initListeners()
    }

    override fun initObservers() {
        observeLoadingview(progress_bar)
        observeMarkList()
        observeSeekWithTimecode()
        observeIsPlaying()
    }

    private fun observeIsPlaying() =
        viewModel.isPlaying().observe(this, Observer {
            btn_play_pause.change(it, true)
            if (isInitialState) {
                service.playSong(filePath)
                seek_bar.max = service.getDuration()
                activity?.run {
                    startService(Intent(this, AudioPlayerService::class.java))
                }
                observeCurrentTime()
                isInitialState = false
                return@Observer
            }
            if (it) service.pause()
            else service.play()
        })

    private fun observeCurrentTime() =
        viewModel.fetchCurrentData().observe(this, Observer {
            seek_bar.progress = it
        })

    private fun observeMarkList() =
        viewModel.fetchMarks(args.lecture.id).observe(this, Observer {
            (rv_marks.adapter as MarkAdapter).submitList(it)
        })

    private fun observeSeekWithTimecode() =
        viewModel.seekWithTimecode.observe(this, Observer { time ->
            time?.let { service.seekTo(time) }
        })

    private fun observeLoadingview(view: View) =
        viewModel.isLoading().observe(this, Observer {
            view.visibility = if (it) View.VISIBLE else View.GONE
        })

    private fun initRecycler() {
        rv_marks.layoutManager = LinearLayoutManager(activity)
        rv_marks.adapter = MarkAdapter { mark -> viewModel.markItemClicked(mark.time) }
        val itemDecoration = DividerItemDecoration(activity, DividerItemDecoration.VERTICAL)
        rv_marks.addItemDecoration(itemDecoration)
    }

    private fun initListeners() {
        btn_play_pause.setOnClickListener { viewModel.playPauseBtnClicked(btn_play_pause.isPlay) }
        seek_bar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    service.seekTo(progress.toLong())
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}

            override fun onStopTrackingTouch(seekBar: SeekBar) {
            }
        })
    }
}
