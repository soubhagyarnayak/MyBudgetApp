package com.snsoft.mybudget

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText

import kotlinx.android.synthetic.main.activity_main.*
import java.util.logging.Logger

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        val categories = arrayOf("Food","Grocery","Travel","Bills","Medical","Extra")
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
                Logger.getLogger(MainActivity::class.java.name).info(String.format("Amount:%s,Description:%s,Category:%s",amountText.text.toString(),descriptionText.text.toString(),categorySpinner.selectedItem.toString()))
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
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
