package com.adinda.gotrash.presentation.home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.adinda.gotrash.databinding.FragmentHomeBinding
import com.adinda.gotrash.presentation.maps.MapsActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeViewModel by viewModel()

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
    }

    private fun bindTpsData() {
        viewModel.trash.observe(viewLifecycleOwner) {
            with(binding){
                dist1.text = it.dist1.toString()
                dist2.text = it.dist2.toString()
                distavg.text = it.distavg.toString()
                distv.text = it.distvl53l0x.toString()
                height.text = it.trashheight.toString()
                volume.text = it.volume.toString()
            }
        }
    }

    private fun setupUsername() {
        binding.usernameText.text = viewModel.getCurrentUser()?.username.orEmpty()
    }

    private fun setupMapClickListener() {
        binding.mapCard.setOnClickListener {
            val intent = Intent(activity, MapsActivity::class.java)
            startActivity(intent)
        }
    }
}
