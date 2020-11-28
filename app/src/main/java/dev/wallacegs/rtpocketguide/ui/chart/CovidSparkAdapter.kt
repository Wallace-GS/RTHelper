package dev.wallacegs.rtpocketguide.ui.chart

import android.graphics.RectF
import com.robinhood.spark.SparkAdapter
import dev.wallacegs.rtpocketguide.data.CovidData
import dev.wallacegs.rtpocketguide.ui.chart.Metric
import dev.wallacegs.rtpocketguide.ui.chart.TimeScale

class CovidSparkAdapter(private val dailyData: List<CovidData>) : SparkAdapter() {

    var metric = Metric.POSITIVE
    var daysAgo = TimeScale.MAX

    override fun getY(index: Int): Float {
        return when(metric) {
            Metric.NEGATIVE -> dailyData[index].negativeIncrease.toFloat()
            Metric.POSITIVE -> dailyData[index].positiveIncrease.toFloat()
            Metric.DEATH -> dailyData[index].deathIncrease.toFloat()
        }
    }

    override fun getItem(index: Int) = dailyData[index]

    override fun getCount() = dailyData.size

    override fun getDataBounds(): RectF {
        val bounds = super.getDataBounds()
        if (daysAgo != TimeScale.MAX) bounds.left = count - daysAgo.numDays.toFloat()
        return bounds
    }

}
