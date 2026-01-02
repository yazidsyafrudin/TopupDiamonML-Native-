package com.example.yazstoremlbb

import android.content.Intent // <-- IMPORT INI
import android.os.Bundle
import android.os.Handler // <-- IMPORT INI
import android.os.Looper // <-- IMPORT INI
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    // Tentukan durasi splash screen dalam milidetik (2000 ms = 2 detik)
    private val SPLASH_TIME_OUT: Long = 2000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_splash)

        // Kode window insets Anda yang sudah ada
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.splash)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // ==========================================================
        //  TAMBAHKAN KODE UNTUK PINDAH HALAMAN SETELAH 2 DETIK
        // ==========================================================
        Handler(Looper.getMainLooper()).postDelayed({
            // Buat Intent untuk memulai LoginActivity
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)

            // Tutup activity ini agar pengguna tidak bisa kembali ke splash screen dengan tombol back
            finish()
        }, SPLASH_TIME_OUT)
    }
}
