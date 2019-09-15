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
import androidx.activity.addCallback
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.itis2019.lecturerecorder.R
import com.itis2019.lecturerecorder.entities.Mark
import com.itis2019.lecturerecorder.service.audioPlayer.AudioPlayerService
import com.itis2019.lecturerecorder.ui.adapters.MarkAdapter
import com.itis2019.lecturerecorder.ui.base.BaseFragment
import com.itis2019.lecturerecorder.utils.dagger.injectViewModel
import com.itis2019.lecturerecorder.utils.getFromHtml
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_listening.*

class ListeningFragment : BaseFragment() {

    private val args: ListeningFragmentArgs by navArgs()

    override lateinit var viewModel: ListeningViewModel

    private lateinit var service: AudioPlayerService
    private var bound: Boolean = false
    private var isInitialState = true

    private val connection = object : ServiceConnection {

        override fun onServiceConnected(className: ComponentName, _service: IBinder) {
            val binder = _service as AudioPlayerService.AudioPlayerBinder
            service = binder.getService()
            bound = true
            viewModel.setDataSource(service.getCurrentListeningTime())
            initObservers()
        }

        override fun onServiceDisconnected(arg0: ComponentName) {
            bound = false
        }
    }

    override fun initViewModel() {
        AndroidSupportInjection.inject(this)
        viewModel = injectViewModel(viewModelFactory)
        viewModel.loadRecord(args.lectureId)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.run {
            Intent(this, AudioPlayerService::class.java).also { intent ->
                bindService(intent, connection, Context.BIND_AUTO_CREATE)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            if (bound) unbindService()
            findNavController(this@ListeningFragment).popBackStack()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (bound) unbindService()
    }

    private fun unbindService() {
        activity?.run {
            unbindService(connection)
            stopService(Intent(activity, AudioPlayerService::class.java))
        }
        bound = false
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(R.layout.fragment_listening, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycler()
        initListeners()
    }

    override fun initObservers() {
        if (!bound) return
        observeLoading(progress_bar)
        observeRecord()
        observeMarkList()
        observeSeekWithTimecode()
        observeIsPlaying()
    }

    private fun observeRecord() =
        viewModel.getLecture().observe(this, Observer {
            service.setDataSource(it.filePath)
            seek_bar.max = service.getDuration()
            blast.setAudioSessionId(service.getAudioSessionId())
            tv_record_name.text =
                activity?.getFromHtml(R.string.now_playing_record_name, it.name.toUpperCase())
        })

    private fun observeIsPlaying() =
        viewModel.isPlaying().observe(this, Observer {
            btn_play_pause.setImageDrawable(
                if (it) activity?.getDrawable(R.drawable.ic_pause_24dp)
                else activity?.getDrawable(R.drawable.ic_play_24dp)
            )
            if (isInitialState) {
                activity?.run {
                    startService(Intent(this, AudioPlayerService::class.java))
                }
                observeCurrentTime()
                isInitialState = false
                service.play()
                return@Observer
            }
            if (it) service.play()
            else service.pause()
        })

    private fun observeCurrentTime() =
        viewModel.getCurrentData().observe(this, Observer {
            seek_bar.progress = it
        })

    private fun observeMarkList() =
        viewModel.getMarks().observe(this, Observer {
            (rv_marks.adapter as MarkAdapter).submitList(it)
        })

    private fun observeSeekWithTimecode() =
        viewModel.seekWithTimecode.observe(this, Observer { time ->
            time?.let { service.seekTo(time) }
        })

    private fun initRecycler() {
        rv_marks.layoutManager = LinearLayoutManager(activity)
        rv_marks.adapter =
            MarkAdapter(clickListener = { mark: Mark -> viewModel.markItemClicked(mark.time) })
//        val itemDecoration = DividerItemDecoration(activity, DividerItemDecoration.VERTICAL)
//        rv_marks.addItemDecoration(itemDecoration)
    }

    private fun initListeners() {
        btn_play_pause.setOnClickListener { viewModel.playPauseBtnClicked() }
        val paddingTop = tv_record_name.paddingTop
        tv_record_name.setOnApplyWindowInsetsListener { v, insets ->
            v.setPadding(
                v.paddingStart,
                paddingTop + insets.systemWindowInsetTop,
                v.paddingEnd,
                v.paddingBottom
            )
            insets
        }
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
