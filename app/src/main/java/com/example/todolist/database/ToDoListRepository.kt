package com.example.todolist.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import java.lang.IllegalStateException
import java.util.*
import java.util.concurrent.Executors


private const val DATABASE_NAME="toDoList_database"

class ToDoListRepository {
    class ToDoListRepository(context: Context) {

        private val Database: ToListDatabase = Room.databaseBuilder(
            context.applicationContext,
            ToListDatabase::class.java,
            DATABASE_NAME

        ).build()


        private val ToDoListDao = Database.ToDoListDao()
        private val executor = Executors.newSingleThreadExecutor()

        fun getAllToDoList(): LiveData<List<ToDoList>> = ToDoListDao.getAllToDoList()

        fun getToDoList(id: UUID): LiveData<ToDoList?> {
            return ToDoListDao.getToDoList(id)
        }

        fun updateToDoList(crime: ToDoList) {
            executor.execute {
                ToDoListDao.updateToDoList(ToDoList())
            }

        }

        fun addToDoList(toDoList: ToDoList) {
            executor.execute {
                ToDoListDao.addToDoList(toDoList)
            }
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


















