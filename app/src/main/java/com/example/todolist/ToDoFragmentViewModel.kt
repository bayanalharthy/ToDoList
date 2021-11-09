package com.example.todolist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.todolist.database.ToDoList
import com.example.todolist.database.ToDoListRepository
import java.util.*


class ToDoFragmentViewModel : ViewModel() {

    private val toDoListRepository = ToDoListRepository.get()
    private val toDoListIdLiveData = MutableLiveData<UUID>()

    var toDoLiveData: LiveData<ToDoList> =
        Transformations.switchMap(toDoListIdLiveData) {
            toDoListRepository.getToDoList(it)
        }

    fun loadToDoList( toDoListId: UUID) {
        toDoListIdLiveData.value = toDoListId
    }
    fun saveUpdate(toDoList: ToDoList){
        toDoListRepository.updateToDoList(toDoList)
    }
}
