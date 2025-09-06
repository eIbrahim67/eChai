package com.eibrahim.chatbot.settings

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.eibrahim.chatbot.R

class SettingsAdapter(
    private val items: List<SettingItem>,
    private val goTo: (Int) -> Unit,
    private val showBottomSheet: () -> Unit
) :
    RecyclerView.Adapter<SettingsAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val icon = view.findViewById<ImageView>(R.id.item_icon)
        val title = view.findViewById<TextView>(R.id.item_title)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.setting_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        val destination = item.destination
        holder.icon.setImageResource(item.iconRes)
        holder.title.text = item.title

        holder.itemView.setOnClickListener {

            if (destination != null) {
                if (destination == -1){
                    showBottomSheet()
                }
                else
                    goTo(item.destination)
            }

        }
    }

    override fun getItemCount() = items.size
}
