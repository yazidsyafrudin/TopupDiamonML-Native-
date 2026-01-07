package com.example.yazstoremlbb

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth

/**
 * Fragment ini menampilkan detail saldo pengguna dan riwayat transaksi saldo.
 * Ia juga menyediakan tombol aksi untuk Top Up dan Tarik Saldo.
 */
class SaldoFragment : Fragment() {

    // Deklarasi variabel untuk Firebase, meskipun untuk contoh ini kita pakai data statis.
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Menghubungkan file Kotlin ini dengan layout XML-nya.
        return inflater.inflate(R.layout.fragment_saldo, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inisialisasi Firebase
        firebaseAuth = FirebaseAuth.getInstance()

        // --- Inisialisasi Komponen UI ---
        val btnBack: ImageButton = view.findViewById(R.id.btnBack)
        val tvTotalSaldo: TextView = view.findViewById(R.id.tvTotalSaldo)
        val btnTopUpSaldo: MaterialButton = view.findViewById(R.id.btnTopUpSaldo)
        val btnTarikSaldo: MaterialButton = view.findViewById(R.id.btnTarikSaldo)

        // --- Atur Data dan Listener ---

        // 1. Atur data saldo (untuk sementara statis)
        // Di aplikasi nyata, Anda akan mengambil data ini dari Firestore atau Realtime Database.
        tvTotalSaldo.text = "Rp 150.000"

        // 2. Fungsikan tombol kembali di header
        btnBack.setOnClickListener {
            // Memanggil fungsi kembali dari Activity, yang akan kembali ke HomeFragment
            // berkat logika `addToBackStack` di MainActivity.
            activity?.onBackPressedDispatcher?.onBackPressed()
        }

        // 3. Fungsikan tombol "Top Up"
        btnTopUpSaldo.setOnClickListener {
            // Untuk saat ini, kita hanya menampilkan pesan Toast.
            // Di masa depan, Anda bisa membuat Activity atau Fragment baru khusus untuk top up saldo.
            Toast.makeText(context, "Membuka halaman Top Up Saldo...", Toast.LENGTH_SHORT).show()
        }

        // 4. Fungsikan tombol "Tarik Saldo"
        btnTarikSaldo.setOnClickListener {
            // Menampilkan pesan bahwa fitur belum tersedia.
            Toast.makeText(context, "Fitur Tarik Saldo akan segera tersedia", Toast.LENGTH_SHORT).show()
        }

        // Di sini Anda bisa menambahkan logika untuk mengisi RecyclerView riwayat saldo jika sudah ada.
    }
}
