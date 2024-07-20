package com.adinda.gotrash.presentation.home

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.adinda.gotrash.R
import com.adinda.gotrash.databinding.FragmentHomeBinding
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeViewModel by viewModel()
    private lateinit var pieChart: PieChart

    private val requestLocationPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            openGoogleMaps()
        } else {
            // Permission denied
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUsername()
        setupMapClickListener()
        bindTpsData()
        setupPieChart()
    }

    private fun bindTpsData() {
        viewModel.trash.observe(viewLifecycleOwner) {
            with(binding) {
                statusValue.text = String.format("%.1f", it.volume) + " Cm³"
                updatePieChart(it.volume.toFloat(), 6000f) // Contoh max value
            }
        }
    }

    private fun setupPieChart() {
        pieChart = binding.pieChart
        pieChart.description.isEnabled = false
        pieChart.isRotationEnabled = false
        pieChart.legend.isEnabled = false // Disable the legend
        pieChart.setDrawEntryLabels(false) // Disable entry labels
    }

    private fun updatePieChart(currentValue: Float, maxValue: Float) {
        val entries = listOf(
            PieEntry(currentValue, "Filled"),
            PieEntry(maxValue - currentValue, "Remaining")
        )
        val dataSet = PieDataSet(entries, "Status TPS")
        dataSet.colors = listOf(
            ContextCompat.getColor(requireContext(), R.color.green),
            ContextCompat.getColor(requireContext(), R.color.white)
        )
        dataSet.valueTextColor = ContextCompat.getColor(requireContext(), R.color.black)
        dataSet.valueTextSize = 12f
        dataSet.sliceSpace = 3f // Space between slices
        dataSet.selectionShift = 10f // Shift when a slice is selected
        dataSet.setDrawValues(true) // Enable value text

        // Adding shadow
        pieChart.setDrawSliceText(false)
        pieChart.setDrawHoleEnabled(false)
        pieChart.setDrawEntryLabels(true)
        pieChart.setEntryLabelColor(ContextCompat.getColor(requireContext(), R.color.black))
        pieChart.setDrawSlicesUnderHole(false)

        val data = PieData(dataSet)
        pieChart.data = data
        pieChart.setExtraOffsets(5f, 10f, 5f, 5f) // Offset for shadow
        pieChart.invalidate() // Refresh the chart
    }

    private fun setupUsername() {
        binding.usernameText.text = viewModel.getCurrentUser()?.username.orEmpty()
    }

    private fun setupMapClickListener() {
        binding.mapCard.setOnClickListener {
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED) {
                checkAndEnableGPS()
            } else {
                requestLocationPermissionLauncher.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
            }
        }
    }

    private fun checkAndEnableGPS() {
        val locationManager = requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)

        if (!isGPSEnabled) {
            val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            startActivity(intent)
        } else {
            openGoogleMaps()
        }
    }

    private fun openGoogleMaps() {
        val latitude = -7.782167 // Ganti dengan latitude yang diinginkan
        val longitude = 110.415181 // Ganti dengan longitude yang diinginkan
        val gmmIntentUri = Uri.parse("geo:$latitude,$longitude?q=$latitude,$longitude")
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
        mapIntent.setPackage("com.google.android.apps.maps")
        if (mapIntent.resolveActivity(requireActivity().packageManager) != null) {
            startActivity(mapIntent)
        } else {
            // Jika aplikasi Google Maps tidak tersedia, Anda bisa menampilkan pesan atau mengambil tindakan lain
            // Misalnya, buka browser dengan URL Google Maps
            val browserIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            startActivity(browserIntent)
        }
    }
}
