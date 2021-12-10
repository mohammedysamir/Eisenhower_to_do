package com.myasser.eisenhowertodo

import android.graphics.Paint
import android.media.MediaPlayer
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.LinearLayout
import androidx.annotation.RequiresApi
import com.google.android.material.resources.TextAppearance
import com.myasser.eisenhowertodo.databinding.ActivityDoTaskBinding

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
            val checkBox = CheckBox(this, null, 0, R.style.checkBoxText)
            checkBox.text = task.getName()
            checkBox.setOnCheckedChangeListener { _, _ ->
                checkBox.visibility = ViewGroup.GONE
                val databaseHelper = DatabaseHelper(applicationContext)
                MediaPlayer.create(this,R.raw.check_sound).start()
                databaseHelper.deleteTask(task)
            }
            bind.doNowListLayout.addView(checkBox)
        }
    }
}