package com.example.interactivelist

import android.app.AlertDialog
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    
    private lateinit var toolbar: Toolbar
    private lateinit var editTextTask: EditText
    private lateinit var buttonAdd: Button
    private lateinit var recyclerViewTasks: RecyclerView
    private lateinit var taskAdapter: TaskAdapter
    private var taskList = mutableListOf<Task>()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
        setupToolbar()
        setupRecyclerView()
        setupClickListeners()
    }
    private fun initViews() {
        toolbar = findViewById(R.id.toolbar)
        editTextTask = findViewById(R.id.edit_text_task)
        buttonAdd = findViewById(R.id.button_add)
        recyclerViewTasks = findViewById(R.id.recycler_view_tasks)
    }
    private fun setupToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Lista Interativa de Tarefas"
    }
    private fun setupRecyclerView() {
        taskAdapter = TaskAdapter(
            onTaskClicked = { task ->
                updateTask(task)
            },
            onEditClicked = { task ->
                editTask(task)
            },
            onDeleteClicked = { task ->
                deleteTask(task)
            }
        )
        
        recyclerViewTasks.layoutManager = LinearLayoutManager(this)
        recyclerViewTasks.adapter = taskAdapter
    }
    private fun setupClickListeners() {
        buttonAdd.setOnClickListener {
            addTask()
        }
    }
    private fun addTask() {
        val title = editTextTask.text.toString().trim()
        if (title.isNotEmpty()) {
            val newTask = Task(title = title)
            taskList.add(newTask)
            taskAdapter.submitList(taskList.toList())
            editTextTask.text.clear()
        }
    }
    private fun updateTask(updatedTask: Task) {
        val index = taskList.indexOfFirst { it.id == updatedTask.id }
        if (index != -1) {
            taskList[index] = updatedTask
            taskAdapter.submitList(taskList.toList())
        }
    }
    private fun editTask(taskToEdit: Task) {
        val editText = EditText(this)
        editText.setText(taskToEdit.title)
        editText.setSelection(editText.text.length)
        AlertDialog.Builder(this)
            .setTitle("Editar Tarefa")
            .setView(editText)
            .setPositiveButton("Salvar") { _, _ ->
                val newTitle = editText.text.toString().trim()
                if (newTitle.isNotEmpty()) {
                    val updatedTask = taskToEdit.copy(title = newTitle)
                    updateTask(updatedTask)
                }
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }
    private fun deleteTask(taskToDelete: Task) {
        taskList.removeAll { it.id == taskToDelete.id }
        taskAdapter.submitList(taskList.toList())
    }
}
