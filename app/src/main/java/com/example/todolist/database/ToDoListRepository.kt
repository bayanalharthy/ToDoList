package com.example.todolist.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import java.lang.IllegalStateException
import java.util.*
import java.util.concurrent.Executors


private const val DATABASE_NAME="toDoList_database"

class ToDoListRepository private constructor(context: Context): ToDoListDao {

        private val database: ToListDatabase = Room.databaseBuilder(
            context.applicationContext,
            ToListDatabase::class.java,
            DATABASE_NAME

        ).build()


        private val toDoListDao = database.ToDoListDao()
        private val executor = Executors.newSingleThreadExecutor()

        override fun getAllToDoList(): LiveData<List<ToDoList>> = toDoListDao.getAllToDoList()

        override fun getToDoList(id: UUID): LiveData<ToDoList?> {
         return  toDoListDao.getToDoList(id)
        }

        override fun updateToDoList(toDoList:ToDoList) {
            executor.execute {
                toDoListDao.updateToDoList(toDoList)
            }

        }

        override fun addToDoList(toDoList:ToDoList) {
            executor.execute {
                toDoListDao.addToDoList(toDoList)
            }
        }

    override fun deleteToDOList(toDoList: ToDoList) {
        executor.execute {
            toDoListDao.deleteToDOList(toDoList)
        }

    }

    companion object {

        var INSTANCE:ToDoListRepository? = null


        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = ToDoListRepository(context)
            }
        }


        fun get(): ToDoListRepository {
            return INSTANCE
                ?: throw IllegalStateException("to do list Repository must be initialized")
        }

    }

}
















