package com.example.yazstoremlbb

import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

// 1. DATA MODEL UNTUK SATU NOTIFIKASI
data class Notification(
    val id: String,
    val title: String,
    val body: String,
    val time: String,
    val iconResId: Int,
    var isRead: Boolean = false
)

// 2. ACTIVITY UTAMA
class NotifikasiActivity : AppCompatActivity() {

    private lateinit var rvNotifications: RecyclerView
    private lateinit var notificationAdapter: NotificationAdapter
    private lateinit var layoutEmpty: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notifikasi)

        val btnBack: ImageButton = findViewById(R.id.btnBack)
        rvNotifications = findViewById(R.id.rvNotifications)
        layoutEmpty = findViewById(R.id.layoutEmpty)

        btnBack.setOnClickListener {
            finish()
        }

        setupRecyclerView()
        loadNotifications()
    }

    private fun setupRecyclerView() {
        notificationAdapter = NotificationAdapter(emptyList()) // <-- Memanggil kelas dari file NotificationAdapter.kt
        rvNotifications.layoutManager = LinearLayoutManager(this)
        rvNotifications.adapter = notificationAdapter
    }

    private fun loadNotifications() {
        val dummyNotifications = createDummyData()
        if (dummyNotifications.isEmpty()) {
            rvNotifications.visibility = View.GONE
            layoutEmpty.visibility = View.VISIBLE
        } else {
            rvNotifications.visibility = View.VISIBLE
            layoutEmpty.visibility = View.GONE
            notificationAdapter.updateData(dummyNotifications)
        }
    }

    private fun createDummyData(): List<Notification> {
        return listOf(
            Notification(id = "1", title = "Promo Gajian!", body = "Dapatkan diskon 20% untuk semua top up hari ini.", time = "2j lalu", iconResId = R.drawable.ic_discount),
            Notification(id = "2", title = "Transaksi Berhasil", body = "Pembelian 257 Diamond (TRX12345) telah berhasil.", time = "1h lalu", iconResId = R.drawable.ic_check_circle),
            Notification(id = "3", title = "Selamat Datang!", body = "Terima kasih telah bergabung dengan YazStore MLBB.", time = "1d lalu", iconResId = R.drawable.ic_waving_hand)
        )
    }
}

// PASTIKAN TIDAK ADA LAGI DEFINISI "class NotificationAdapter" DI BAWAH SINI
