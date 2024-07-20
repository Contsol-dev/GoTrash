package com.adinda.gotrash.presentation.notification

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.adinda.gotrash.data.local.room.NotificationDatabase
import com.adinda.gotrash.databinding.FragmentNotificationBinding

class NotificationFragment : Fragment() {

    private val viewModel: NotificationViewModel by viewModels()
    private lateinit var binding: FragmentNotificationBinding
    private lateinit var adapter: NotificationAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNotificationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = NotificationAdapter()
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = adapter

        NotificationDatabase.getDatabase(requireContext()).notificationDao().getAllNotifications().observe(viewLifecycleOwner) {
            adapter.setNotifications(it)
        }
    }
}
