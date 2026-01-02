package com.example.yazstoremlbb

import android.content.Intent // <-- 1. IMPORT Intent
import android.os.Bundle
import android.widget.TextView // <-- 2. IMPORT TextView
import androidx.appcompat.app.AppCompatActivity
// ... import lainnya jika ada

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // ==========================================================
        //  KODE UNTUK PINDAH KE HALAMAN DAFTAR
        // ==========================================================

        // 3. Dapatkan referensi ke TextView "Daftar" dari layout
        // ID-nya adalah "tvSignUp" sesuai dengan XML Anda
        val tvSignUp: TextView = findViewById(R.id.tvSignUp)

        // 4. Tambahkan listener klik pada TextView tersebut
        tvSignUp.setOnClickListener {
            // Buat Intent untuk memulai SignupActivity
            val intent = Intent(this, SignupActivity::class.java)

            // Jalankan Intent untuk pindah halaman
            startActivity(intent)
        }

        // Anda juga bisa menambahkan listener untuk tombol lain di sini
        // Contoh: val btnLogin: MaterialButton = findViewById(R.id.btnLogin)
        // btnLogin.setOnClickListener { ... }
    }
}
