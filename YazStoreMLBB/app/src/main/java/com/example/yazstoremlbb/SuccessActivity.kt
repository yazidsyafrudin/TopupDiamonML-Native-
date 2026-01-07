package com.example.yazstoremlbb

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton

class SuccessActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_succes)

        // Ambil semua data yang dikirim dari TopupFragment
        val transactionId = intent.getStringExtra("EXTRA_TRANSACTION_ID")
        val userId = intent.getStringExtra("EXTRA_USER_ID")
        val diamondAmount = intent.getStringExtra("EXTRA_DIAMOND_AMOUNT")
        val paymentMethod = intent.getStringExtra("EXTRA_PAYMENT_METHOD")
        val totalPrice = intent.getStringExtra("EXTRA_TOTAL_PRICE")

        // Inisialisasi semua TextView dan Button
        val tvTransactionId: TextView = findViewById(R.id.tvTransactionId)
        val tvUserId: TextView = findViewById(R.id.tvUserId)
        val tvDiamondAmount: TextView = findViewById(R.id.tvDiamondAmount)
        val tvPaymentMethod: TextView = findViewById(R.id.tvPaymentMethod)
        val tvTotalPrice: TextView = findViewById(R.id.tvTotalPrice)
        val btnCopy: ImageView = findViewById(R.id.btnCopy)
        val btnBackToHome: MaterialButton = findViewById(R.id.btnBackToHome)
        val btnTopUpAgain: MaterialButton = findViewById(R.id.btnTopUpAgain)
        val tvContactCS: TextView = findViewById(R.id.tvContactCS)

        // Tampilkan data ke TextView
        tvTransactionId.text = transactionId
        tvUserId.text = userId
        tvDiamondAmount.text = diamondAmount
        tvPaymentMethod.text = paymentMethod
        tvTotalPrice.text = totalPrice

        // --- Fungsikan semua tombol ---

        // Fungsi untuk menyalin ID Transaksi
        btnCopy.setOnClickListener {
            val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("ID Transaksi", transactionId)
            clipboard.setPrimaryClip(clip)
            Toast.makeText(this, "ID Transaksi disalin!", Toast.LENGTH_SHORT).show()
        }

        // ========================================================
        //      PERUBAHAN UTAMA ADA DI SINI
        // ========================================================
        // Fungsi untuk kembali ke Beranda (MainActivity)
        btnBackToHome.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            // Flag ini akan membersihkan semua activity di atas MainActivity dan membawanya ke depan
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
            // Tambahkan "pesan" untuk memberitahu MainActivity agar menampilkan HomeFragment
            intent.putExtra("GO_TO_HOME_FRAGMENT", true)
            startActivity(intent)
            finish() // Tutup SuccessActivity
        }
        // ========================================================

        // Fungsi untuk Top Up Lagi (kembali ke MainActivity lalu membuka TopupFragment)
        btnTopUpAgain.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
            // Tambahkan "pesan" untuk memberitahu MainActivity agar langsung membuka TopupFragment
            intent.putExtra("GO_TO_TOPUP_FRAGMENT", true)
            startActivity(intent)
            finish()
        }

        // Fungsi untuk menghubungi CS (contoh)
        tvContactCS.setOnClickListener {
            Toast.makeText(this, "Membuka halaman bantuan...", Toast.LENGTH_SHORT).show()
        }
    }
}
