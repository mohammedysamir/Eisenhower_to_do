package com.myasser.eisenhowertodo

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

const val databaseName = "Eisenhower matrix"
const val version = 1
const val noneTable = "NONE_TABLE"
const val doTable = "DO_TABLE"
const val decideTable = "DECIDE_TABLE"
const val delegateTable = "DELEGATE_TABLE"
const val deleteTable = "DELETE_TABLE"
const val taskName = "TASK_NAME"
const val taskClass = "CLASSIFIED"

class DatabaseHelper(val context: Context) : SQLiteOpenHelper(context, databaseName, null, version) {
    override fun onCreate(db: SQLiteDatabase?) {
        //create table for each category (None, Do, Decide, Delegate, Delete)
        db?.execSQL("Create Table $noneTable ($taskName TEXT PRIMARY KEY, $taskClass TEXT)")
        db?.execSQL("Create Table $doTable ($taskName TEXT PRIMARY KEY, $taskClass TEXT)")
        db?.execSQL("Create Table $decideTable ($taskName TEXT PRIMARY KEY, $taskClass TEXT)")
        db?.execSQL("Create Table $delegateTable ($taskName TEXT PRIMARY KEY, $taskClass TEXT)")
        db?.execSQL("Create Table $deleteTable ($taskName TEXT PRIMARY KEY, $taskClass TEXT)")
    }

    override fun onUpgrade(db: SQLiteDatabase?, old_version: Int, new_version: Int) {
        TODO("Not yet implemented")
    }

    fun insertTask(task: Task, classified: Classified) {
        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(taskName, task.getName())
        contentValues.put(taskClass, classified.toString())
        val table = when (classified) {
            Classified.None -> noneTable
            Classified.Do -> doTable
            Classified.Decide -> decideTable
            Classified.Delegate -> delegateTable
            Classified.Delete -> deleteTable
        }
        database.insert(table, null, contentValues)
    }

    fun insertTaskList(taskList: ArrayList<Task>, classified: Classified) {
        val database = this.writableDatabase
        for (task in taskList) {
            val contentValues = ContentValues()
            contentValues.put(taskName, task.getName())
            contentValues.put(taskClass, classified.toString())
            val table = when (classified) {
                Classified.None -> noneTable
                Classified.Do -> doTable
                Classified.Decide -> decideTable
                Classified.Delegate -> delegateTable
                Classified.Delete -> deleteTable
            }
            database.insert(table, null, contentValues)
        }
    }

    @SuppressLint("Range")
    fun readData(classified: Classified): ArrayList<Task> {
        val list = ArrayList<Task>()
        val reader = this.readableDatabase
        val table = when (classified) {
            Classified.None -> noneTable
            Classified.Do -> doTable
            Classified.Decide -> decideTable
            Classified.Delegate -> delegateTable
            Classified.Delete -> deleteTable
        }
        val query = "Select * from $table"
        val result = reader.rawQuery(query, null)
        if (result.moveToFirst()) {
            do {
                val task_name = result.getString(result.getColumnIndex(taskName))
                list.add(Task(task_name, classified))
            } while (result.moveToNext())
        }
        return list
    }

    fun clearAllData() {
        val db: SQLiteDatabase = this.writableDatabase
        db.delete(noneTable, null, null)
        db.delete(doTable, null, null)
        db.delete(decideTable, null, null)
        db.delete(delegateTable, null, null)
        db.delete(deleteTable, null, null)
    }
}
/*
* Usage scenario:
*   1. before navigating from to do list to eisenhower add all tasks to noneTable
*   2. before navigating from eisenhower to the fragments add tasks to their table
*   3. when pressing on the delete button call clearAllData()
* */