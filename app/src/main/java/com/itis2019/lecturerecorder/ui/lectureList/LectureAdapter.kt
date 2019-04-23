package com.itis2019.lecturerecorder.ui.lectureList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.core.text.HtmlCompat.FROM_HTML_MODE_LEGACY
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.itis2019.lecturerecorder.R
import com.itis2019.lecturerecorder.model.Lecture
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_lecture.*
import java.text.DateFormat

class LectureAdapter(private val listener: (Int) -> Unit) :
        ListAdapter<Lecture, LectureAdapter.LectureHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LectureHolder =
            LectureHolder(
                    LayoutInflater.from(parent.context).inflate(
                            R.layout.item_lecture,
                            parent,
                            false
                    )
            )

    override fun onBindViewHolder(holder: LectureHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
        holder.itemView.setOnClickListener { listener(position) }
    }

    class LectureHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bind(item: Lecture): Unit = with(item) {
            val context = containerView.context
            card_view.background = context.getDrawable(R.drawable.gradient_green)
            val date = DateFormat.getDateInstance().format(creationDate)
            tv_lecture_name.text = HtmlCompat.fromHtml(context.getString(R.string.lecture_topic, name), FROM_HTML_MODE_LEGACY)
            tv_subject.text = HtmlCompat.fromHtml(context.getString(R.string.subject, folderName), FROM_HTML_MODE_LEGACY)
            tv_date.text = HtmlCompat.fromHtml(context.getString(R.string.date, date), FROM_HTML_MODE_LEGACY)
            tv_duration.text = HtmlCompat.fromHtml(context.getString(R.string.duration, "1 hour 15 minutes"), FROM_HTML_MODE_LEGACY)
        }
    }

    companion object {
        private val DIFF_CALLBACK = object :
                DiffUtil.ItemCallback<Lecture>() {

            override fun areItemsTheSame(oldItem: Lecture, newItem: Lecture): Boolean =
                    oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Lecture, newItem: Lecture): Boolean =
                    oldItem == newItem
        }
    }
}

