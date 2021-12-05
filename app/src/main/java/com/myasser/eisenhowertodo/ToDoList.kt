package com.myasser.eisenhowertodo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import com.myasser.eisenhowertodo.databinding.ActivityToDoListBinding

class ToDoList : AppCompatActivity() {
    lateinit var binding: ActivityToDoListBinding
    private val tasks = arrayListOf<Task>()
    private var taskListString = ""
    var textCounter = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityToDoListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.taskInputLayout.imeOptions = EditorInfo.IME_ACTION_NEXT
        binding.taskInputLayout.setOnEditorActionListener { _, i, _ ->
            if (i == EditorInfo.IME_ACTION_NEXT) {
                addToTasks()
                true
            } else
                false
        }
    }

    private fun getTasks(): List<Task> {
        return tasks
    }

    private fun addToTasks() {
        val task = Task(binding.taskInputLayout.text.toString(), "unclassified")
        tasks.add(task)
        taskListString += "${textCounter++}: ${binding.taskInputLayout.text.toString()}\n"
        binding.taskList.text = taskListString
        binding.taskInputLayout.text?.clear()
    }
}