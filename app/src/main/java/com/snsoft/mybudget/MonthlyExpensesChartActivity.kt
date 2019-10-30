package com.snsoft.mybudget

import android.graphics.Color
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.animation.Easing
import android.view.WindowManager
import android.widget.TextView
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
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
import android.text.style.ForegroundColorSpan
import android.graphics.Typeface
import android.text.style.StyleSpan
import android.text.style.RelativeSizeSpan
import android.text.SpannableString
import androidx.core.app.ComponentActivity
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import java.util.logging.Level
import java.util.logging.Logger


class MonthlyExpensesChartActivity : AppCompatActivity(),OnSeekBarChangeListener,OnChartValueSelectedListener  {

    private var chart: PieChart? = null
    private var seekBarX: SeekBar? = null
    private var seekBarY: SeekBar? = null
    private var tvX: TextView? = null
    private var tvY: TextView? = null

    protected val parties = arrayOf("Party A", "Party B", "Party C", "Party D", "Party E", "Party F", "Party G", "Party H", "Party I", "Party J", "Party K", "Party L", "Party M", "Party N", "Party O", "Party P", "Party Q", "Party R", "Party S", "Party T", "Party U", "Party V", "Party W", "Party X", "Party Y", "Party Z")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Logger.getLogger("chart").log(Level.WARNING,"inside the chart module")
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_monthly_expenses_chart)
        title = "PieChartActivity"

        //
        tvX = findViewById(R.id.tvXMax)
        tvY = findViewById(R.id.tvYMax)

        seekBarX = findViewById(R.id.seekBar1)
        seekBarY = findViewById(R.id.seekBar2)

        seekBarX?.setOnSeekBarChangeListener(this)
        seekBarY?.setOnSeekBarChangeListener(this)

        chart = findViewById(R.id.chart1)
        chart?.setUsePercentValues(true)
        chart?.getDescription()?.setEnabled(false)
        chart?.setExtraOffsets(5f, 10f, 5f, 5f)

        chart?.setDragDecelerationFrictionCoef(0.95f)

        //chart?.setCenterTextTypeface(tfLight)
        chart?.setCenterText(generateCenterSpannableText())

        chart?.setDrawHoleEnabled(true)
        chart?.setHoleColor(Color.WHITE)

        chart?.setTransparentCircleColor(Color.WHITE)
        chart?.setTransparentCircleAlpha(110)

        chart?.setHoleRadius(58f)
        chart?.setTransparentCircleRadius(61f)

        chart?.setDrawCenterText(true)

        chart?.setRotationAngle(0.0f)
        // enable rotation of the chart by touch
        chart?.setRotationEnabled(true)
        chart?.setHighlightPerTapEnabled(true)

        // chart.setUnit(" €");
        // chart.setDrawUnitsInChart(true);

        // add a selection listener
        chart?.setOnChartValueSelectedListener(this)

        seekBarX?.setProgress(4)
        seekBarY?.setProgress(10)

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
        //
        Logger.getLogger("chart").log(Level.WARNING,"inside the chart module2")
    }

    private fun setData(count: Int?, range: Int?) {
        Logger.getLogger("chart").log(Level.WARNING,"inside the chart module3")
        val entries = ArrayList<PieEntry>()

        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
        // the chart.
        for (i in 0 until count!!) {
            entries.add(PieEntry((Math.random() * range!! + range!! / 5).toFloat(),
                    parties[i % parties.size],
                    resources.getDrawable(R.drawable.star)))
        }

        val dataSet = PieDataSet(entries, "Election Results")

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
        Logger.getLogger("chart").log(Level.WARNING,"inside the chart module4")
    }

    override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {

        tvX?.setText(seekBarX?.getProgress().toString())
        tvY?.setText(seekBarY?.getProgress().toString())

        setData(seekBarX?.getProgress(), seekBarY?.getProgress())
        Logger.getLogger("chart").log(Level.WARNING,"inside the chart module5")
    }

    private fun generateCenterSpannableText(): SpannableString {
        Logger.getLogger("chart").log(Level.WARNING,"inside the chart module6")
        val s = SpannableString("MPAndroidChart\ndeveloped by Philipp Jahoda")
        s.setSpan(RelativeSizeSpan(1.7f), 0, 14, 0)
        s.setSpan(StyleSpan(Typeface.NORMAL), 14, s.length - 15, 0)
        s.setSpan(ForegroundColorSpan(Color.GRAY), 14, s.length - 15, 0)
        s.setSpan(RelativeSizeSpan(.8f), 14, s.length - 15, 0)
        s.setSpan(StyleSpan(Typeface.ITALIC), s.length - 14, s.length, 0)
        s.setSpan(ForegroundColorSpan(ColorTemplate.getHoloBlue()), s.length - 14, s.length, 0)
        return s

    }

    override fun onStartTrackingTouch(seekBar: SeekBar) {}

    override fun onStopTrackingTouch(seekBar: SeekBar) {}

    override fun onValueSelected(e:Entry, h:Highlight) {}

    override fun onNothingSelected() {}
}