package com.myasser.eisenhowertodo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources.*
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class TaskAdapter(private val mList: List<Task>) : RecyclerView.Adapter<TaskAdapter.ViewHolder>(),
    View.OnClickListener {
    lateinit var taskItem: Task

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val taskName: TextView = itemView.findViewById(R.id.taskName)
        val doNowButton: FloatingActionButton = itemView.findViewById(R.id.doNowButton)
        val decideButton: FloatingActionButton = itemView.findViewById(R.id.decideButton)
        val delegateButton: FloatingActionButton = itemView.findViewById(R.id.delegateButton)
        val deleteButton: FloatingActionButton = itemView.findViewById(R.id.deleteButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.task_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        taskItem = mList[position]
        holder.taskName.text = taskItem.getName()
        if (taskItem.getClassified() == Classified.None) {
            holder.decideButton.background = getDrawable(holder.itemView.context, R.color.transparent)
            holder.doNowButton.background = getDrawable(holder.itemView.context, R.color.transparent)
            holder.delegateButton.background = getDrawable(holder.itemView.context, R.color.transparent)
            holder.deleteButton.background = getDrawable(holder.itemView.context, R.color.transparent)
        }
        holder.doNowButton.setOnClickListener(this)
        holder.decideButton.setOnClickListener(this)
        holder.delegateButton.setOnClickListener(this)
        holder.deleteButton.setOnClickListener(this)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onClick(p0: View?) {
        if (p0?.background == getDrawable(p0?.context!!, R.color.black))
            p0.background = getDrawable(p0.context!!, R.color.transparent)
        else
            p0.background = getDrawable(p0.context!!, R.color.black)

        when (p0.id) {
            R.id.doNowButton -> taskItem.setClass(Classified.Do)
            R.id.decideButton -> taskItem.setClass(Classified.Decide)
            R.id.delegateButton -> taskItem.setClass(Classified.Delegate)
            R.id.deleteButton -> taskItem.setClass(Classified.Delete)
        }
    }

}