package com.example.todolist.database

import androidx.lifecycle.LiveData
import androidx.room.*
import java.util.*
@Dao
interface ToDoListDao {

    @Query("SELECT*FROM ToDoList")
    fun getAllToDoList(): LiveData<List<ToDoList>>

    @Query("SELECT*FROM ToDoList WHERE id =(:id)")
    fun getToDoList(id: UUID): LiveData<ToDoList?>
    @Update
    fun updateToDoList(toDoList:ToDoList)
    @Insert
    fun addToDoList(toDoList:ToDoList )
    @Delete
    fun deleteToDOList(toDoList:ToDoList)



}




