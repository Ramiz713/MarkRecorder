package com.itis2019.lecturerecorder.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.itis2019.lecturerecorder.R
import com.itis2019.lecturerecorder.entities.Mark
import com.itis2019.lecturerecorder.utils.getTimeInFormatWithSeconds
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_mark.*
import kotlinx.android.synthetic.main.item_mark.view.*

class MarkAdapter(
    private val clickListener: (Mark) -> Unit? = {},
    private val editListener: (Mark) -> Unit? = {},
    private val deleteListener: (Mark) -> Unit? = { }
) : ListAdapter<Mark, MarkAdapter.MarkHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarkHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_mark, parent, false)
        return MarkHolder(view)
    }

    override fun onBindViewHolder(holder: MarkHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
        with(holder.itemView) {
            setOnClickListener { clickListener(item) }
            iv_edit_name.setOnClickListener { editListener(item) }
            iv_delete_mark.setOnClickListener { deleteListener(item) }
        }
    }

    class MarkHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
        LayoutContainer {

        fun bind(item: Mark) =
            with(item) {
                tv_mark_name.text = if (name.isNotEmpty()) name else null
                tv_mark_number.text = getTimeInFormatWithSeconds(time)
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
