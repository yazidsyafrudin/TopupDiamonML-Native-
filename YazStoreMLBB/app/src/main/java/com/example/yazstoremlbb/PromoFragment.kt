package com.example.yazstoremlbb

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton // <-- IMPORT INIimport androidx.fragment.app.Fragment
import androidx.fragment.app.Fragment
import androidx.navigation.activity

class PromoFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Menghubungkan Fragment ini dengan layout-nya.
        return inflater.inflate(R.layout.fragment_promo, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // ========================================================
        //      TAMBAHAN FUNGSI UNTUK TOMBOL KEMBALI
        // ========================================================
        // Cari tombol kembali berdasarkan ID-nya di fragment_promo.xml
        val btnBack: ImageButton = view.findViewById(R.id.btnBack)

        // Tambahkan listener klik
        btnBack.setOnClickListener {
            // Panggil fungsi untuk kembali ke fragment sebelumnya
            activity?.onBackPressedDispatcher?.onBackPressed()
        }
        // ========================================================

        // ... (Logika lain untuk halaman promo bisa ditambahkan di sini nanti)
    }
}
