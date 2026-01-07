package com.example.yazstoremlbb

import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions

class EditProfileActivity : AppCompatActivity() {

    // Deklarasi komponen UI dan Firebase
    private lateinit var etFullName: TextInputEditText
    private lateinit var etEmail: TextInputEditText
    private lateinit var etPhoneNumber: TextInputEditText
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        // Inisialisasi Firebase
        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        // Inisialisasi Views
        val btnBack: ImageButton = findViewById(R.id.btnBack)
        val btnSaveChanges: MaterialButton = findViewById(R.id.btnSaveChanges)
        etFullName = findViewById(R.id.etFullName)
        etEmail = findViewById(R.id.etEmail)
        etPhoneNumber = findViewById(R.id.etPhoneNumber)

        // Muat data pengguna saat ini ke dalam form
        loadCurrentUserData()

        // Tombol kembali
        btnBack.setOnClickListener {
            finish()
        }

        // Tombol simpan perubahan
        btnSaveChanges.setOnClickListener {
            saveProfileChanges()
        }
    }

    private fun loadCurrentUserData() {
        val user = auth.currentUser
        if (user != null) {
            // Isi nama dan email dari Firebase Auth
            etFullName.setText(user.displayName)
            etEmail.setText(user.email)

            // Ambil nomor HP dari Firestore
            db.collection("users")
                .document(user.uid)
                .get()
                .addOnSuccessListener { document ->
                    if (document != null && document.exists()) {
                        val phoneNumber = document.getString("phoneNumber")
                        etPhoneNumber.setText(phoneNumber)
                    }
                }
                .addOnFailureListener {
                    Log.w("EditProfile", "Gagal mengambil nomor HP dari Firestore.")
                }
        }
    }

    private fun saveProfileChanges() {
        val newFullName = etFullName.text.toString().trim()
        val newPhoneNumber = etPhoneNumber.text.toString().trim()
        val user = auth.currentUser

        // Validasi
        if (user == null) {
            Toast.makeText(
                this,
                "Tidak ada pengguna yang login.",
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        if (newFullName.isEmpty()) {
            etFullName.error = "Nama tidak boleh kosong"
            return
        }

        // 1. Update DisplayName di Firebase Auth
        val profileUpdates = UserProfileChangeRequest.Builder()
            .setDisplayName(newFullName)
            .build()

        user.updateProfile(profileUpdates)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("EditProfile", "DisplayName berhasil diperbarui.")
                } else {
                    Log.w(
                        "EditProfile",
                        "Gagal memperbarui DisplayName.",
                        task.exception
                    )
                }
            }

        // 2. Update data di Firestore (merge)
        val userDocRef = db.collection("users").document(user.uid)
        val userData = hashMapOf(
            "username" to newFullName,
            "phoneNumber" to newPhoneNumber
        )

        userDocRef.set(userData, SetOptions.merge())
            .addOnSuccessListener {
                Toast.makeText(
                    this,
                    "Profil berhasil diperbarui!",
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            }
            .addOnFailureListener { e ->
                Toast.makeText(
                    this,
                    "Gagal memperbarui profil: ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
                Log.w("EditProfile", "Error updating document", e)
            }
    }
}
