// 1. PERBAIKI BAGIAN PACKAGE DAN IMPORT
package com.example.yazstoremlbb

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
// Hapus import yang tidak perlu seperti 'layout' dan 'ViewCompat' jika tidak digunakan secara langsung
// (Pada kode ini, ViewCompat dan WindowInsetsCompat tidak digunakan, jadi bisa dihapus)

class SplashActivity : AppCompatActivity() {

    private val SPLASH_TIME_OUT: Long = 2000
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_splash)

        firebaseAuth = FirebaseAuth.getInstance()

        // Handler untuk menunda dan pindah halaman
        Handler(Looper.getMainLooper()).postDelayed({
            // Cek apakah pengguna sudah login sebelumnya
            val currentUser = firebaseAuth.currentUser
            if (currentUser != null) {
                // Jika sudah login, langsung ke MainActivity
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            } else {
                // Jika belum login, arahkan ke LoginActivity
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
            // Tutup SplashActivity
            finish()
        }, SPLASH_TIME_OUT)
    }
}
