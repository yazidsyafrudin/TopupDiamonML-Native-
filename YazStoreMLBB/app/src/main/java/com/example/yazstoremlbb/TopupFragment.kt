package com.example.yazstoremlbb

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore

/**
 * Fragment untuk halaman Top Up Diamond.
 * Mengelola input pengguna, pemilihan paket, metode pembayaran,
 * dan memproses checkout dengan menyimpan data ke Firestore.
 */
class TopupFragment : Fragment() {

    // Inisialisasi ViewModel menggunakan 'by viewModels()' dari library fragment-ktx
    private val topupViewModel: TopupViewModel by viewModels()

    // Deklarasi semua komponen UI yang akan digunakan
    private lateinit var etUserId: TextInputEditText
    private lateinit var etZoneId: TextInputEditText
    private lateinit var rvDiamondPackages: RecyclerView
    private lateinit var rgPaymentMethods: RadioGroup
    private lateinit var cardSummary: MaterialCardView
    private lateinit var tvSummaryDiamond: TextView
    private lateinit var tvSummaryPayment: TextView
    private lateinit var tvSummaryPrice: TextView
    private lateinit var btnCheckout: MaterialButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Menghubungkan Fragment dengan layout XML-nya
        return inflater.inflate(R.layout.fragment_topup, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews(view)
        setupRecyclerView()
        setupInputListeners()
        observeViewModel()
    }

    /**
     * Menghubungkan variabel di Kotlin dengan komponen di XML menggunakan findViewById.
     */
    private fun initViews(view: View) {
        // Setup Tombol Kembali di header
        val btnBack: ImageButton = view.findViewById(R.id.btnBack)
        btnBack.setOnClickListener {
            activity?.onBackPressedDispatcher?.onBackPressed()
        }

        etUserId = view.findViewById(R.id.etUserId)
        etZoneId = view.findViewById(R.id.etZoneId)
        rvDiamondPackages = view.findViewById(R.id.rvDiamondPackages)
        rgPaymentMethods = view.findViewById(R.id.rgPaymentMethods)
        cardSummary = view.findViewById(R.id.cardSummary)
        tvSummaryDiamond = view.findViewById(R.id.tvSummaryDiamond)
        tvSummaryPayment = view.findViewById(R.id.tvSummaryPayment)
        tvSummaryPrice = view.findViewById(R.id.tvSummaryPrice)
        btnCheckout = view.findViewById(R.id.btnCheckout)
    }

    /**
     * Mengatur semua listener untuk input dari pengguna (EditText, RadioGroup, Button).
     */
    private fun setupInputListeners() {
        // Listener untuk memantau ketikan pada kolom User ID
        etUserId.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                topupViewModel.setUserId(s.toString())
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        // Listener untuk memantau ketikan pada kolom Zone ID
        etZoneId.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                topupViewModel.setZoneId(s.toString())
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        // Listener untuk mendeteksi perubahan pilihan pada Metode Pembayaran
        rgPaymentMethods.setOnCheckedChangeListener { _, checkedId ->
            val selectedRadioButton = view?.findViewById<RadioButton>(checkedId)
            selectedRadioButton?.let {
                topupViewModel.setSelectedPayment(it.text.toString())
            }
        }

        // Listener untuk tombol "Bayar Sekarang"
        btnCheckout.setOnClickListener {
            // Ambil semua data terakhir dari ViewModel
            val userId = topupViewModel.userId.value
            val zoneId = topupViewModel.zoneId.value
            val diamondPackage = topupViewModel.selectedDiamond.value
            val paymentMethod = topupViewModel.selectedPayment.value
            val currentUser = FirebaseAuth.getInstance().currentUser

            // Pastikan semua data valid dan ada pengguna yang login sebelum melanjutkan
            if (userId != null && zoneId != null && diamondPackage != null && paymentMethod != null && currentUser != null) {

                // --- PERSIAPAN DATA & SIMPAN KE FIRESTORE ---
                val db = FirebaseFirestore.getInstance()
                val transactionId = "TRX${System.currentTimeMillis()}"

                // Buat objek Map untuk data yang akan disimpan
                val transactionData = hashMapOf(
                    "transactionId" to transactionId,
                    "userUid" to currentUser.uid, // <-- SANGAT PENTING untuk query riwayat
                    "gameUserId" to userId,
                    "gameZoneId" to zoneId,
                    "diamondAmount" to diamondPackage.diamondAmount,
                    "price" to diamondPackage.price,
                    "paymentMethod" to paymentMethod,
                    "status" to "SUCCESS",
                    "timestamp" to FieldValue.serverTimestamp() // Stempel waktu dari server
                )

                // Simpan data ke koleksi "transactions"
                db.collection("transactions").document(transactionId)
                    .set(transactionData)
                    .addOnSuccessListener {
                        Log.d("TopupFragment", "Transaksi berhasil disimpan ke Firestore.")

                        // --- PINDAH KE HALAMAN SUKSES SETELAH DATA DISIMPAN ---
                        val intent = Intent(activity, SuccessActivity::class.java)

                        // Kirim data yang relevan ke halaman sukses
                        intent.putExtra("EXTRA_TRANSACTION_ID", transactionId)
                        intent.putExtra("EXTRA_USER_ID", "$userId ($zoneId)")
                        intent.putExtra("EXTRA_DIAMOND_AMOUNT", diamondPackage.diamondAmount)
                        intent.putExtra("EXTRA_PAYMENT_METHOD", paymentMethod)
                        intent.putExtra("EXTRA_TOTAL_PRICE", diamondPackage.price)

                        startActivity(intent)
                    }
                    .addOnFailureListener { e ->
                        Log.w("TopupFragment", "Error menyimpan transaksi", e)
                        Toast.makeText(context, "Gagal memproses pesanan, coba lagi.", Toast.LENGTH_SHORT).show()
                    }
                // -------------------------------------------------------------

            } else {
                Toast.makeText(context, "Harap lengkapi semua data", Toast.LENGTH_SHORT).show()
            }
        }
    }

