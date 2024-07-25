package com.adinda.gotrash.presentation.notification.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.adinda.gotrash.R
import com.adinda.gotrash.data.model.Notification
import com.adinda.gotrash.databinding.NotificationCardBinding
import com.adinda.gotrash.utils.DateUtils.timeAgo

class NotificationsAdapter(
    private val onReadClick: (Int) -> Unit // Add this line
) : RecyclerView.Adapter<NotificationsAdapter.NotificationViewHolder>() {
    private var asyncDataDiffer =
        AsyncListDiffer(
            this,
            object : DiffUtil.ItemCallback<Notification>() {
                override fun areItemsTheSame(
                    oldItem: Notification,
                    newItem: Notification,
                ): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(
                    oldItem: Notification,
                    newItem: Notification,
                ): Boolean {
                    return oldItem.hashCode() == newItem.hashCode()
                }
            },
        )

    fun submitDataNotification(data: List<Notification>) {
        asyncDataDiffer.submitList(data)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): NotificationViewHolder {
        val binding =
            NotificationCardBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return NotificationViewHolder(binding)
    }

    override fun getItemCount(): Int = asyncDataDiffer.currentList.size

    override fun onBindViewHolder(
        holder: NotificationViewHolder,
        position: Int,
    ) {
        holder.bind(asyncDataDiffer.currentList[position])
    }

    inner class NotificationViewHolder(
        private val binding: NotificationCardBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(notification: Notification) {
            binding.textViewDate.text = timeAgo(notification.sentAt)
            if (notification.isRead == 1) {
                binding.root.setCardBackgroundColor(
                    ContextCompat.getColor(
                        binding.root.context,
                        R.color.passiveBackground
                    )
                )
                binding.textViewTitle.setTextColor(
                    ContextCompat.getColor(
                        binding.root.context,
                        R.color.passiveText
                    )
                )
                binding.textViewMessage.setTextColor(
                    ContextCompat.getColor(
                        binding.root.context,
                        R.color.passiveText
                    )
                )
                binding.imageViewIcon.setColorFilter(
                    ContextCompat.getColor(
                        binding.root.context,
                        R.color.passiveText
                    )
                )
                binding.tvRead.visibility = View.GONE
            } else {
                binding.root.setCardBackgroundColor(
                    ContextCompat.getColor(
                        binding.root.context,
                        R.color.activeBackground
                    )
                )
                binding.textViewTitle.setTextColor(
                    ContextCompat.getColor(
                        binding.root.context,
                        R.color.activeText
                    )
                )
                binding.textViewMessage.setTextColor(
                    ContextCompat.getColor(
                        binding.root.context,
                        R.color.activeText
                    )
                )
                binding.imageViewIcon.setColorFilter(
                    ContextCompat.getColor(
                        binding.root.context,
                        R.color.activeText
                    )
                )
                binding.tvRead.visibility = View.VISIBLE
            }

            binding.tvRead.setOnClickListener {
                onReadClick(notification.id)
            }
        }
    }
}