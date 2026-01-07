package com.example.yazstoremlbb

// HAPUS BARIS IMPORT COMPOSE DI SINI
// import androidx.compose.ui.layout.layout

import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity


class TentangAplikasiActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tentang_aplikasi)

        // Cari tombol kembali berdasarkan ID-nya di layout
        val btnBack: ImageButton = findViewById(R.id.btnBack)

        // Beri fungsi OnClickListener
        btnBack.setOnClickListener {
            // Perintah ini akan menutup Activity saat ini dan kembali
            // ke Activity/Fragment sebelumnya (yaitu ProfileFragment).
            finish()
        }
    }
}

