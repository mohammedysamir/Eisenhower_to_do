package com.myasser.eisenhowertodo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import com.myasser.eisenhowertodo.databinding.ActivityToDoListBinding

class ToDoList : AppCompatActivity(), View.OnClickListener {
    lateinit var binding: ActivityToDoListBinding
    private val tasks = arrayListOf<Task>()
    private var taskListString = ""
    var textCounter = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityToDoListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.navigateButton.setOnClickListener(this)
        binding.taskInputLayout.imeOptions = EditorInfo.IME_ACTION_NEXT
        binding.taskInputLayout.setOnEditorActionListener { _, i, _ ->
            if (i == EditorInfo.IME_ACTION_NEXT) {
                addToTasks()
                true
            } else
                false
        }
    }

    fun getTasks(): List<Task> {
        return tasks
    }

    private fun addToTasks() {
        val task = Task(binding.taskInputLayout.text.toString(), Classified.None)
        tasks.add(task)
        taskListString += "${textCounter++}: ${binding.taskInputLayout.text.toString()}\n"
        binding.taskList.text = taskListString
        binding.taskInputLayout.text?.clear()
    }

    override fun onClick(p0: View?) {
        if (p0?.id == R.id.navigateButton) {
            val intent: Intent = Intent(this, EisenhowerList::class.java)
            startActivity(intent)
            finish()
        }
    }
}