    /**
     * Menyiapkan RecyclerView dengan data dan adapter.
     */
    private fun setupRecyclerView() {
        val diamondList = createDummyData()
        val adapter = DiamondAdapter(diamondList) { selectedPackage ->
            // Ini adalah callback dari adapter yang akan dipanggil setiap kali item diamond diklik.
            // Kita teruskan informasinya ke ViewModel.
            topupViewModel.setSelectedDiamond(selectedPackage)
        }
        rvDiamondPackages.adapter = adapter
        rvDiamondPackages.layoutManager = GridLayoutManager(context, 3) // Menampilkan 3 item per baris
    }

    /**
     * Mengamati (observe) semua LiveData dari ViewModel.
     * UI akan diperbarui secara otomatis setiap kali ada perubahan data di ViewModel.
     */
    private fun observeViewModel() {
        // Mengamati perubahan pada paket diamond yang dipilih
        topupViewModel.selectedDiamond.observe(viewLifecycleOwner) { diamondPackage ->
            if (diamondPackage != null) {
                val bonusText = if (diamondPackage.bonusAmount != null) " + ${diamondPackage.bonusAmount} Bonus" else ""
                tvSummaryDiamond.text = "${diamondPackage.diamondAmount}$bonusText"
                tvSummaryPrice.text = diamondPackage.price
            }
        }

        // Mengamati perubahan pada metode pembayaran yang dipilih
        topupViewModel.selectedPayment.observe(viewLifecycleOwner) { paymentMethod ->
            tvSummaryPayment.text = paymentMethod
        }

        // Mengamati status kesiapan checkout untuk menampilkan ringkasan dan mengaktifkan tombol
        topupViewModel.isReadyForCheckout.observe(viewLifecycleOwner) { isReady ->
            cardSummary.isVisible = isReady
            btnCheckout.isEnabled = isReady
        }
    }

    /**
     * Membuat daftar data dummy untuk paket diamond.
     * Di aplikasi nyata, data ini sebaiknya diambil dari server/database.
     */
    private fun createDummyData(): List<DiamondPackage> {
        return listOf(
            DiamondPackage(diamondAmount = "86", bonusAmount = "8", price = "Rp 20.000"),
            DiamondPackage(diamondAmount = "172", bonusAmount = "16", price = "Rp 40.000"),
            DiamondPackage(diamondAmount = "257", bonusAmount = "26", price = "Rp 60.000"),
            DiamondPackage(diamondAmount = "344", price = "Rp 80.000"), // Contoh tanpa bonus
            DiamondPackage(diamondAmount = "429", price = "Rp 100.000"),
            DiamondPackage(diamondAmount = "514", bonusAmount = "51", price = "Rp 120.000"),
            DiamondPackage(diamondAmount = "706", price = "Rp 160.000"),
            DiamondPackage(diamondAmount = "1050", bonusAmount = "105", price = "Rp 240.000")
        )
    }
}
