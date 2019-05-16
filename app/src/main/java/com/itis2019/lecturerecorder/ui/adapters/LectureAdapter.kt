package com.itis2019.lecturerecorder.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.core.text.HtmlCompat.FROM_HTML_MODE_LEGACY
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.itis2019.lecturerecorder.R
import com.itis2019.lecturerecorder.entities.Lecture
import com.itis2019.lecturerecorder.utils.getTimeInFormatWithSeconds
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_lecture.*
import java.text.DateFormat

class LectureAdapter(private val listener: (Lecture) -> Unit) : ListAdapter<Lecture, LectureAdapter.LectureHolder>(DIFF_CALLBACK) {

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
        holder.itemView.setOnClickListener { listener(item) }
    }

    class LectureHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bind(item: Lecture) =
            with(item) {
                val context = containerView.context
                card_view.background = context.getDrawable(folderBackground)
                val date = DateFormat.getDateInstance().format(creationDate)

                val getFromHtml = { stringId: Int, string: String ->
                    HtmlCompat.fromHtml(context.getString(stringId, string), FROM_HTML_MODE_LEGACY)
                }
                tv_lecture_name.text = getFromHtml(R.string.lecture_topic, name)
                tv_subject.text = getFromHtml(R.string.subject, folderName)
                tv_date.text = getFromHtml(R.string.date, date)
                tv_duration.text = getFromHtml(R.string.duration, getTimeInFormatWithSeconds(duration))
            }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Lecture>() {

            override fun areItemsTheSame(oldItem: Lecture, newItem: Lecture): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Lecture, newItem: Lecture): Boolean =
                oldItem == newItem
        }
    }
}
