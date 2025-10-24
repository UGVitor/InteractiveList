package com.example.interactivelist

data class Task(
    val id: Long = System.currentTimeMillis(),
    val title: String,
    val isCompleted: Boolean = false
)
