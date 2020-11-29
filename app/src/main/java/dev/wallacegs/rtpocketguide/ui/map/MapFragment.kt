package dev.wallacegs.rtpocketguide.ui.map

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
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
            displayMap(systolic, diastolic)
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

    private fun displayMap(systolic: Float, diastolic: Float) {
        viewModel.calculateMap(systolic, diastolic)
        @ColorInt val colorInt = context?.let { ContextCompat.getColor(it, viewModel.color) }
        binding.tvResult.text = viewModel.resultText
        binding.tvNumResult.text = viewModel.numResultText
        if (colorInt != null) binding.tvResult.setTextColor(colorInt)
    }
}