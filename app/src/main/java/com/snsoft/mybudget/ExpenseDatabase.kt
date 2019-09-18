package com.snsoft.mybudget

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import android.content.Context

@Database(entities = arrayOf(ExpenseEntry::class), version = 1)
@TypeConverters(Converters::class)
abstract class ExpenseDatabase: RoomDatabase() {
    abstract  fun expenseDataDao() : ExpenseDataDao

    companion object {
        private var instance: ExpenseDatabase? = null

        fun getInstance(context:Context) : ExpenseDatabase?{
            if(instance == null){
                synchronized(ExpenseDatabase::class){
                    instance = Room.databaseBuilder(context.applicationContext,
                            ExpenseDatabase::class.java,"expense.db")
                            .build()
                }
            }
            return instance
        }

        fun destroyInstance() {
            instance = null
        }
    }
}