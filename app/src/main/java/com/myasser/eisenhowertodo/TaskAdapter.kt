package com.myasser.eisenhowertodo

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TaskAdapter(private var mList: List<Task>) : RecyclerView.Adapter<TaskAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val taskName: TextView = itemView.findViewById(R.id.taskName)
        val doNowButton: ImageButton = itemView.findViewById(R.id.doNowButton)
        val decideButton: ImageButton = itemView.findViewById(R.id.decideButton)
        val delegateButton: ImageButton = itemView.findViewById(R.id.delegateButton)
        val deleteButton: ImageButton = itemView.findViewById(R.id.deleteButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.task_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val taskItem = mList[position]
        Log.i("task", taskItem.toString())
        holder.taskName.text = taskItem.getName()
        holder.doNowButton.setOnClickListener {
            taskItem.setClass(Classified.Do)
            holder.decideButton.setImageResource(R.color.transparent)
            holder.delegateButton.setImageResource(R.color.transparent)
            holder.deleteButton.setImageResource(R.color.transparent)
            holder.doNowButton.setImageResource(R.drawable.ic_baseline_done_24)
        }
        holder.decideButton.setOnClickListener {
            taskItem.setClass(Classified.Decide)
            holder.doNowButton.setImageResource(R.color.transparent)
            holder.delegateButton.setImageResource(R.color.transparent)
            holder.deleteButton.setImageResource(R.color.transparent)
            holder.decideButton.setImageResource(R.drawable.ic_baseline_done_24)
        }
        holder.delegateButton.setOnClickListener {
            taskItem.setClass(Classified.Delegate)
            holder.doNowButton.setImageResource(R.color.transparent)
            holder.decideButton.setImageResource(R.color.transparent)
            holder.deleteButton.setImageResource(R.color.transparent)
            holder.delegateButton.setImageResource(R.drawable.ic_baseline_done_24)
        }
        holder.deleteButton.setOnClickListener {
            taskItem.setClass(Classified.Delete)
            holder.doNowButton.setImageResource(R.color.transparent)
            holder.delegateButton.setImageResource(R.color.transparent)
            holder.decideButton.setImageResource(R.color.transparent)
            holder.deleteButton.setImageResource(R.drawable.ic_baseline_done_24)
        }
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    fun setList(list: List<Task>) {
        mList = list
    }

    fun getList(): List<Task> {
        return mList
    }
}