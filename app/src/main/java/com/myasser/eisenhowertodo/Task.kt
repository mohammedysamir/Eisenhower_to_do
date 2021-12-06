package com.myasser.eisenhowertodo


class Task(Name: String, Classified: Classified) {
    private var taskName = Name
    private var taskClassified = Classified
    fun getName(): String {
        return taskName
    }

    fun getClassified(): Classified {
        return taskClassified
    }

    fun setName(name: String) {
        taskName = name
    }

    fun setClass(Classified: Classified) {
        taskClassified = Classified
    }
}

enum class Classified {
    Do, Decide, Delegate, Delete, None
} 
