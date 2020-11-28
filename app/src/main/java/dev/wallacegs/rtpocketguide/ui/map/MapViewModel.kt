package dev.wallacegs.rtpocketguide.ui.map

import android.util.Log
import androidx.lifecycle.ViewModel

private const val TAG = "MAPFragment"

class MapViewModel : ViewModel() {
    init {
        Log.i(TAG, "MapViewModel created!")
    }

    override fun onCleared() {
        super.onCleared()
        Log.i(TAG, "MapViewModel destroyed!")
    }
}