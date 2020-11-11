package dev.wallacegs.rtpocketguide

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import dev.wallacegs.rtpocketguide.databinding.FragmentMAPBinding

private const val TAG = "MAPFragment"

class MAPFragment : Fragment() {

    private var _binding: FragmentMAPBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMAPBinding.inflate(inflater, container, false)
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
            updateDisplay(true)
            calculateMAP(systolic, diastolic)
        }
        binding.btnRecalculate.setOnClickListener {
            updateDisplay(false)
        }
    }

    private fun updateDisplay(updateFlag: Boolean) {
        if (updateFlag) {
            binding.tvSystolic.visibility = View.INVISIBLE
            binding.etSystolic.visibility = View.INVISIBLE
            binding.tvDiastolic.visibility = View.INVISIBLE
            binding.etDiastolic.visibility = View.INVISIBLE
            binding.tvInstruction.visibility = View.INVISIBLE
            binding.btnCalculate.visibility = View.INVISIBLE

            binding.tvNumResult.visibility = View.VISIBLE
            binding.tvResult.visibility = View.VISIBLE
            binding.tvRange.visibility = View.VISIBLE
            binding.btnRecalculate.visibility = View.VISIBLE
        } else {
            binding.tvSystolic.visibility = View.VISIBLE
            binding.etSystolic.visibility = View.VISIBLE
            binding.tvDiastolic.visibility = View.VISIBLE
            binding.etDiastolic.visibility = View.VISIBLE
            binding.tvInstruction.visibility = View.VISIBLE
            binding.btnCalculate.visibility = View.VISIBLE

            binding.tvNumResult.visibility = View.INVISIBLE
            binding.tvResult.visibility = View.INVISIBLE
            binding.tvRange.visibility = View.INVISIBLE
            binding.btnRecalculate.visibility = View.INVISIBLE
            resetResults()
        }
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