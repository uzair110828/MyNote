package com.example.mynotes.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mynotes.dao.NoteDao
import com.example.mynotes.entities.Notes

@Database(entities = [Notes::class],version = 1 , exportSchema = false)
abstract class NoteDatabase : RoomDatabase() {
    companion object{

        @Synchronized
        fun getDatabase(context: Context):NoteDatabase{
            val db = Room.databaseBuilder(
                context,
                NoteDatabase::class.java, "database-name"
            ).build()
            return db
        }
    }

    abstract fun noteDao():NoteDao
}