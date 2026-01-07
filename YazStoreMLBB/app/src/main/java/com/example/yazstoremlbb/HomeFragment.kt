package com.example.yazstoremlbb

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import java.text.NumberFormat
import java.util.*

class HomeFragment : Fragment() {

    private var listener: OnMenuHomeClickListener? = null

    // Komponen UI
    private lateinit var tvUserName: TextView
    private lateinit var tvUserBalance: TextView
    private lateinit var rvLastTransactions: RecyclerView
    private lateinit var tvNoHistoryMessage: TextView
    private lateinit var btnTopUpHome: View
    private lateinit var tvLihatSemuaTransaksi: TextView
    private lateinit var menuRiwayat: View
    private lateinit var promoViewPager: ViewPager2 // <-- Komponen untuk Slider

    // Adapter
    private lateinit var transactionAdapter: TransactionAdapter
    private lateinit var promoSliderAdapter: PromoSliderAdapter // <-- Adapter untuk Slider

    // Firebase
    private lateinit var db: FirebaseFirestore
    private lateinit var auth: FirebaseAuth

    // Handler untuk Auto-Scroll
    private val sliderHandler = Handler(Looper.getMainLooper())
    private lateinit var sliderRunnable: Runnable
    private val SLIDER_DELAY_MS: Long = 2000 // 2 detik

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnMenuHomeClickListener) {
            listener = context
        } else {
            throw RuntimeException("$context harus mengimplementasikan OnMenuHomeClickListener")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        initViews(view)
        setupRecyclerView()
        setupClickListeners()
        setupPromoSlider() // <-- Memanggil fungsi untuk slider

        fetchUserData()
        fetchLastThreeTransactions()
    }

    private fun initViews(view: View) {
        tvUserName = view.findViewById(R.id.tvUserName)
        tvUserBalance = view.findViewById(R.id.tvUserBalance)
        rvLastTransactions = view.findViewById(R.id.rvLastTransactions)
        tvNoHistoryMessage = view.findViewById(R.id.tvNoHistoryMessage)
        btnTopUpHome = view.findViewById(R.id.btnTopUpHome)
        tvLihatSemuaTransaksi = view.findViewById(R.id.tvLihatSemuaTransaksi)
        menuRiwayat = view.findViewById(R.id.menuRiwayat)
        promoViewPager = view.findViewById(R.id.promoViewPager) // Inisialisasi ViewPager2
    }

    private fun setupRecyclerView() {
        transactionAdapter = TransactionAdapter(emptyList())
        rvLastTransactions.layoutManager = LinearLayoutManager(context)
        rvLastTransactions.adapter = transactionAdapter
        rvLastTransactions.isNestedScrollingEnabled = false
    }

    private fun setupClickListeners() {
        // (Tidak ada perubahan di sini, kode listener Anda sebelumnya tetap sama)
        btnTopUpHome.setOnClickListener { listener?.onMenuHomeClick(R.id.menuTopup) }
        tvLihatSemuaTransaksi.setOnClickListener { listener?.onMenuHomeClick(R.id.menuRiwayat) }
        menuRiwayat.setOnClickListener { listener?.onMenuHomeClick(R.id.menuRiwayat) }
        view?.findViewById<View>(R.id.menuTopup)?.setOnClickListener { listener?.onMenuHomeClick(R.id.menuTopup) }
        view?.findViewById<View>(R.id.menuPromo)?.setOnClickListener { listener?.onMenuHomeClick(R.id.menuPromo) }
    }

    private fun fetchUserData() {
        // (Tidak ada perubahan di sini)
        val user = auth.currentUser
        if (user != null) {
            val displayName = user.displayName
            if (!displayName.isNullOrEmpty()) {
                tvUserName.text = displayName
            } else {
                tvUserName.text = user.email?.split('@')?.get(0) ?: "Pengguna"
            }
            db.collection("users").document(user.uid).get()
                .addOnSuccessListener { document ->
                    if (isAdded && document != null && document.exists()) {
                        val balance = document.getDouble("balance") ?: 0.0
                        val format = NumberFormat.getCurrencyInstance(Locale("in", "ID"))
                        tvUserBalance.text = format.format(balance)
                    } else {
                        tvUserBalance.text = "Rp 0"
                    }
                }.addOnFailureListener { if (isAdded) tvUserBalance.text = "Rp 0" }
        } else {
            tvUserName.text = "Tamu"
            tvUserBalance.text = "Rp 0"
        }
    }

    private fun fetchLastThreeTransactions() {
        // (Tidak ada perubahan di sini)
        val currentUser = auth.currentUser ?: return
        db.collection("transactions")
            .whereEqualTo("userUid", currentUser.uid)
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .limit(3)
            .get()
            .addOnSuccessListener { documents ->
                if (!isAdded) return@addOnSuccessListener
                if (documents.isEmpty) {
                    tvNoHistoryMessage.visibility = View.VISIBLE
                    rvLastTransactions.visibility = View.GONE
                } else {
                    val lastTransactions = documents.toObjects(Transaction::class.java)
                    transactionAdapter.updateData(lastTransactions)
                    tvNoHistoryMessage.visibility = View.GONE
                    rvLastTransactions.visibility = View.VISIBLE
                }
            }.addOnFailureListener { exception ->
                if (!isAdded) return@addOnFailureListener
                Log.e("HomeFragment", "Error: ", exception)
                tvNoHistoryMessage.text = "Gagal memuat riwayat."
                tvNoHistoryMessage.visibility = View.VISIBLE
                rvLastTransactions.visibility = View.GONE
            }
    }

    // ===== FUNGSI BARU UNTUK PROMO SLIDER =====
    private fun setupPromoSlider() {
        // Daftar gambar promo Anda. Pastikan nama file ini ada di folder /res/drawable
        val promoImages = listOf(
            R.drawable.bannerml,
            R.drawable.bannerml2,
            R.drawable.bannerml3
        )

        promoSliderAdapter = PromoSliderAdapter(promoImages)
        promoViewPager.adapter = promoSliderAdapter

        // Mulai dari posisi tengah agar bisa scroll ke kiri dari awal
        if (promoImages.isNotEmpty()) {
            promoViewPager.setCurrentItem(Int.MAX_VALUE / 2, false)
        }

        // Definisikan aksi yang akan dijalankan oleh Handler (menggeser slider)
        sliderRunnable = Runnable {
            if (isAdded) { // Hanya jalankan jika fragment masih ada
                promoViewPager.currentItem = promoViewPager.currentItem + 1
            }
        }

        // Reset timer setiap kali user menggeser slider secara manual
        promoViewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                sliderHandler.removeCallbacks(sliderRunnable)
                sliderHandler.postDelayed(sliderRunnable, SLIDER_DELAY_MS)
            }
        })
    }

    override fun onResume() {
        super.onResume()
        // Mulai auto-scroll saat fragment kembali terlihat
        sliderHandler.postDelayed(sliderRunnable, SLIDER_DELAY_MS)
    }

    override fun onPause() {
        super.onPause()
        // Hentikan auto-scroll saat fragment dijeda untuk menghemat sumber daya
        sliderHandler.removeCallbacks(sliderRunnable)
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }
}
