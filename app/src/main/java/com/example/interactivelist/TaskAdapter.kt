package com.example.interactivelist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class TaskAdapter(
    private val onTaskClicked: (Task) -> Unit,
    private val onEditClicked: (Task) -> Unit,
    private val onDeleteClicked: (Task) -> Unit
) : ListAdapter<Task, TaskAdapter.TaskViewHolder>(TaskDiffCallback()) {

    class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val checkboxCompleted: CheckBox = itemView.findViewById(R.id.checkbox_completed)
        val textTitle: TextView = itemView.findViewById(R.id.text_title)
        val buttonEdit: ImageButton = itemView.findViewById(R.id.button_edit)
        val buttonDelete: ImageButton = itemView.findViewById(R.id.button_delete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = getItem(position)
        holder.textTitle.text = task.title
        holder.checkboxCompleted.setOnCheckedChangeListener(null)
        holder.checkboxCompleted.isChecked = task.isCompleted
        holder.checkboxCompleted.setOnCheckedChangeListener { _, isChecked ->
            val currentTask = getItem(holder.adapterPosition)
            val updatedTask = currentTask.copy(isCompleted = isChecked)
            onTaskClicked(updatedTask)
        }
        holder.buttonEdit.setOnClickListener {
            val currentTask = getItem(holder.adapterPosition)
            onEditClicked(currentTask)
        }
        holder.buttonDelete.setOnClickListener {
            val currentTask = getItem(holder.adapterPosition)
            onDeleteClicked(currentTask)
        }
    }
}
class TaskDiffCallback : DiffUtil.ItemCallback<Task>() {
    override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
        return oldItem.id == newItem.id
    }
    override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
        return oldItem == newItem
    }
}
