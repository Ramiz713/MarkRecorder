package com.itis2019.lecturerecorder.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.core.text.HtmlCompat.FROM_HTML_MODE_LEGACY
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.itis2019.lecturerecorder.R
import com.itis2019.lecturerecorder.entities.Lecture
import com.itis2019.lecturerecorder.utils.getTimeInFormatWithSeconds
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_header.*
import kotlinx.android.synthetic.main.item_lecture.*
import java.text.DateFormat

private val ITEM_VIEW_TYPE_HEADER = 0
private val ITEM_VIEW_TYPE_ITEM = 1

class LectureAdapter(private val listener: (Lecture) -> Unit)
    : ListAdapter<LectureDataItem, RecyclerView.ViewHolder>(DIFF_CALLBACK) {

    @Suppress("CheckResult")
    fun addHeaderAndSubmitList(list: List<Lecture>?, title: String = "", subtitle: String = "") {
        Observable.just(list)
            .map {
                when (it) {
                    null -> listOf(LectureDataItem.Header(title, subtitle))
                    else -> listOf(LectureDataItem.Header(title, subtitle)) + it.map { LectureDataItem.LectureItem(it) }
                }
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { submitList(it) }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is LectureDataItem.Header -> ITEM_VIEW_TYPE_HEADER
            is LectureDataItem.LectureItem -> ITEM_VIEW_TYPE_ITEM
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (viewType) {
            ITEM_VIEW_TYPE_HEADER -> HeaderHolder.from(parent)
            ITEM_VIEW_TYPE_ITEM -> LectureHolder.from(parent)
            else -> throw ClassCastException("Unknown viewType $viewType")
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) =
        when (holder) {
            is LectureHolder -> {
                val item = getItem(position) as LectureDataItem.LectureItem
                holder.bind(item.lecture)
                holder.itemView.setOnClickListener { listener(item.lecture) }
            }
            is HeaderHolder -> {
                val item = getItem(position) as LectureDataItem.Header
                holder.bind(item)
            }
            else -> {
            }
        }

    class LectureHolder(override val containerView: View)
        : RecyclerView.ViewHolder(containerView), LayoutContainer {

        companion object {
            fun from(parent: ViewGroup): LectureHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater.inflate(R.layout.item_lecture, parent, false)
                return LectureHolder(view)
            }
        }

        fun bind(item: Lecture) =
            with(item) {
                val context = containerView.context
                try {
                    card_view.background = context.getDrawable(folderBackground)
                } catch (ex: Exception) {
                }
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

    class HeaderHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {
        companion object {
            fun from(parent: ViewGroup): HeaderHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater.inflate(R.layout.item_header, parent, false)
                return HeaderHolder(view)
            }
        }

        fun bind(item: LectureDataItem.Header) =
            with(item) {
                tv_title.text = title
                if (subtitle.isNotEmpty())
                    tv_subtitle.text = subtitle
                else tv_subtitle.visibility = View.GONE
            }
    }


    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<LectureDataItem>() {

            override fun areItemsTheSame(oldItem: LectureDataItem, newItem: LectureDataItem): Boolean =
                oldItem.id == newItem.id

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldItem: LectureDataItem, newItem: LectureDataItem): Boolean =
                oldItem == newItem
        }
    }
}

sealed class LectureDataItem {
    abstract val id: Long

    data class LectureItem(val lecture: Lecture) : LectureDataItem() {
        override val id: Long = lecture.id
    }

    data class Header(val title: String, val subtitle: String) : LectureDataItem() {
        override val id = Long.MIN_VALUE
    }
}
