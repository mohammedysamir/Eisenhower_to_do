package com.myasser.eisenhowertodo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import java.util.*

//started in 5/12/21
class Splash : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        val databaseHelper = DatabaseHelper(applicationContext)
        val intent: Intent
        if (databaseHelper.readData(Classified.None).size > 0) //there is previous saved data so go to eisenhower activity
            intent = Intent(this, EisenhowerList::class.java)
        else
            intent = Intent(this, ToDoList::class.java)
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(intent)
            finish()
        }, 2000)
    }
}