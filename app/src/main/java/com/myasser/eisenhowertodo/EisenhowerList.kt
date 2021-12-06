package com.myasser.eisenhowertodo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.myasser.eisenhowertodo.databinding.ActivityEisenhowerListBinding

class EisenhowerList : AppCompatActivity(), View.OnClickListener {
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var binding: ActivityEisenhowerListBinding
    private lateinit var recyclerView: RecyclerView
    private val todoList = ToDoList()
    private val tasks = todoList.getTasks()
    private val adapter = TaskAdapter(tasks)

    companion object {
        val doNowTasks = arrayListOf<Task>()
        val decideTasks = arrayListOf<Task>()
        val delegateTasks = arrayListOf<Task>()
        val deleteTasks = arrayListOf<Task>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEisenhowerListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        linearLayoutManager = LinearLayoutManager(this)
        recyclerView = binding.taskRecyclerView
        recyclerView.layoutManager = linearLayoutManager
        adapter.setList(todoList.getTasks())
        recyclerView.adapter = adapter

        binding.displayButton.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.displayButton -> {
                //fill the tasks lists
                val tempList = adapter.getList()
                for (t in tempList) {
                    if (t.getClassified() == Classified.Do)
                        doNowTasks.add(t)
                    if (t.getClassified() == Classified.Decide)
                        decideTasks.add(t)
                    if (t.getClassified() == Classified.Delegate)
                        delegateTasks.add(t)
                    if (t.getClassified() == Classified.Delete)
                        deleteTasks.add(t)
                }
                //navigate
            }
        }
    }

}
//TODO: add button on top of the page to delete all tasks and return to ToDoList activity