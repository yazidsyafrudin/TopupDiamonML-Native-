package com.example.yazstoremlbb

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.checkbox.MaterialCheckBox

class SignupActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Menghubungkan activity ini dengan layout activity_signup.xml
        setContentView(R.layout.activity_signup)

        // 1. Menangani Tombol Kembali (btnBack)
        val btnBack: ImageButton = findViewById(R.id.btnBack)
        btnBack.setOnClickListener {
            // Perintah ini sama seperti menekan tombol 'back' di navigasi sistem
            onBackPressedDispatcher.onBackPressed()
        }

        // 2. Menangani Teks "Masuk" (tvLogin)
        val tvLogin: TextView = findViewById(R.id.tvLogin)
        tvLogin.setOnClickListener {
            // Membuat intent untuk kembali ke LoginActivity
            val intent = Intent(this, LoginActivity::class.java)
            // Menambahkan flag untuk membersihkan activity di atasnya agar tidak menumpuk
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }

        // 3. Menangani Tombol Daftar (btnSignUp)
        val btnSignUp: MaterialButton = findViewById(R.id.btnSignUp)
        val cbTerms: MaterialCheckBox = findViewById(R.id.cbTerms)
        btnSignUp.setOnClickListener {
            // Ini adalah tempat untuk validasi dan logika pendaftaran nanti

            // Contoh validasi sederhana:
            if (!cbTerms.isChecked) {
                // Tampilkan pesan jika syarat & ketentuan belum dicentang
                Toast.makeText(this, "Anda harus menyetujui Syarat & Ketentuan", Toast.LENGTH_SHORT).show()
            } else {
                // Jika sudah dicentang, lanjutkan logika pendaftaran
                // Untuk saat ini, kita hanya tampilkan pesan Toast
                Toast.makeText(this, "Logika pendaftaran akan diimplementasikan di sini", Toast.LENGTH_SHORT).show()

                // Nanti di sini Anda akan memanggil fungsi untuk mendaftarkan user
                // ke Firebase Authentication, database Anda, dll.
            }
        }
    }
}
