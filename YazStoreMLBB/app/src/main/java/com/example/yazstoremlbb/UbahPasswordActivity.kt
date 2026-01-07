package com.example.yazstoremlbb

import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth

class UbahPasswordActivity : AppCompatActivity() {

    private lateinit var etCurrentPassword: TextInputEditText
    private lateinit var etNewPassword: TextInputEditText
    private lateinit var etConfirmPassword: TextInputEditText
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ubah_password)

        // Inisialisasi Firebase
        auth = FirebaseAuth.getInstance()

        // Inisialisasi Views
        val btnBack: ImageButton = findViewById(R.id.btnBack)
        val btnUpdatePassword: MaterialButton = findViewById(R.id.btnUpdatePassword)
        etCurrentPassword = findViewById(R.id.etCurrentPassword)
        etNewPassword = findViewById(R.id.etNewPassword)
        etConfirmPassword = findViewById(R.id.etConfirmPassword)

        // Fungsikan tombol kembali
        btnBack.setOnClickListener {
            finish()
        }

        // Fungsikan tombol ubah password
        btnUpdatePassword.setOnClickListener {
            updatePassword()
        }
    }

    private fun updatePassword() {
        val currentPassword = etCurrentPassword.text.toString().trim()
        val newPassword = etNewPassword.text.toString().trim()
        val confirmPassword = etConfirmPassword.text.toString().trim()
        val user = auth.currentUser

        // Validasi input
        if (currentPassword.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(this, "Semua kolom harus diisi.", Toast.LENGTH_SHORT).show()
            return
        }
        if (newPassword.length < 6) {
            etNewPassword.error = "Password baru minimal 6 karakter"
            return
        }
        if (newPassword != confirmPassword) {
            etConfirmPassword.error = "Konfirmasi password tidak cocok"
            return
        }
        if (user?.email == null) {
            Toast.makeText(this, "Gagal mendapatkan data pengguna.", Toast.LENGTH_SHORT).show()
            return
        }

        // Proses re-autentikasi dan ubah password
        val credential = EmailAuthProvider.getCredential(user.email!!, currentPassword)
        user.reauthenticate(credential)
            .addOnSuccessListener {
                // Re-autentikasi berhasil, lanjutkan ubah password
                user.updatePassword(newPassword)
                    .addOnSuccessListener {
                        Toast.makeText(this, "Password berhasil diubah.", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                    .addOnFailureListener { e ->
                        Toast.makeText(this, "Gagal mengubah password: ${e.message}", Toast.LENGTH_SHORT).show()
                        Log.e("UbahPassword", "Error updating password", e)
                    }
            }
            .addOnFailureListener { e ->
                // Re-autentikasi gagal (password lama salah)
                etCurrentPassword.error = "Password saat ini salah"
                Log.e("UbahPassword", "Re-authentication failed", e)
            }
    }
}
