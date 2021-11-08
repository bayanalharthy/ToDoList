package com.example.todolist.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
    data class ToDoList
    (
    @PrimaryKey val id: UUID = UUID.randomUUID(),
    var title: String = "",
    var date: Date = Date(),
    var name: String = "",
    var description: String = "" ,
    var done: Boolean = false
)


