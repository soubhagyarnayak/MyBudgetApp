package com.snsoft.mybudget

object ExpenseCategory {
    val categories = mapOf<Int, String>(0 to "Extra",1 to "Food", 2 to "Grocery", 3 to "Travel",
            4 to "Bills", 5 to "Medical")
    fun getCategory(categoryId:Int):String?{
        return categories[categoryId]
    }
}