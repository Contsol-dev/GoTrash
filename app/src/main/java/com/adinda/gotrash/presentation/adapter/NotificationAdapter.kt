package com.adinda.gotrash.presentation.notification

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.adinda.gotrash.data.local.model.Notification
import com.adinda.gotrash.databinding.NotificationCardBinding

class NotificationAdapter : RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder>() {

    private var notifications: List<Notification> = emptyList()

    fun setNotifications(notifications: List<Notification>) {
        this.notifications = notifications
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

            binding.buttonRead.setOnClickListener {
                // Set the CardView to passive state
                binding.root.setCardBackgroundColor(Color.LTGRAY)
                binding.buttonRead.visibility = View.GONE
            }
        }
    }
}
