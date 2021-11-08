package com.example.todolist.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters

@Database(entities = [ToDoList::class],version =1)
@TypeConverters(ToDoListTypeConverters::class)
abstract class ToListDatabase:RoomDatabase(){

    abstract fun ToDoListDao():ToDoListDao
}



