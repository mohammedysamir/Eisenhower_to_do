package com.myasser.eisenhowertodo

import android.content.res.ColorStateList
import android.media.MediaPlayer
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.myasser.eisenhowertodo.databinding.ActivityDoTaskBinding

class DoTask : AppCompatActivity(), View.OnClickListener {
    lateinit var bind: ActivityDoTaskBinding

    companion object {
        var position: Int = 1
        var currentTaskClass: Classified = Classified.Do
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityDoTaskBinding.inflate(layoutInflater)
        setContentView(bind.root)
        bind.navigateNext?.setOnClickListener(this)
        bind.navigateBack?.setOnClickListener(this)
        if (position == 1) {
            bind.navigateBack?.visibility = ViewGroup.GONE
            bind.navigateNext?.imageTintList =
                ColorStateList.valueOf(ContextCompat.getColor(applicationContext, R.color.doLater_color))
        }
        getData()
    }

    override fun onClick(p0: View?) {
        var color = ContextCompat.getColor(applicationContext, R.color.doNow_color)
        var nextFragmentColor = ContextCompat.getColor(applicationContext, R.color.doLater_color)
        var backFragmentColor = ContextCompat.getColor(applicationContext, R.color.doNow_color)
        when (p0?.id) {
            /*
            * what to change:
            *   1. if position == 1 then make the back button GONE
            *   2. if position == 4 then make the next button GONE
            *   3. change Main text color and text.
            *   4. change Slogan text and color.
            *   5. change Buttons tint.
            *   6. change background tint.
            * */
            R.id.navigateNext -> {
                if (position <= 3)
                    position++
                if (position == 2) {
                    color = ContextCompat.getColor(applicationContext, R.color.doLater_color)
                    nextFragmentColor = ContextCompat.getColor(applicationContext, R.color.delegate_color)
                    backFragmentColor = ContextCompat.getColor(applicationContext, R.color.doNow_color)
                    bind.fragmentTitle?.text = resources.getString(R.string.decide)
                    bind.fragmentSlogan?.text = resources.getString(R.string.decide_slogan)
                    currentTaskClass = Classified.Decide
                }
                if (position == 3) {
                    color = ContextCompat.getColor(applicationContext, R.color.delegate_color)
                    nextFragmentColor = ContextCompat.getColor(applicationContext, R.color.delete_color)
                    backFragmentColor = ContextCompat.getColor(applicationContext, R.color.doLater_color)
                    bind.fragmentTitle?.text = resources.getString(R.string.delegate)
                    bind.fragmentSlogan?.text = resources.getString(R.string.delegate_slogan)
                    currentTaskClass = Classified.Delegate

                }
                if (position == 4) {
                    color = ContextCompat.getColor(applicationContext, R.color.delete_color)
                    nextFragmentColor = ContextCompat.getColor(applicationContext, R.color.doNow_color)
                    backFragmentColor = ContextCompat.getColor(applicationContext, R.color.delegate_color)
                    bind.fragmentTitle?.text = resources.getString(R.string.delete)
                    bind.fragmentSlogan?.text = resources.getString(R.string.delete_slogan)
                    currentTaskClass = Classified.Delete
                    bind.navigateNext?.visibility = ViewGroup.GONE
                } else {
                    bind.navigateNext?.visibility = ViewGroup.VISIBLE
                }
                bind.fragmentListLayout?.backgroundTintList = ColorStateList.valueOf(color)
                bind.fragmentTitle?.setTextColor(color)
                bind.fragmentSlogan?.setTextColor(color)
                bind.navigateNext?.imageTintList = ColorStateList.valueOf(nextFragmentColor)
                bind.navigateBack?.imageTintList = ColorStateList.valueOf(backFragmentColor)
                getData()
                bind.navigateBack?.visibility = ViewGroup.VISIBLE
            }
            R.id.navigateBack -> {
                if (position > 1)
                    position--
                if (position == 1) {
                    color = ContextCompat.getColor(applicationContext, R.color.doNow_color)
                    nextFragmentColor = ContextCompat.getColor(applicationContext, R.color.doLater_color)
                    backFragmentColor = ContextCompat.getColor(applicationContext, R.color.doNow_color)
                    bind.fragmentTitle?.text = resources.getString(R.string.do_first)
                    bind.fragmentSlogan?.text = resources.getString(R.string.do_now_slogan)
                    currentTaskClass = Classified.Do
                    bind.navigateBack?.visibility = ViewGroup.GONE
                } else {
                    bind.navigateBack?.visibility = ViewGroup.VISIBLE
                }
                if (position == 2) {
                    color = ContextCompat.getColor(applicationContext, R.color.doLater_color)
                    nextFragmentColor = ContextCompat.getColor(applicationContext, R.color.delegate_color)
                    backFragmentColor = ContextCompat.getColor(applicationContext, R.color.doNow_color)
                    bind.fragmentTitle?.text = resources.getString(R.string.decide)
                    bind.fragmentSlogan?.text = resources.getString(R.string.decide_slogan)
                    currentTaskClass = Classified.Decide
                }
                if (position == 3) {
                    color = ContextCompat.getColor(applicationContext, R.color.delegate_color)
                    nextFragmentColor = ContextCompat.getColor(applicationContext, R.color.delete_color)
                    backFragmentColor = ContextCompat.getColor(applicationContext, R.color.doLater_color)
                    bind.fragmentTitle?.text = resources.getString(R.string.decide)
                    bind.fragmentSlogan?.text = resources.getString(R.string.decide_slogan)
                    currentTaskClass = Classified.Delegate
                }
                bind.fragmentListLayout?.backgroundTintList = ColorStateList.valueOf(color)
                bind.fragmentTitle?.setTextColor(color)
                bind.fragmentSlogan?.setTextColor(color)
                bind.navigateNext?.imageTintList = ColorStateList.valueOf(nextFragmentColor)
                bind.navigateBack?.imageTintList = ColorStateList.valueOf(backFragmentColor)
                getData()
                bind.navigateNext?.visibility = ViewGroup.VISIBLE
            }
        }
    }

    private fun getData() {
        bind.fragmentListLayout?.removeAllViews()
        //fill the linear layout with do tasks
        val databaseHelper = DatabaseHelper(applicationContext)
        for (task in databaseHelper.readData(currentTaskClass)) {
            val checkBox = CheckBox(this, null, 0, R.style.checkBoxText)
            checkBox.text = task.getName()
            checkBox.setOnCheckedChangeListener { _, _ ->
                checkBox.visibility = ViewGroup.GONE
                val databaseHelper = DatabaseHelper(applicationContext)
                MediaPlayer.create(this, R.raw.check_sound).start()
                databaseHelper.deleteTask(task)
            }
            bind.fragmentListLayout?.addView(checkBox)
        }
    }
}