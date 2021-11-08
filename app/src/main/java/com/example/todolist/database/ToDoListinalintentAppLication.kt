package com.example.todolist.database

import android.app.Application

class ToDoListinalintentAppLication: Application() {


    override fun onCreate() {
        super.onCreate()

       ToDoListRepository.initialize(this)
    }
}