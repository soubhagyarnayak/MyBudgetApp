package com.snsoft.mybudget

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.arch.persistence.room.Query

@Dao
interface ExpenseDataDao {
    @Insert(onConflict = REPLACE)
    fun insert(expenseEntry: ExpenseEntry)

    @Query("SELECT * FROM expenseEntity")
    fun getAll(): List<ExpenseEntry>
}