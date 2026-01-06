package com.example.yazstoremlbb

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    // Deklarasikan Firebase Auth
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Inisialisasi Firebase Auth
        firebaseAuth = FirebaseAuth.getInstance()

        // --- Navigasi ke Halaman Daftar ---
        // Ini adalah fungsi untuk teks "Daftar" yang sudah pernah kita bahas
        val tvSignUp: TextView = findViewById(R.id.tvSignUp)
        tvSignUp.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }

        // ========================================================
        //      FUNGSI UTAMA UNTUK TOMBOL "MASUK" ADA DI SINI
        // ========================================================
        val btnLogin: MaterialButton = findViewById(R.id.btnLogin)
        val tilEmail = findViewById<TextInputLayout>(R.id.tilEmail)
        val tilPassword = findViewById<TextInputLayout>(R.id.tilPassword)

        btnLogin.setOnClickListener {
            // Ambil input dari pengguna
            val email = tilEmail.editText?.text.toString().trim()
            val password = tilPassword.editText?.text.toString().trim()

            // --- Validasi Input ---
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Email dan Password harus diisi", Toast.LENGTH_SHORT).show()
                return@setOnClickListener // Hentikan proses jika ada yang kosong
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(this, "Format email tidak valid", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // --- Proses Login ke Firebase ---
            firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Jika login berhasil
                        Toast.makeText(this, "Login Berhasil!", Toast.LENGTH_SHORT).show()

                        // Buat Intent untuk pindah ke MainActivity (halaman utama)
                        val intent = Intent(this, MainActivity::class.java)

                        // Flags ini penting untuk menghapus semua halaman sebelumnya (login, splash)
                        // dari tumpukan, agar pengguna tidak bisa kembali ke halaman login dengan tombol back.
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)

                    }
                    // ...
                    else {
                        // Jika login gagal
                        Toast.makeText(this, "Login Gagal: ${task.exception?.message}", Toast.LENGTH_LONG).show() // <-- SUDAH DIPERBAIKI
                    }
                }
        }
    }
}
