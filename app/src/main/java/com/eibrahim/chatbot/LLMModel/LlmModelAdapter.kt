package com.eibrahim.chatbot.LLMModel

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.eibrahim.chatbot.R

class LlmModelAdapter(
    private val models: List<LlmModel>,
    private val onModelSelected: (LlmModel) -> Unit
) : RecyclerView.Adapter<LlmModelAdapter.ModelViewHolder>() {

    private var selectedPosition = -1

    inner class ModelViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val modelName: TextView = itemView.findViewById(R.id.modelName)
        val modelDescription: TextView = itemView.findViewById(R.id.modelDescription)
        val modelImage: ImageView = itemView.findViewById(R.id.modelImage)
        val radioButton: RadioButton = itemView.findViewById(R.id.radioButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ModelViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_llm_model, parent, false)
        return ModelViewHolder(view)
    }

    override fun onBindViewHolder(holder: ModelViewHolder, position: Int) {
        val model = models[position]
        holder.modelName.text = model.name
        holder.modelDescription.text = model.description
        holder.modelImage.setImageResource(model.imageResId)
        holder.radioButton.isChecked = position == selectedPosition

        holder.itemView.setOnClickListener {
            selectPosition(position)
        }

        holder.radioButton.setOnClickListener {
            selectPosition(position)
        }
    }

    private fun selectPosition(position: Int) {
        val previousPosition = selectedPosition
        selectedPosition = position
        notifyItemChanged(previousPosition)
        notifyItemChanged(position)
        onModelSelected(models[position])
    }

    override fun getItemCount(): Int = models.size

    fun getSelectedModel(): LlmModel? {
        return if (selectedPosition != -1) models[selectedPosition] else null
    }
}
