package com.snsoft.mybudget

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
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