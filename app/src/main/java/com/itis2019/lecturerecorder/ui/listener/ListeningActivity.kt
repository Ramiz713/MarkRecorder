package com.itis2019.lecturerecorder.ui.listener

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.view.View
import android.widget.SeekBar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.itis2019.lecturerecorder.R
import com.itis2019.lecturerecorder.service.AudioPlayer.AudioPlayerService
import com.itis2019.lecturerecorder.ui.adapters.MarkAdapter
import com.itis2019.lecturerecorder.utils.dagger.DiActivity
import com.itis2019.lecturerecorder.utils.dagger.injectViewModel
import kotlinx.android.synthetic.main.activity_listening.*
import kotlinx.android.synthetic.main.activity_listening.btn_play_pause
import kotlinx.android.synthetic.main.activity_listening.rv_marks
import javax.inject.Inject

class ListeningActivity : DiActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: ListeningViewModel

    private val args: ListeningActivityArgs by navArgs()

    private lateinit var service: AudioPlayerService
    private var bound: Boolean = false
    private var lectureId: Long = 0
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listening)
        viewModel = injectViewModel(viewModelFactory)
        val lecture = args.lecture
        lectureId = lecture.id
        filePath = lecture.filePath
        Intent(this, AudioPlayerService::class.java).also { intent ->
            bindService(intent, connection, Context.BIND_AUTO_CREATE)
        }
        initObservers()
        initRecycler()
        initListeners()
    }

    override fun onDestroy() {
        super.onDestroy()
        service.stop()
        unbindService(connection)
        stopService(Intent(this, AudioPlayerService::class.java))
    }

    private fun initObservers() {
        observeLoadingview(progress_bar)
//        observeError()
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
                startService(Intent(this, AudioPlayerService::class.java))
                observeCurrentTime()
                isInitialState = false
                return@Observer
            }
            if (it) {
                service.pause()
            } else {
                service.play()
            }
        })

    private fun observeCurrentTime() =
        viewModel.fetchCurrentData().observe(this, Observer {
            seek_bar.progress = it
        })

    private fun observeMarkList() =
        viewModel.fetchMarks(lectureId).observe(this, Observer {
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

    private fun observeError(view: View) =
        viewModel.error().observe(this, Observer {
            Snackbar.make(view, it.localizedMessage, Snackbar.LENGTH_SHORT).show()
        })

    private fun initRecycler() {
        rv_marks.layoutManager = LinearLayoutManager(this)
        rv_marks.adapter = MarkAdapter { mark -> viewModel.markItemClicked(mark.time) }
        val itemDecoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
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
