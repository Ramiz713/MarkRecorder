package com.itis2019.lecturerecorder.ui.lectureList


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.itis2019.lecturerecorder.R
import com.itis2019.lecturerecorder.utils.provideViewModel
import kotlinx.android.synthetic.main.fragment_lecture_list.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein


class LectureListFragment : Fragment(), KodeinAware {

    override val kodein: Kodein by closestKodein()

    private val viewModel: LectureListViewModel by provideViewModel()

    private val adapter = LectureAdapter { position: Int ->
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_lecture_list, container, false)
        initRecycler(view)
        observeLectureList(view)
        observeLoading()
        return view

    }

    private fun observeLoading() {
        viewModel.isLoading().observe(this, Observer {
            progress_bar.visibility = if (it) View.VISIBLE else View.GONE
        })
    }

    private fun observeLectureList(view: View) {
        viewModel.onLoadLectures().observe(this, Observer {
            when {
                it?.data != null -> adapter.submitList(it.data)
                it?.error != null -> Snackbar.make(view, it.error.localizedMessage, Snackbar.LENGTH_SHORT).show()
                else -> Snackbar.make(view, "We have problem!!!", Snackbar.LENGTH_SHORT).show()
            }
        })
    }

    private fun initRecycler(view: View) {
        val manager = LinearLayoutManager(activity)
        val recyclerView = view.findViewById<RecyclerView>(R.id.rv_lectures)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = manager
    }
}
