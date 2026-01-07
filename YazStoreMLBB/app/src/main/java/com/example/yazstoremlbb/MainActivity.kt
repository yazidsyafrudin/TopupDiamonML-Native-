package com.example.yazstoremlbb

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomnavigation.BottomNavigationView

/**
 * MainActivity adalah Activity utama setelah pengguna login.
 * Ia bertanggung jawab untuk menampung semua Fragment utama (Home, Riwayat, Promo)
 * dan mengatur navigasi di antara mereka melalui BottomNavigationView dan perintah dari HomeFragment.
 *
 * Kelas ini juga mengimplementasikan OnMenuHomeClickListener untuk "mendengarkan"
 * event klik dari tombol-tombol menu di dalam HomeFragment.
 */
class MainActivity : AppCompatActivity(), OnMenuHomeClickListener {

    private val TAG = "MainActivity"
    private lateinit var bottomNav: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d(TAG, "onCreate: Activity Dibuat")

        // Penyesuaian padding untuk status bar agar konten tidak tertimpa oleh UI sistem.
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0)
            insets
        }

        // Inisialisasi BottomNavigationView
        bottomNav = findViewById(R.id.bottomNav)

        // Hanya tampilkan fragment default saat Activity pertama kali dibuat,
        // bukan saat konfigurasi berubah (misal: rotasi layar).
        if (savedInstanceState == null) {
            replaceFragment(HomeFragment())
            bottomNav.selectedItemId = R.id.menu_home
        }

        // Mengatur listener untuk navigasi utama di bagian bawah.
        setupBottomNavListener()
    }

    /**
     * Fungsi ini akan dipanggil ketika MainActivity yang sudah berjalan menerima
     * sebuah Intent baru (misalnya dari SuccessActivity).
     */
    // ========================================================
    //      PERBAIKAN UTAMA ADA DI SINI: TANDA TANYA (?) DIHAPUS
    // ========================================================
    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        Log.d(TAG, "onNewIntent: Menerima intent baru.")
        handleIntentExtras(intent)
    }

    /**
     * Memeriksa "pesan" atau extra di dalam Intent yang diterima.
     */
    private fun handleIntentExtras(intent: Intent) { // <-- TANDA TANYA (?) DIHAPUS
        // Periksa apakah ada "pesan" untuk kembali ke Home
        if (intent.getBooleanExtra("GO_TO_HOME_FRAGMENT", false)) { // <-- Tanda tanya (?) tidak perlu lagi
            Log.d(TAG, "Intent extra: GO_TO_HOME_FRAGMENT diterima.")
            // Bersihkan semua fragment dari back stack
            supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
            // Ganti dengan HomeFragment
            replaceFragment(HomeFragment())
            // Atur item yang aktif di bottom nav ke Home
            bottomNav.selectedItemId = R.id.menu_home
        }

        // Periksa apakah ada "pesan" untuk kembali ke halaman Top Up
        if (intent.getBooleanExtra("GO_TO_TOPUP_FRAGMENT", false)) { // <-- Tanda tanya (?) tidak perlu lagi
            Log.d(TAG, "Intent extra: GO_TO_TOPUP_FRAGMENT diterima.")
            supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
            replaceFragment(TopupFragment())
            // Tidak perlu mengubah selectedItemId karena TopupFragment bukan bagian dari menu utama
        }
    }


    /**
     * Mengatur listener untuk BottomNavigationView.
     * Fungsi ini menentukan tindakan apa yang harus diambil saat setiap item menu diklik.
     */
    private fun setupBottomNavListener() {
        bottomNav.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu_home -> {
                    // Saat kembali ke Home, bersihkan semua back stack agar Home jadi dasar lagi.
                    supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
                    replaceFragment(HomeFragment())
                    true
                }
                R.id.menu_riwayat -> {
                    replaceFragment(RiwayatFragment())
                    true
                }
                R.id.menu_promo -> {
                    replaceFragment(PromoFragment())
                    true
                }
                R.id.menu_profile -> {
                    // Untuk profil, kita buka Activity baru, bukan Fragment.
                    val intent = Intent(this, ProfileActivity::class.java)
                    startActivity(intent)
                    // Kembalikan false agar highlight tidak berpindah permanen ke ikon profil.
                    false
                }
                else -> false
            }
        }
    }

    /**
     * Fungsi helper untuk mengganti Fragment di dalam 'fragmentContainer'.
     * @param fragment Fragment yang akan ditampilkan.
     */
    private fun replaceFragment(fragment: Fragment) {
        try {
            val fragmentName = fragment.javaClass.simpleName
            Log.d(TAG, "replaceFragment: Mengganti dengan $fragmentName")

            val transaction = supportFragmentManager.beginTransaction()
            transaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
            transaction.replace(R.id.fragmentContainer, fragment)

            // Perubahan Kunci:
            // JANGAN tambahkan HomeFragment ke back stack, karena itu adalah halaman dasar.
            // Semua fragment lain akan ditambahkan ke tumpukan agar tombol 'kembali' berfungsi.
            if (fragment !is HomeFragment) {
                transaction.addToBackStack(fragmentName)
            }

            transaction.commit()

            Log.d(TAG, "replaceFragment: Transaksi untuk $fragmentName berhasil di-commit.")
        } catch (e: Exception) {
            Log.e(TAG, "Error saat mengganti fragment: ", e)
        }
    }

    /**
     * Fungsi ini adalah implementasi dari interface OnMenuHomeClickListener.
     * Akan dipanggil dari HomeFragment setiap kali salah satu menu di dalamnya diklik.
     * @param menuId ID dari menu yang diklik di dalam HomeFragment.
     */
    override fun onMenuHomeClick(menuId: Int) {
        Log.d(TAG, "onMenuHomeClick: Menerima klik dari HomeFragment dengan ID: $menuId")

        when (menuId) {
            // Jika yang diklik adalah menu Riwayat di HomeFragment
            R.id.menu_riwayat -> {
                replaceFragment(RiwayatFragment())
                // Secara manual, atur item yang aktif di BottomNavigationView ke ikon riwayat.
                bottomNav.selectedItemId = R.id.menu_riwayat
            }

            // Jika yang diklik adalah menu Promo di HomeFragment
            R.id.menu_promo -> {
                replaceFragment(PromoFragment())
                // Atur item yang aktif di BottomNavigationView ke ikon promo.
                bottomNav.selectedItemId = R.id.menu_promo
            }

            // Jika yang diklik adalah menu Topup (yang tidak ada di BottomNavigationView)
            R.id.menuTopup -> {
                // Cukup ganti fragment-nya saja, tidak perlu mengubah highlight di bottom nav.
                replaceFragment(TopupFragment())
            }

            // Jika yang diklik adalah menu Saldo
            R.id.menuSaldo -> {
                replaceFragment(SaldoFragment())
            }
        }
    }
}
