package com.snsoft.mybudget

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query

@Dao
interface ExpenseDataDao {
    @Insert(onConflict = REPLACE)
    fun insert(expenseEntry: ExpenseEntry)

    @Query("SELECT * FROM expenseEntity")
    fun getAll(): List<ExpenseEntry>
}