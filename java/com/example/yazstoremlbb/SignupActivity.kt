package com.example.yazstoremlbb

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.checkbox.MaterialCheckBox
import com.google.android.material.textfield.TextInputLayout // Pastikan ini di-import
import com.google.firebase.auth.FirebaseAuth

class SignupActivity : AppCompatActivity() {

    // Deklarasikan variabel untuk Firebase Auth
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        // Inisialisasi Firebase Auth
        firebaseAuth = FirebaseAuth.getInstance()

        // === Navigasi dan Tombol ===

        // Menangani Tombol Kembali (btnBack)
        val btnBack: ImageButton = findViewById(R.id.btnBack)
        btnBack.setOnClickListener {
            // Kembali ke halaman sebelumnya
            onBackPressedDispatcher.onBackPressed()
        }

        // Menangani Teks "Masuk" (tvLogin) untuk kembali ke LoginActivity
        val tvLogin: TextView = findViewById(R.id.tvLogin)
        tvLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP) // Membersihkan tumpukan activity
            startActivity(intent)
        }

        // === Logika Pendaftaran ===

        val btnSignUp: MaterialButton = findViewById(R.id.btnSignUp)
        val cbTerms: MaterialCheckBox = findViewById(R.id.cbTerms)

        // Dapatkan referensi ke semua TextInputLayout
        val tilName = findViewById<TextInputLayout>(R.id.tilName)
        val tilEmail = findViewById<TextInputLayout>(R.id.tilEmail)
        val tilPassword = findViewById<TextInputLayout>(R.id.tilPassword)
        val tilConfirmPassword = findViewById<TextInputLayout>(R.id.tilConfirmPassword)
        val tilPhone = findViewById<TextInputLayout>(R.id.tilPhone)

        // Listener untuk tombol "Daftar"
        btnSignUp.setOnClickListener {
            // Ambil teks dari setiap editText di dalam TextInputLayout
            val name = tilName.editText?.text.toString().trim()
            val email = tilEmail.editText?.text.toString().trim()
            val password = tilPassword.editText?.text.toString().trim()
            val confirmPassword = tilConfirmPassword.editText?.text.toString().trim()
            val phone = tilPhone.editText?.text.toString().trim()

            // === Validasi Input ===
            if (name.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || phone.isEmpty()) {
                Toast.makeText(this, "Semua kolom harus diisi", Toast.LENGTH_SHORT).show()
                return@setOnClickListener // Hentikan proses jika ada yang kosong
            }
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(this, "Format email tidak valid", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (password.length < 6) {
                Toast.makeText(this, "Password minimal harus 6 karakter", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (password != confirmPassword) {
                Toast.makeText(this, "Password dan Konfirmasi Password tidak cocok", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (!cbTerms.isChecked) {
                Toast.makeText(this, "Anda harus menyetujui Syarat & Ketentuan", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // === Proses Pendaftaran ke Firebase ===
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Pendaftaran berhasil
                        Toast.makeText(this, "Pendaftaran Berhasil!", Toast.LENGTH_SHORT).show()

                        // Di sini Anda bisa menambahkan data lain (nama, no HP) ke Firestore/Realtime Database jika perlu

                        // Setelah berhasil, arahkan ke halaman utama aplikasi (misalnya MainActivity)
                        val intent = Intent(this, MainActivity::class.java)
                        // Hapus semua activity sebelumnya dari tumpukan agar pengguna tidak bisa kembali ke halaman login/signup
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)

                    } else {
                        // Pendaftaran gagal, tampilkan pesan error dari Firebase
                        Toast.makeText(this, "Pendaftaran Gagal: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                    }
                }
        }
    }
}
