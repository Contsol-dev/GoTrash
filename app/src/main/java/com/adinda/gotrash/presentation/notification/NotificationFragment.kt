package com.adinda.gotrash.presentation.notification

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.adinda.gotrash.R
import com.adinda.gotrash.data.model.Notification
import com.adinda.gotrash.databinding.FragmentNotificationBinding
import com.adinda.gotrash.presentation.notification.adapter.NotificationsAdapter
import com.adinda.gotrash.utils.proceedWhen
import org.koin.androidx.viewmodel.ext.android.viewModel

class NotificationFragment : Fragment() {

    private val viewModel: NotificationViewModel by viewModel()
    private lateinit var binding: FragmentNotificationBinding
    private val notificationAdapter: NotificationsAdapter by lazy {
        NotificationsAdapter { id ->
            viewModel.markAsRead(id)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNotificationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setNotification()
        fetchNotification()
    }

    private fun fetchNotification() {
        viewModel.notifications.observe(viewLifecycleOwner) {
            it.proceedWhen(
                doOnSuccess = {
                    it.payload?.let { data -> submitData(data) }
                },
                doOnError = {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.error_notif),
                        Toast.LENGTH_SHORT,
                    ).show()
                }
            )
        }
    }

    private fun submitData(data: List<Notification>) {
        notificationAdapter.submitDataNotification(data)
    }

    private fun setNotification() {
        binding.recyclerView.apply {
            adapter = notificationAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }
}
