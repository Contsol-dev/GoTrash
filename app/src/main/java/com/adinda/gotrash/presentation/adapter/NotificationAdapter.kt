package com.adinda.gotrash.presentation.notification

import android.app.NotificationManager
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.adinda.gotrash.R
import com.adinda.gotrash.data.local.model.Notification
import com.adinda.gotrash.databinding.NotificationCardBinding

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
            binding.textViewDate.text = notification.time

            if (notification.isRead) {
                binding.root.setCardBackgroundColor(ContextCompat.getColor(binding.root.context, R.color.passiveText))
                        binding.root.setCardBackgroundColor(ContextCompat.getColor(binding.root.context, R.color.passiveBackground))
                        binding.textViewTitle.setTextColor(ContextCompat.getColor(binding.root.context, R.color.passiveText))
                        binding.textViewMessage.setTextColor(ContextCompat.getColor(binding.root.context, R.color.passiveText))
                        binding.imageViewIcon.setColorFilter(ContextCompat.getColor(binding.root.context, R.color.passiveText)) // Set trash icon to gray
                        binding.tvRead.visibility = View.GONE
            } else {
                showNotification(binding.root.context, notification.title, notification.message)
                binding.root.setCardBackgroundColor(ContextCompat.getColor(binding.root.context, R.color.activeBackground))
                binding.textViewTitle.setTextColor(ContextCompat.getColor(binding.root.context, R.color.activeText))
                binding.textViewMessage.setTextColor(ContextCompat.getColor(binding.root.context, R.color.activeText))
                binding.imageViewIcon.setColorFilter(ContextCompat.getColor(binding.root.context, R.color.activeText)) // Set trash icon to default color
                binding.tvRead.visibility = View.VISIBLE
            }

            binding.tvRead.setOnClickListener {
                notification.isRead = true
                updateNotification(notification)
                binding.root.setCardBackgroundColor(ContextCompat.getColor(binding.root.context, R.color.passiveBackground))
                binding.textViewTitle.setTextColor(ContextCompat.getColor(binding.root.context, R.color.passiveText))
                binding.textViewMessage.setTextColor(ContextCompat.getColor(binding.root.context, R.color.passiveText))
                binding.imageViewIcon.setColorFilter(ContextCompat.getColor(binding.root.context, R.color.passiveText)) // Set trash icon to gray
                binding.tvRead.visibility = View.GONE
            }
        }
    }

    private fun showNotification(context: Context, title: String, message: String) {
        val builder = NotificationCompat.Builder(context, "TrashNotificationChannel")
            .setSmallIcon(R.drawable.ic_trash)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(1, builder.build())
    }
}
