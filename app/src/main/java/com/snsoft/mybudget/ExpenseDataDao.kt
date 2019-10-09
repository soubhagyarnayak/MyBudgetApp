package com.snsoft.mybudget

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import java.util.*

@Dao
interface ExpenseDataDao {
    @Insert(onConflict = REPLACE)
    fun insert(expenseEntry: ExpenseEntry)

    @Query("SELECT * FROM expenseEntity")
    fun getAll(): List<ExpenseEntry>

    @Query("SELECT * FROM expenseEntity WHERE createTime>:after AND createTime<:before")
    fun getAllForTimeRange(after: Date, before: Date): List<ExpenseEntry>
}