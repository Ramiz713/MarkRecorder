package com.itis2019.lecturerecorder.ui.adapters

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.itis2019.lecturerecorder.entities.Folder
import com.itis2019.lecturerecorder.entities.Record
import com.itis2019.lecturerecorder.utils.ITEM_VIEW_TYPE_FOLDER
import com.itis2019.lecturerecorder.utils.ITEM_VIEW_TYPE_HEADER
import com.itis2019.lecturerecorder.utils.ITEM_VIEW_TYPE_ITEM
import com.itis2019.lecturerecorder.utils.ITEM_VIEW_TYPE_ITEM_ANOTHER_STYLE

class RecordAdapter(private val listener: (id: Long) -> Unit) :
    ListAdapter<RecordDataItem, RecordDataViewHolder>(DIFF_CALLBACK) {

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is RecordDataItem.Header -> ITEM_VIEW_TYPE_HEADER
            is RecordDataItem.RecordItem -> ITEM_VIEW_TYPE_ITEM
            is RecordDataItem.RecordItemFolderVersion -> ITEM_VIEW_TYPE_ITEM_ANOTHER_STYLE
            is RecordDataItem.FolderItem -> ITEM_VIEW_TYPE_FOLDER
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecordDataViewHolder =
        when (viewType) {
            ITEM_VIEW_TYPE_HEADER -> RecordDataViewHolder.HeaderHolder.from(parent)
            ITEM_VIEW_TYPE_ITEM -> RecordDataViewHolder.RecordHolder.from(parent)
            ITEM_VIEW_TYPE_FOLDER -> RecordDataViewHolder.FolderHolder.from(parent)
            ITEM_VIEW_TYPE_ITEM_ANOTHER_STYLE -> RecordDataViewHolder.RecordFolderVersionHolder.from(
                parent
            )
            else -> throw ClassCastException("Unknown viewType $viewType")
        }

    override fun onBindViewHolder(holder: RecordDataViewHolder, position: Int) =
        when (holder) {
            is RecordDataViewHolder.HeaderHolder -> {
                val item = getItem(position) as RecordDataItem.Header
                holder.bind(item, item.isWhite)
            }
            is RecordDataViewHolder.RecordHolder -> {
                val item = getItem(position) as RecordDataItem.RecordItem
                holder.bind(item.lecture)
                holder.itemView.setOnClickListener { listener(item.lecture.id) }
            }
            is RecordDataViewHolder.RecordFolderVersionHolder -> {
                val item = getItem(position) as RecordDataItem.RecordItemFolderVersion
                holder.bind(item.lecture)
                holder.itemView.setOnClickListener { listener(item.lecture.id) }
            }
            is RecordDataViewHolder.FolderHolder -> {
                val item = getItem(position) as RecordDataItem.FolderItem
                holder.bind(item.folder)
                holder.itemView.setOnClickListener { listener(item.folder.id) }
            }
        }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<RecordDataItem>() {

            override fun areItemsTheSame(
                oldItem: RecordDataItem,
                newItem: RecordDataItem
            ): Boolean = oldItem.id == newItem.id

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(
                oldItem: RecordDataItem,
                newItem: RecordDataItem
            ): Boolean = oldItem == newItem
        }
    }
}

sealed class RecordDataItem {
    abstract val id: Long

    data class RecordItem(val lecture: Record) : RecordDataItem() {
        override val id: Long = lecture.id
    }

    data class RecordItemFolderVersion(val lecture: Record) : RecordDataItem() {
        override val id: Long = lecture.id
    }

    data class FolderItem(val folder: Folder) : RecordDataItem() {
        override val id: Long = folder.id
    }

    data class Header(val title: String, val recordsCount: Int = -1, val isWhite: Boolean = false) :
        RecordDataItem() {
        override val id = Long.MIN_VALUE
    }
}
