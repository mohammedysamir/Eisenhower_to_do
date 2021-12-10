package com.myasser.eisenhowertodo

import android.content.Intent
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
        //adapter.setList(todoList.getTasks())
        val databaseHelper = DatabaseHelper(applicationContext)
        adapter.setList(databaseHelper.readData(Classified.None))
        recyclerView.adapter = adapter
        binding.displayButton.setOnClickListener(this)
        binding.deleteTasksButton.setOnClickListener(this)
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
                //Add tasks to sql
                val databaseHelper = DatabaseHelper(applicationContext)
                databaseHelper.insertTaskList(doNowTasks, Classified.Do)
                databaseHelper.insertTaskList(decideTasks, Classified.Decide)
                databaseHelper.insertTaskList(delegateTasks, Classified.Delegate)
                databaseHelper.insertTaskList(deleteTasks, Classified.Delete)
                databaseHelper.close()
                //navigate
                startActivity(Intent(this,taskFragment::class.java))
                finish()
            }
            R.id.deleteTasksButton -> {
                //clear all lists
                doNowTasks.clear()
                decideTasks.clear()
                delegateTasks.clear()
                deleteTasks.clear()
                adapter.clearList()
                ToDoList.tasks.clear()
                ToDoList.taskListString = ""
                adapter.setList(ToDoList.tasks)
                binding.taskRecyclerView.removeAllViews()
                //Delete from db
                val databaseHelper = DatabaseHelper(applicationContext)
                databaseHelper.clearAllData()
                databaseHelper.close()
                //intent and navigate to To do activity
                val intent = Intent(this, ToDoList::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

}

//TODO: after the current code with lists and DB works good, remove lists and depend only on the DB