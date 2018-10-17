package com.snsoft.mybudget

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import kotlinx.android.synthetic.main.activity_monthly_expenses.*
import kotlinx.android.synthetic.main.monthly_expense_entry.view.*
import java.util.*
import java.util.logging.Logger
import kotlin.collections.ArrayList

class MonthlyExpensesActivity : AppCompatActivity() {
    private var expenseDatabase : ExpenseDatabase? = null
    private lateinit var dbWorkerThread: DbWorkerThread
    private var expenses: List<ExpenseEntry> = ArrayList()
    private var gridViewAdapter : GridViewAdapter? = null
    private var updateUIHandler : Handler = object : Handler(Looper.getMainLooper()){
        override fun handleMessage(inputMessage: Message) {
            Logger.getLogger("handler").warning("in the handler")
            Logger.getLogger("handler").warning(expenses.count().toString())
            gridViewAdapter = GridViewAdapter(applicationContext,expenses)
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
            Logger.getLogger("handler").warning("in the handler2")
        }

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_monthly_expenses)

        dbWorkerThread = DbWorkerThread("dbWorkerThread")
        dbWorkerThread.start()
        expenseDatabase = ExpenseDatabase.getInstance(this)
        val readTask = Runnable { var allEntries = expenseDatabase?.expenseDataDao()?.getAll()
            expenses = allEntries!!
            updateUIHandler.obtainMessage().sendToTarget()
        }
        dbWorkerThread.postTask(readTask)

    }
}


