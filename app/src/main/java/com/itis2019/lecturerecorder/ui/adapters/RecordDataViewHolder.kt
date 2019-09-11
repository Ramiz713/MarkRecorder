package com.itis2019.lecturerecorder.ui.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.itis2019.lecturerecorder.R
import com.itis2019.lecturerecorder.entities.Folder
import com.itis2019.lecturerecorder.entities.Record
import com.itis2019.lecturerecorder.utils.getFromHtml
import com.itis2019.lecturerecorder.utils.getTimeInFormatWithSeconds
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_folder.*
import kotlinx.android.synthetic.main.item_header.*
import kotlinx.android.synthetic.main.item_record.*
import kotlinx.android.synthetic.main.item_record.card_view
import java.text.DateFormat

sealed class RecordDataViewHolder(override val containerView: View) :
    RecyclerView.ViewHolder(containerView), LayoutContainer {

    class RecordHolder(override val containerView: View) : RecordDataViewHolder(containerView) {

        companion object {
            fun from(parent: ViewGroup): RecordHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater.inflate(R.layout.item_record, parent, false)
                return RecordHolder(view)
            }
        }

        fun bind(item: Record) =
            with(containerView.context) {
                card_view.background = getDrawable(item.folderBackground)
                val date = DateFormat.getDateInstance().format(item.creationDate)
                tv_record_name.text = getFromHtml(R.string.card_title_name, item.name)
                tv_date.text = getFromHtml(R.string.card_title_date, date)
                tv_duration.text = getFromHtml(
                    R.string.card_title_duration,
                    getTimeInFormatWithSeconds(item.duration)
                )
                tv_folder.text = getFromHtml(R.string.card_title_folder, item.folderName)
            }
    }

    class RecordFolderVersionHolder(override val containerView: View) :
        RecordDataViewHolder(containerView) {

        companion object {
            fun from(parent: ViewGroup): RecordFolderVersionHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view =
                    layoutInflater.inflate(R.layout.item_record_folder_version, parent, false)
                return RecordFolderVersionHolder(view)
            }
        }

        fun bind(item: Record) =
            with(containerView.context) {
                val date = DateFormat.getDateInstance().format(item.creationDate)
                tv_record_name.text = getFromHtml(R.string.card_title_name, item.name)
                tv_date.text = getFromHtml(R.string.card_title_date, date)
                tv_duration.text = getFromHtml(
                    R.string.card_title_duration,
                    getTimeInFormatWithSeconds(item.duration)
                )
            }

    }

    class HeaderHolder(override val containerView: View) : RecordDataViewHolder(containerView) {
        companion object {
            fun from(parent: ViewGroup): HeaderHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater.inflate(R.layout.item_header, parent, false)
                return HeaderHolder(view)
            }
        }

        fun bind(item: RecordDataItem.Header, isWhite: Boolean) =
            with(item) {
                tv_title.text = title
                if (recordsCount != -1)
                    tv_subtitle.text =
                        containerView.context.getString(R.string.records_count, recordsCount)
                else tv_subtitle.visibility = View.GONE
                if (isWhite) {
                    tv_title.setTextColor(Color.WHITE)
                    tv_subtitle.setTextColor(Color.WHITE)
                }
            }
    }

    class FolderHolder(override val containerView: View) : RecordDataViewHolder(containerView) {

        companion object {
            fun from(parent: ViewGroup): FolderHolder {
                val inflater = LayoutInflater.from(parent.context)
                val view = inflater.inflate(R.layout.item_folder, parent, false)
                return FolderHolder(view)
            }
        }

        fun bind(item: Folder) =
            with(item) {
                val context = containerView.context
                card_view.background = context.getDrawable(background)
                tv_name.text = name.toUpperCase()
            }
    }
}
