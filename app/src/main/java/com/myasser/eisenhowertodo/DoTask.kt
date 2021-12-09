package com.myasser.eisenhowertodo

import android.graphics.Paint
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Layout
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.LinearLayout
import androidx.annotation.RequiresApi
import com.myasser.eisenhowertodo.databinding.ActivityDoTaskBinding
import org.w3c.dom.Text

class DoTask : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bind = ActivityDoTaskBinding.inflate(layoutInflater)
        setContentView(bind.root)
        //fill the linear layout with do tasks
        val databaseHelper = DatabaseHelper(applicationContext)
        val params = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        for (task in databaseHelper.readData(Classified.Do)) {
            val checkBox = CheckBox(this)
            checkBox.text = task.getName()
            checkBox.textSize = 25f
            checkBox.gravity = Gravity.CENTER
            checkBox.textAlignment = View.TEXT_ALIGNMENT_TEXT_START
            checkBox.setTextColor(resources.getColor(R.color.white))
            params.setMargins(5, 10, 5, 10)
            checkBox.layoutParams = params
            checkBox.setOnCheckedChangeListener { _, _ ->
                checkBox.visibility = ViewGroup.GONE
                val databaseHelper = DatabaseHelper(applicationContext)
                databaseHelper.deleteTask(task)
            }
            bind.doNowListLayout.addView(checkBox)
        }
    }
}