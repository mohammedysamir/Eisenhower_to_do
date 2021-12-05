package com.myasser.eisenhowertodo


class Task(Name: String, Classified: String) {
    private var taskName = Name
    private var taskClassified = Classified
    fun getName(): String {
        return taskName
    }

    fun getClassified(): String {
        return taskClassified
    }

    fun setName(name: String) {
        taskName = name
    }

    fun setClass(classified: String) {
        taskClassified = classified
    }
}
