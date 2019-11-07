package com.snsoft.mybudget

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.animation.Easing
import android.view.WindowManager
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.github.mikephil.charting.utils.ColorTemplate
import com.github.mikephil.charting.utils.MPPointF
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import kotlin.collections.ArrayList
import androidx.core.app.ComponentActivity
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.os.Handler
import android.os.Looper
import android.os.Message
import java.util.logging.Logger


class MonthlyExpensesChartActivity : AppCompatActivity(),OnChartValueSelectedListener  {

    private var chart: PieChart? = null

    private var expenseDatabase : ExpenseDatabase? = null
    private lateinit var dbWorkerThread: DbWorkerThread
    private var expenses: List<ExpenseEntry> = ArrayList()
    private var categoryExpensesMap: MutableMap<String?, MutableList<ExpenseEntry>> = mutableMapOf()

    private var updateUIHandler : Handler = object : Handler(Looper.getMainLooper()){
        override fun handleMessage(inputMessage: Message) {
            val categoryMap = mutableMapOf<String?,Double>();
            for(expense in expenses){
                val category = ExpenseCategory.getCategory(expense.category);
                if(!categoryMap.containsKey(category)){
                    categoryMap[category] = 0.0
                    categoryExpensesMap[category] = arrayListOf<ExpenseEntry>()
                }
                categoryMap[category] = categoryMap[category]!! + (expense.amount);
                categoryExpensesMap[category]?.add(expense)
            }
            setData(categoryMap)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_monthly_expenses_chart)
        title = "Monthly Expenses"

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

        chart = findViewById(R.id.chart1)
        chart?.setUsePercentValues(true)
        chart?.getDescription()?.setEnabled(false)
        chart?.setExtraOffsets(5f, 10f, 5f, 5f)

        chart?.setDragDecelerationFrictionCoef(0.95f)

        //chart?.setCenterTextTypeface(tfLight)
        chart?.setCenterText("Monthly Expenses")

        chart?.setDrawHoleEnabled(true)
        chart?.setHoleColor(Color.WHITE)

        chart?.setTransparentCircleColor(Color.WHITE)
        chart?.setTransparentCircleAlpha(110)

        chart?.setHoleRadius(58f)
        chart?.setTransparentCircleRadius(61f)

        chart?.setDrawCenterText(true)

        chart?.setRotationAngle(0.0f)

        chart?.setRotationEnabled(true)
        chart?.setHighlightPerTapEnabled(true)

        chart?.setOnChartValueSelectedListener(this)

        chart?.animateY(1400, Easing.EaseInOutQuad)
        // chart.spin(2000, 0, 360);

        val l = chart?.getLegend()
        l?.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP)
        l?.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT)
        l?.setOrientation(Legend.LegendOrientation.VERTICAL)
        l?.setDrawInside(false)
        l?.setXEntrySpace(7f)
        l?.setYEntrySpace(0f)
        l?.setYOffset(0f)

        // entry label styling
        chart?.setEntryLabelColor(Color.WHITE)
        //chart?.setEntryLabelTypeface(tfRegular)
        chart?.setEntryLabelTextSize(12f)

    }

    private fun setData(categoryMap:Map<String?,Double> ) {
        val entries = ArrayList<PieEntry>()

        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
        // the chart.
        for ((category, expense) in categoryMap) {
            entries.add(PieEntry(expense.toFloat(),category))
        }

        val dataSet = PieDataSet(entries, "Monthly Expenses")

        dataSet.setDrawIcons(false)

        dataSet.sliceSpace = 3f
        dataSet.iconsOffset = MPPointF(0f, 40f)
        dataSet.selectionShift = 5f

        // add a lot of colors

        val colors = ArrayList<Int>()

        for (c in ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c)

        for (c in ColorTemplate.JOYFUL_COLORS)
            colors.add(c)

        for (c in ColorTemplate.COLORFUL_COLORS)
            colors.add(c)

        for (c in ColorTemplate.LIBERTY_COLORS)
            colors.add(c)

        for (c in ColorTemplate.PASTEL_COLORS)
            colors.add(c)

        colors.add(ColorTemplate.getHoloBlue())

        dataSet.setColors(colors)
        //dataSet.setSelectionShift(0f);

        val data = PieData(dataSet)
        data.setValueFormatter(PercentFormatter(chart))
        data.setValueTextSize(11f)
        data.setValueTextColor(Color.WHITE)
        //data.setValueTypeface(tfLight)
        chart?.setData(data)

        // undo all highlights
        chart?.highlightValues(null)

        chart?.invalidate()
    }

    override fun onValueSelected(e:Entry, h:Highlight) {
        Logger.getLogger("chart").info("OnValueSelected"+e.toString()+h.toString())
        val pieEntry = e as PieEntry
        val intent = Intent(this, MonthlyExpensesActivity::class.java)
        intent.putExtra("FETCH_MONTHLY_DATA", false)
        intent.putParcelableArrayListExtra("EXPENSE_ENTRIES", categoryExpensesMap[pieEntry.label] as ArrayList<ExpenseEntry>)
        startActivityForResult(intent, 0)
    }

    override fun onNothingSelected() {}
}