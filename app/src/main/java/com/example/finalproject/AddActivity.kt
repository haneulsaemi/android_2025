package com.example.finalproject

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.finalproject.databinding.ActivityAddBinding

class AddActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.addToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val date = intent.getStringExtra("today")
        binding.date.text = date

        binding.btnSave.setOnClickListener {
            val todo_str = binding.addEditView.text.toString()
            val db = DBHelper(this).writableDatabase
            db.execSQL("insert into TODO_TB (todo) values (?)", arrayOf(todo_str))
            db.close()
            val intent = intent
            intent.putExtra("result", todo_str)
            setResult(Activity.RESULT_OK, intent)

            finish()
            true
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        val intent = intent
        intent.putExtra("result", "")
        setResult(Activity.RESULT_OK, intent)

        finish()
        return super.onSupportNavigateUp()
    }

}