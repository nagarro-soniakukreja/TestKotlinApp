package com.example.testkotlinapp.view.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.testkotlinapp.R

class MainActivity : AppCompatActivity(){

    companion object { //to make it constant and cannot have multiple companion object in 1 class
        private val TAG: String = MainActivity::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        startActivity(Intent(this@MainActivity, TodoListActivity::class.java))
        finish()
    }



}
