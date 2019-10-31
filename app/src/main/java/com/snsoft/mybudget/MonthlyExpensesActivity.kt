package com.snsoft.mybudget

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.view.LayoutInflater
import kotlinx.android.synthetic.main.activity_monthly_expenses.*
import kotlinx.android.synthetic.main.monthly_expense_entry.view.*
import java.util.*
import java.util.logging.Logger
import kotlin.collections.ArrayList

class MonthlyExpensesActivity : AppCompatActivity() {
    private var expenseDatabase : ExpenseDatabase? = null
    private lateinit var dbWorkerThread: DbWorkerThread
    private var expenses: List<ExpenseEntry> = ArrayList()
    private var gridViewAdapter : ExpenseViewAdapter? = null
    private var updateUIHandler : Handler = object : Handler(Looper.getMainLooper()){
        override fun handleMessage(inputMessage: Message) {
            gridViewAdapter = ExpenseViewAdapter(applicationContext,expenses)
            monthlyExpenseTable.invalidate()
            for (expense in expenses){
                var inflator = applicationContext!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
                var expenseEntryView = inflator.inflate(R.layout.monthly_expense_entry, null)
                expenseEntryView.amountCellText.text = expense.amount.toString()
                expenseEntryView.categoryCellText.text = expense.category.toString()
                expenseEntryView.descriptionCellText.text = expense.description.toString()
                expenseEntryView.dateTimeCellText.text = expense.createTime.toString()
                monthlyExpenseTable.addView(expenseEntryView)
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_monthly_expenses)
        val startTime = DateUtil.findStartOfMonth()
        val endTime = DateUtil.findEndOfMonth()
        dbWorkerThread = DbWorkerThread("dbWorkerThread")
        dbWorkerThread.start()
        expenseDatabase = ExpenseDatabase.getInstance(this)
        val readTask = Runnable { var allEntries = expenseDatabase?.expenseDataDao()?.getAllForTimeRange(startTime,endTime)
            expenses = allEntries!!
            updateUIHandler.obtainMessage().sendToTarget()
        }
        dbWorkerThread.postTask(readTask)

    }
}


