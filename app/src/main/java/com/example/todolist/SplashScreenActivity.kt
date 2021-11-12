package com.example.todolist

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.example.todolist.database.DatePickerDialogFragment
import com.example.todolist.database.ToDoList

class SplashScreenActivity : AppCompatActivity() {

    private lateinit var et_name: EditText
    private lateinit var addBtn: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        et_name = findViewById(R.id.et_name)
        addBtn = findViewById(R.id.btn_add)
         val sharedPreference = getSharedPreferences("BayanApp" , Context.MODE_PRIVATE)
        var
                edietor = sharedPreference.edit()


        if(sharedPreference.getBoolean("isOpen", false)){
            val OpenAct = Intent(this , MainActivity::class.java)
            startActivity(OpenAct)
        }
        addBtn.setOnClickListener {
            var name_User = et_name.text.toString()
            edietor.putString("UserName" ,"Welcome "+name_User)
            edietor.putBoolean("isOpen" ,true)
            edietor.commit()

            val OpenAct = Intent(this , MainActivity::class.java)
            startActivity(OpenAct)

        }


    }
}