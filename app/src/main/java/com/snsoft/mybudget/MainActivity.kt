package com.snsoft.mybudget

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText

import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import java.util.logging.Logger

class MainActivity : AppCompatActivity() {
    private var expenseDatabase : ExpenseDatabase? = null
    private lateinit var dbWorkerThread: DbWorkerThread

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        dbWorkerThread = DbWorkerThread("dbWorkerThread")
        dbWorkerThread.start()
        expenseDatabase = ExpenseDatabase.getInstance(this)
        val categories = arrayOf("Extra","Food","Grocery","Travel","Bills","Medical")
        val categorySpinner = findViewById<Spinner>(R.id.categorySpinner)
        if (categorySpinner != null) {
            val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categories)
            categorySpinner.adapter = adapter
        }
        val amountText = findViewById<EditText>(R.id.amountText)
        val descriptionText = findViewById<EditText>(R.id.descriptionText)
        val addButton = findViewById<Button>(R.id.addButton);
        if(addButton != null){
            addButton.setOnClickListener{
                val expenseEntry:ExpenseEntry = ExpenseEntry(null,descriptionText.text.toString(),amountText.text.toString().toDouble(),categories.indexOf(categorySpinner.selectedItem.toString()),Calendar.getInstance().time,Calendar.getInstance().time)
                val task = Runnable { expenseDatabase?.expenseDataDao()?.insert(expenseEntry) }
                dbWorkerThread.postTask(task)
                val readTask = Runnable {
                    var allEntries = expenseDatabase?.expenseDataDao()?.getAll()
                }
                dbWorkerThread.postTask(readTask)

            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.monthly_expense_details -> {
                showMonthlyExpense()
                true
            }
            R.id.monthly_expense_chart -> {
                showMonthlyExpenseChart()
                true
            }
            R.id.expense_details -> {
                showAllExpense()
                true
            }
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun showMonthlyExpense(){
        val intent = Intent(this, MonthlyExpensesActivity::class.java)
        intent.putExtra("FETCH_MONTHLY_DATA", true)
        startActivityForResult(intent, 0)
    }

    fun showMonthlyExpenseChart(){
        val intent = Intent(this, MonthlyExpensesChartActivity::class.java)
        startActivityForResult(intent, 0)
    }

    fun showAllExpense() {

    }
}
