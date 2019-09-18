package com.snsoft.mybudget

import androidx.room.*
import java.util.*

@Entity(tableName = "expenseEntity")
data class ExpenseEntry(@PrimaryKey(autoGenerate = true) var id: Long?,
                        @ColumnInfo(name = "description") var description: String,
                        @ColumnInfo(name = "amount") var amount: Double,
                        @ColumnInfo(name = "category") var category: Int,
                        @ColumnInfo(name= "createTime") var createTime: Date,
                        @ColumnInfo(name= "updateTime") var updateTime: Date
) {
    constructor(): this(null,"",0.0,0,Calendar.getInstance().time,Calendar.getInstance().time)
}