package com.example.yazstoremlbb

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton

/**
 * Activity untuk menampilkan halaman Pusat Bantuan (FAQ).
 * Menyediakan fungsi untuk kembali dan menghubungi customer service via WhatsApp.
 */
class PusatBantuanActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pusat_bantuan)

        // Inisialisasi semua tombol dari layout
        val btnBack: ImageButton = findViewById(R.id.btnBack)
        val btnContactSupport: MaterialButton = findViewById(R.id.btnContactSupport)

        // Beri fungsi pada tombol kembali di header
        btnBack.setOnClickListener {
            // Menutup activity ini dan kembali ke halaman sebelumnya (ProfileActivity)
            finish()
        }

        // Beri fungsi pada tombol "Hubungi Customer Service"
        btnContactSupport.setOnClickListener {
            openWhatsApp()
        }
    }

    /**
     * Membuka aplikasi WhatsApp dan langsung mengarah ke nomor customer service.
     */
    private fun openWhatsApp() {
        // Ganti dengan nomor WhatsApp CS Anda, diawali dengan kode negara tanpa tanda '+' atau '0'.
        val phoneNumber = "6282257613283"
        val message = "Halo, saya butuh bantuan terkait aplikasi YazStore MLBB."

        try {
            val intent = Intent(Intent.ACTION_VIEW)
            val url = "https://api.whatsapp.com/send?phone=$phoneNumber&text=${Uri.encode(message)}"
            intent.data = Uri.parse(url)
            startActivity(intent)
        } catch (e: Exception) {
            // Tampilkan pesan error jika WhatsApp tidak terinstal
            Toast.makeText(this, "Aplikasi WhatsApp tidak ditemukan.", Toast.LENGTH_SHORT).show()
        }
    }
}
