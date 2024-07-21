package com.adinda.gotrash.presentation.home

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.adinda.gotrash.R
import com.adinda.gotrash.data.local.model.Notification
import com.adinda.gotrash.data.local.room.NotificationDatabase
import com.adinda.gotrash.databinding.FragmentHomeBinding
import com.adinda.gotrash.utils.DateUtils
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.time.Instant

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeViewModel by viewModel()
    private lateinit var pieChart: PieChart
    private var notificationSent = false // Flag to track notification state

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

    @SuppressLint("DefaultLocale")
    private fun bindTpsData() {
        viewModel.trash.observe(viewLifecycleOwner) {
            with(binding) {
                pbLoadingLogin.visibility = View.VISIBLE // Show loading state

                if (it != null) {
                    val currentVolume = it.volume.toFloat()
                    val maxVolume = 6000f // Example max value

                    statusValue.text = String.format("%.1f", currentVolume) + " Cm³"
                    updatePieChart(currentVolume, maxVolume)

                    // Show PieChart and statusValue
                    pieChart.visibility = View.VISIBLE
                    statusValue.visibility = View.VISIBLE
                    statusText.visibility = View.VISIBLE
                    statusSubtext.visibility = View.VISIBLE

                    // Check volume range and create notification if within range
                    if (currentVolume >= 3000 && currentVolume <= maxVolume) {
                        if (!notificationSent) {
                            createNotification("Reminder!!", "Your trash bin in TPS 001 is full.")
                            notificationSent = true
                        }
                    } else {
                        notificationSent = false
                    }
                }

                pbLoadingLogin.visibility = View.GONE // Hide loading state after processing
            }
        }
    }

    private fun createNotification(title: String, message: String) {
        lifecycleScope.launch {
            val currentTimeDate = DateUtils.formatDate(Instant.now().toString())
            val currentTime = DateUtils.formatTime(Instant.now().toString())
            val notification = Notification(title = title, message = message, time = "$currentTimeDate $currentTime")
            NotificationDatabase.getDatabase(requireContext()).notificationDao().insert(notification)
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
        @Suppress("DEPRECATION")
        pieChart.setDrawSliceText(false)
        pieChart.isDrawHoleEnabled = false
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
        val latitude = -7.782167 // Replace with the desired latitude
        val longitude = 110.415181 // Replace with the desired longitude
        val gmmIntentUri = Uri.parse("geo:$latitude,$longitude?q=$latitude,$longitude")
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
        mapIntent.setPackage("com.google.android.apps.maps")
        if (mapIntent.resolveActivity(requireActivity().packageManager) != null) {
            startActivity(mapIntent)
        } else {
            // If Google Maps app is not available, you can display a message or take another action
            // For example, open the browser with the Google Maps URL
            val browserIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            startActivity(browserIntent)
        }
    }
}