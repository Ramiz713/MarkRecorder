package com.itis2019.lecturerecorder.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.itis2019.lecturerecorder.R
import com.itis2019.lecturerecorder.entities.Folder
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_folder.*

class FolderAdapter(private val listener: (Folder) -> Unit) : ListAdapter<Folder, FolderAdapter.FolderHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FolderHolder =
        FolderHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_folder,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: FolderHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
        holder.itemView.setOnClickListener { listener(item) }
    }

    class FolderHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bind(item: Folder) =
            with(item) {
                val context = containerView.context
                card_view.background = context.getDrawable(background)
                tv_name.text = name.toUpperCase()
            }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Folder>() {

            override fun areItemsTheSame(oldItem: Folder, newItem: Folder): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Folder, newItem: Folder): Boolean =
                oldItem == newItem
        }
    }
}
