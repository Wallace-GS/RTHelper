package dev.wallacegs.rtpocketguide.ui.map

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import dev.wallacegs.rtpocketguide.R
import dev.wallacegs.rtpocketguide.databinding.FragmentMapBinding
import dev.wallacegs.rtpocketguide.hideKeyboard

private const val TAG = "MAPFragment"

class MAPFragment : Fragment() {

    private var _binding: FragmentMapBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: MapViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMapBinding.inflate(inflater, container, false)

        Log.i(TAG, "Called ViewModelProvider()")
        viewModel = ViewModelProvider(this).get(MapViewModel::class.java)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // TODO: add error handling for empty EditTexts
        binding.btnCalculate.setOnClickListener {
            binding.btnCalculate.isEnabled = true
            val systolic = binding.etSystolic.text.toString().toFloat()
            val diastolic = binding.etDiastolic.text.toString().toFloat()
            calculateMAP(systolic, diastolic)
            hideKeyboard()
            showResults()
        }
        binding.btnRecalculate.setOnClickListener {
            hideResults()
        }
    }

    private fun hideResults() {
        binding.resultGroup.visibility = View.INVISIBLE
        binding.mainGroup.visibility = View.VISIBLE
        resetResults()
    }

    private fun showResults() {
        binding.mainGroup.visibility = View.INVISIBLE
        binding.resultGroup.visibility = View.VISIBLE
    }

    private fun resetResults() {
        binding.etSystolic.text.clear()
        binding.etDiastolic.text.clear()
    }

    @SuppressLint("SetTextI18n")
    private fun calculateMAP(systolic: Float, diastolic: Float) {
        val result = ((systolic + (2 * diastolic)) / 3)
        val color = when {
            result < 80 || result > 100  -> R.color.colorDeath
            else -> R.color.colorNegative
        }
        @ColorInt val colorInt = context?.let { ContextCompat.getColor(it, color) }

        if (result < 80) {
            binding.tvResult.text = "Low"
            if (colorInt != null) binding.tvResult.setTextColor(colorInt)
        } else if (result > 100) {
            binding.tvResult.text = "High"
            if (colorInt != null) binding.tvResult.setTextColor(colorInt)
        } else {
            binding.tvResult.text = "Normal"
            if (colorInt != null) binding.tvResult.setTextColor(colorInt)
        }
        binding.tvNumResult.text = "%.1f mmHg".format(result)
    }

}