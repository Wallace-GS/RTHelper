package dev.wallacegs.rtpocketguide.ui.map

import android.util.Log
import androidx.lifecycle.ViewModel
import dev.wallacegs.rtpocketguide.R

private const val TAG = "MAPFragment"

class MapViewModel : ViewModel() {

    private var result: Float = 0F
    var color: Int = 0
    var resultText: String = ""
    var numResultText: String = ""

    init {
        Log.i(TAG, "MapViewModel created!")
    }

    override fun onCleared() {
        super.onCleared()
        Log.i(TAG, "MapViewModel destroyed!")
    }

    fun calculateMap(systolic: Float, diastolic: Float) {
        result = ((systolic + (2 * diastolic)) / 3)
        setResultText()
        setNumResultText()
        setColor()
    }

    private fun setColor() {
        color = when {
            result < 80 || result > 100  -> R.color.colorDeath
            else -> R.color.colorNegative
        }
    }

    private fun setResultText() {
        resultText = when {
            result < 80 -> "Low"
            result > 100 -> "High"
            else -> "Normal"
        }
    }

    private fun setNumResultText() {
        numResultText = "%.1f mmHg".format(result)
    }
}