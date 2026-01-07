package com.example.yazstoremlbb

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import java.text.NumberFormat
import java.util.*

/**
 * Fragment untuk menampilkan daftar riwayat transaksi pengguna.
 * Data diambil dari koleksi 'transactions' di Cloud Firestore.
 */
class RiwayatFragment : Fragment() {

    // Deklarasi komponen UI dan adapter
    private lateinit var rvTransactions: RecyclerView
    private lateinit var transactionAdapter: TransactionAdapter

    // Deklarasi TextView untuk kartu ringkasan
    private lateinit var tvTotalDiamond: TextView
    private lateinit var tvBonus: TextView
    private lateinit var tvTransactionSummary: TextView

    // Deklarasi instance Firebase
    private lateinit var db: FirebaseFirestore
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Menghubungkan Fragment dengan layout XML-nya
        return inflater.inflate(R.layout.fragment_riwayat, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inisialisasi Firebase
        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        // Inisialisasi semua komponen UI
        initViews(view)

        // Setup RecyclerView
        setupRecyclerView()

        // Panggil fungsi untuk mulai mengambil data riwayat
        fetchTransactions()
    }

    private fun initViews(view: View) {
        // Tombol Kembali
        val btnBack: ImageButton = view.findViewById(R.id.btnBackRiwayat)
        btnBack.setOnClickListener {
            activity?.onBackPressedDispatcher?.onBackPressed()
        }

        // RecyclerView
        rvTransactions = view.findViewById(R.id.rvTransactions)

        // TextViews untuk Summary Card
        tvTotalDiamond = view.findViewById(R.id.tvTotalDiamond)
        tvBonus = view.findViewById(R.id.tvBonus)
        tvTransactionSummary = view.findViewById(R.id.tvTransactionSummary)
    }

    private fun setupRecyclerView() {
        rvTransactions.layoutManager = LinearLayoutManager(context)
        // Inisialisasi adapter dengan list KOSONG di awal
        transactionAdapter = TransactionAdapter(emptyList())
        rvTransactions.adapter = transactionAdapter
    }

    /**
     * Mengambil data transaksi dari Cloud Firestore untuk pengguna yang sedang login.
     */
    private fun fetchTransactions() {
        val currentUser = auth.currentUser
        if (currentUser == null) {
            Log.w("RiwayatFragment", "Tidak ada pengguna yang login untuk menampilkan riwayat.")
            Toast.makeText(context, "Silakan login untuk melihat riwayat.", Toast.LENGTH_SHORT).show()
            return
        }

        // Query ke koleksi 'transactions'
        db.collection("transactions")
            .whereEqualTo("userUid", currentUser.uid)
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener { documents ->
                if (!isAdded) return@addOnSuccessListener // Pastikan fragment masih 'attached'

                val transactionList = documents.toObjects(Transaction::class.java)

                // --- FUNGSI BARU DIPANGGIL DI SINI ---
                // Perbarui kartu ringkasan dengan data yang baru diambil
                updateSummaryCard(transactionList)

                // Berikan data baru ke adapter RecyclerView
                transactionAdapter.updateData(transactionList)

                Log.d("RiwayatFragment", "Berhasil mengambil dan menampilkan ${transactionList.size} transaksi.")
            }
            .addOnFailureListener { exception ->
                Log.w("RiwayatFragment", "Error mengambil dokumen: ", exception)
                Toast.makeText(context, "Gagal memuat riwayat.", Toast.LENGTH_SHORT).show()
            }
    }

    /**
     * Menghitung total dan memperbarui tampilan pada kartu ringkasan.
     * @param transactions Daftar transaksi yang akan dihitung.
     */
    private fun updateSummaryCard(transactions: List<Transaction>) {
        // Filter transaksi hanya untuk bulan dan tahun ini
        val calendar = Calendar.getInstance()
        val currentMonth = calendar.get(Calendar.MONTH)
        val currentYear = calendar.get(Calendar.YEAR)

        val transactionsThisMonth = transactions.filter {
            it.timestamp?.let { date ->
                calendar.time = date
                calendar.get(Calendar.MONTH) == currentMonth && calendar.get(Calendar.YEAR) == currentYear
            } ?: false
        }

        // Kalkulasi total
        var totalDiamond = 0
        var totalPrice = 0L // Gunakan Long untuk harga untuk menghindari overflow

        for (transaction in transactionsThisMonth) {
            // Kalkulasi total diamond (mengabaikan teks " Diamond")
            totalDiamond += transaction.diamondAmount.filter { it.isDigit() }.toIntOrNull() ?: 0

            // Kalkulasi total harga (mengabaikan "Rp " dan ".")
            val priceString = transaction.price.replace("Rp ", "").replace(".", "")
            totalPrice += priceString.toLongOrNull() ?: 0L
        }

        // Format angka menjadi format mata uang Indonesia
        val localeID = Locale("in", "ID")
        val numberFormat = NumberFormat.getCurrencyInstance(localeID)
        numberFormat.maximumFractionDigits = 0 // Hilangkan desimal (sen)

        val formattedTotalPrice = numberFormat.format(totalPrice)

        // Atur teks pada TextViews
        tvTotalDiamond.text = totalDiamond.toString()
        tvBonus.visibility = View.GONE // Sembunyikan bonus untuk sementara
        tvTransactionSummary.text = "${transactionsThisMonth.size} Transaksi • $formattedTotalPrice"
    }
}
