package com.itis2019.lecturerecorder.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.itis2019.lecturerecorder.R
import com.itis2019.lecturerecorder.model.Mark
import kotlinx.android.extensions.LayoutContainer

class MarkAdapter(private val listener: (Mark) -> Unit) : ListAdapter<Mark, MarkAdapter.MarkHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarkHolder =
        MarkHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_folder,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: MarkHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
        holder.itemView.setOnClickListener { listener(item) }
    }

    class MarkHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bind(item: Mark) =
            with(item) {

            }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Mark>() {

            override fun areItemsTheSame(oldItem: Mark, newItem: Mark): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Mark, newItem: Mark): Boolean =
                oldItem == newItem
        }
    }
}
