package com.example.yazstoremlbb

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class ProfileActivity : AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth
    private var currentUser: FirebaseUser? = null

    private lateinit var tvUserName: TextView
    private lateinit var tvEmail: TextView
    private lateinit var tvPhone: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        // Inisialisasi Firebase
        firebaseAuth = FirebaseAuth.getInstance()
        currentUser = firebaseAuth.currentUser

        // Inisialisasi semua view dari layout
        tvUserName = findViewById(R.id.tvUserName)
        tvEmail = findViewById(R.id.tvEmail)
        tvPhone = findViewById(R.id.tvPhone)

        // Panggil fungsi untuk memuat data pengguna
        loadUserData()

        // Beri fungsi pada tombol Logout
        val btnLogout: CardView = findViewById(R.id.btnLogout)
        btnLogout.setOnClickListener {
            showLogoutConfirmationDialog()
        }

        // Beri fungsi pada tombol-tombol lainnya
        setupClickListeners()
    }

    private fun loadUserData() {
        currentUser?.let { user ->
            val name = user.displayName
            val email = user.email
            val phone = user.phoneNumber

            tvUserName.text = if (!name.isNullOrEmpty()) name else email?.split('@')?.get(0) ?: "Pengguna"
            tvEmail.text = email ?: "Email tidak tersedia"
            tvPhone.text = if (!phone.isNullOrEmpty()) phone else "Nomor HP belum diatur"
        }
    }

    private fun showLogoutConfirmationDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Konfirmasi Keluar")
        builder.setMessage("Apakah Anda yakin ingin keluar dari akun Anda?")
        builder.setPositiveButton("Ya, Keluar") { _, _ ->
            firebaseAuth.signOut()
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
        builder.setNegativeButton("Batal") { dialog, _ ->
            dialog.dismiss()
        }
        builder.create().show()
    }

    private fun setupClickListeners() {
        val btnBack: ImageButton = findViewById(R.id.btnBack)
        btnBack.setOnClickListener {
            // Untuk Activity, perintah kembali yang benar adalah ini
            onBackPressedDispatcher.onBackPressed()
        }

        // Menambahkan listener untuk tombol lain
        val btnEditProfile: CardView = findViewById(R.id.btnEditProfile)
        val btnChangePassword: CardView = findViewById(R.id.btnChangePassword)
        val btnNotification: CardView = findViewById(R.id.btnNotification)
        val btnHelp: CardView = findViewById(R.id.btnHelp)
        val btnAbout: CardView = findViewById(R.id.btnTentangAplikasi)

        btnEditProfile.setOnClickListener { showToast("Fitur Edit Profil akan datang!") }
        btnChangePassword.setOnClickListener { showToast("Fitur Ubah Password akan datang!") }
        btnNotification.setOnClickListener { showToast("Fitur Notifikasi akan datang!") }
        btnHelp.setOnClickListener { showToast("Fitur Pusat Bantuan akan datang!") }

        // ========================================================
        //      PERBAIKAN UTAMA ADA DI SINI
        // ========================================================
        btnAbout.setOnClickListener {
            // Buat Intent untuk memulai TentangAplikasiActivity
            val intent = Intent(this, TentangAplikasiActivity::class.java)
            // Mulai Activity baru
            startActivity(intent)
        }
        // ========================================================
        //      untuk tombol edit profile
        // ========================================================
        btnEditProfile.setOnClickListener {
            val intent = Intent(this, EditProfileActivity::class.java)
            startActivity(intent)
        }
        // ========================================================
        //      untuk tombol ubah password
        // ========================================================
        btnChangePassword.setOnClickListener {
            val intent = Intent(this, UbahPasswordActivity::class.java)
            startActivity(intent)
        }
        // ========================================================
        //      untuk tombol help
        // ========================================================
        btnHelp.setOnClickListener {
            val intent = Intent(this, PusatBantuanActivity::class.java)
            startActivity(intent)
        }
        // ========================================================
        //      untuk tombol notification
        // ========================================================
        btnNotification.setOnClickListener {
            val intent = Intent(this, NotifikasiActivity::class.java)
            startActivity(intent)
        }
    }

    private fun showToast(message: String) {
        // 'this' merujuk ke Activity Context, jadi aman digunakan
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
