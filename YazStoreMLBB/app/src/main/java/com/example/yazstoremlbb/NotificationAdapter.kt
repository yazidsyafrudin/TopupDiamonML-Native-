package com.example.yazstoremlbb

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

/**
 * Adapter untuk menghubungkan data Notifikasi dengan RecyclerView di NotifikasiActivity.
 * @param initialNotifications Daftar awal notifikasi yang akan ditampilkan.
 */
class NotificationAdapter(private var notifications: List<Notification>) : RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder>() {

    /**
     * ViewHolder menyimpan referensi ke semua komponen UI di dalam satu item layout (item_notification.xml).
     */
    inner class NotificationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Hubungkan view dari item_notification.xml
        val icon: ImageView = itemView.findViewById(R.id.ivNotificationIcon)
        val title: TextView = itemView.findViewById(R.id.tvNotificationTitle)
        val body: TextView = itemView.findViewById(R.id.tvNotificationBody)
        val time: TextView = itemView.findViewById(R.id.tvNotificationTime)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_notification, parent, false)
        return NotificationViewHolder(view)
    }

    override fun onBindViewHolder(holder: NotificationViewHolder, position: Int) {
        val notification = notifications[position]

        // Isi data ke dalam view
        holder.icon.setImageResource(notification.iconResId)
        holder.title.text = notification.title
        holder.body.text = notification.body
        holder.time.text = notification.time

        // Anda bisa menambahkan logika lain di sini, misalnya mengubah warna background
        // jika notifikasi sudah dibaca (notification.isRead).
    }

    override fun getItemCount(): Int {
        return notifications.size
    }

    /**
     * Fungsi untuk memperbarui data di dalam adapter dari luar (Activity).
     */
    fun updateData(newNotifications: List<Notification>) {
        this.notifications = newNotifications
        notifyDataSetChanged() // Memberitahu RecyclerView untuk me-render ulang
    }
}
