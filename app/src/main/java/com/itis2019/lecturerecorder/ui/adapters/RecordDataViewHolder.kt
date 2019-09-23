package com.itis2019.lecturerecorder.ui.adapters

import android.graphics.Color
import android.view.*
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.itis2019.lecturerecorder.R
import com.itis2019.lecturerecorder.entities.Folder
import com.itis2019.lecturerecorder.entities.Record
import com.itis2019.lecturerecorder.utils.MENU_DELETE
import com.itis2019.lecturerecorder.utils.MENU_RENAME
import com.itis2019.lecturerecorder.utils.getFromHtml
import com.itis2019.lecturerecorder.utils.getTimeInFormatWithSeconds
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_folder.*
import kotlinx.android.synthetic.main.item_header.*
import kotlinx.android.synthetic.main.item_record.*
import java.text.DateFormat

sealed class RecordDataViewHolder(override val containerView: View) :
    RecyclerView.ViewHolder(containerView), LayoutContainer {

    class RecordHolder(
        override val containerView: View,
        private val menuItemClickListener: MenuItem.OnMenuItemClickListener
    ) : RecordDataViewHolder(containerView), View.OnCreateContextMenuListener {

        companion object {
            fun from(
                parent: ViewGroup,
                menuItemClickListener: MenuItem.OnMenuItemClickListener
            ): RecordHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater.inflate(R.layout.item_record, parent, false)
                return RecordHolder(view, menuItemClickListener)
            }
        }

        fun bind(item: Record, isWhite: Boolean) =
            with(containerView.context) {
                containerView.setOnCreateContextMenuListener(this@RecordHolder)
                if (isWhite) {
                    (card_view as CardView).setCardBackgroundColor(resources.getColor(R.color.colorWhiteSemiTransparentStatusBar))
                    val date = DateFormat.getDateInstance().format(item.creationDate)
                    val textColor = resources.getColor(R.color.colorText)
                    tv_record_name.setTextColor(textColor)
                    tv_record_name.text = getFromHtml(R.string.card_title_name, item.name)
                    tv_date.setTextColor(textColor)
                    tv_date.text = getFromHtml(R.string.card_title_date, date)
                    tv_duration.setTextColor(textColor)
                    tv_duration.text = getFromHtml(
                        R.string.card_title_duration,
                        getTimeInFormatWithSeconds(item.duration)
                    )
                    tv_folder.visibility = View.GONE
                } else {
                    card_view_record.background = getDrawable(item.folderBackground)
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

        override fun onCreateContextMenu(
            menu: ContextMenu?,
            view: View?,
            menuInfo: ContextMenu.ContextMenuInfo?
        ) {
            menu?.apply {
                add(position, MENU_RENAME, Menu.NONE, R.string.rename)
                    .setOnMenuItemClickListener(menuItemClickListener)
                add(position, MENU_DELETE, Menu.NONE, R.string.delete)
                    .setOnMenuItemClickListener(menuItemClickListener)
            }
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

    class FolderHolder(
        override val containerView: View,
        private val menuItemClickListener: MenuItem.OnMenuItemClickListener
    ) : RecordDataViewHolder(containerView),
        View.OnCreateContextMenuListener {

        companion object {
            fun from(
                parent: ViewGroup,
                menuListener: MenuItem.OnMenuItemClickListener
            ): FolderHolder {
                val inflater = LayoutInflater.from(parent.context)
                val view = inflater.inflate(R.layout.item_folder, parent, false)
                return FolderHolder(view, menuListener)
            }
        }

        fun bind(item: Folder) =
            with(item) {
                containerView.setOnCreateContextMenuListener(this@FolderHolder)
                card_view_folder.background = containerView.context.getDrawable(background)
                tv_name.text = name.toUpperCase()
            }

        override fun onCreateContextMenu(
            menu: ContextMenu?,
            view: View?,
            menuInfo: ContextMenu.ContextMenuInfo?
        ) {
            menu?.apply {
                add(position, MENU_RENAME, Menu.NONE, R.string.rename)
                    .setOnMenuItemClickListener(menuItemClickListener)
                add(position, MENU_DELETE, Menu.NONE, R.string.delete)
                    .setOnMenuItemClickListener(menuItemClickListener)
            }
        }
    }
}
