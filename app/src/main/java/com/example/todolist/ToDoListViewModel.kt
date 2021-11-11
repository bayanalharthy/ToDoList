package com.example.todolist

import androidx.lifecycle.ViewModel
import com.example.todolist.database.ToDoList
import com.example.todolist.database.ToDoListRepository

class ToDoListViewModel : ViewModel() {

    private val toDoListRepository = ToDoListRepository.get()

    val liveDataToDoList=toDoListRepository.getAllToDoList()


}

