package com.adinda.gotrash.presentation.notification

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.adinda.gotrash.data.local.model.Notification
import com.adinda.gotrash.databinding.NotificationCardBinding
import kotlinx.coroutines.launch

class NotificationAdapter(private val updateNotification: (Notification) -> Unit) : RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder>() {

    private var notifications: List<Notification> = emptyList()

    fun setNotifications(notifications: List<Notification>) {
        this.notifications = notifications.reversed()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationViewHolder {
        val binding = NotificationCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NotificationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NotificationViewHolder, position: Int) {
        holder.bind(notifications[position])
    }

    override fun getItemCount() = notifications.size

    inner class NotificationViewHolder(private val binding: NotificationCardBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(notification: Notification) {
            binding.textViewTitle.text = notification.title
            binding.textViewMessage.text = notification.message

            if (notification.isRead) {
                binding.root.setCardBackgroundColor(Color.LTGRAY)
                binding.buttonRead.visibility = View.GONE
            } else {
                binding.root.setCardBackgroundColor(Color.WHITE)
                binding.buttonRead.visibility = View.VISIBLE
            }

            binding.buttonRead.setOnClickListener {
                notification.isRead = true
                updateNotification(notification)
                binding.root.setCardBackgroundColor(Color.LTGRAY)
                binding.buttonRead.visibility = View.GONE
            }
        }
    }
}
