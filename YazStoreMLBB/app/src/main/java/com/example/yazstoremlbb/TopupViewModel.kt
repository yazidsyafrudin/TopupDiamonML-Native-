package com.example.yazstoremlbb

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * ViewModel untuk TopupFragment.
 * Bertugas untuk menyimpan state (data) dari UI dan memisahkan logika bisnis dari tampilan.
 * Data di sini akan tetap ada bahkan saat layar dirotasi.
 */
class TopupViewModel : ViewModel() {

    // --- Data yang akan diamati oleh UI (Fragment) ---

    // Menyimpan User ID yang diinput. Dibuat private agar hanya bisa diubah dari dalam ViewModel.
    private val _userId = MutableLiveData<String?>()
    // Versi public yang hanya bisa dibaca (read-only) oleh UI.
    val userId: LiveData<String?> = _userId

    // Menyimpan Zone ID yang diinput.
    private val _zoneId = MutableLiveData<String?>()
    val zoneId: LiveData<String?> = _zoneId

    // Menyimpan paket diamond yang dipilih pengguna.
    private val _selectedDiamond = MutableLiveData<DiamondPackage?>()
    val selectedDiamond: LiveData<DiamondPackage?> = _selectedDiamond

    // Menyimpan metode pembayaran yang dipilih.
    private val _selectedPayment = MutableLiveData<String?>()
    val selectedPayment: LiveData<String?> = _selectedPayment

    // Menyimpan status apakah semua input sudah valid dan siap untuk checkout.
    // Diberi nilai awal 'false'.
    private val _isReadyForCheckout = MutableLiveData<Boolean>(false)
    val isReadyForCheckout: LiveData<Boolean> = _isReadyForCheckout


    // --- Fungsi untuk memperbarui data dari UI ---

    /**
     * Dipanggil dari Fragment saat pengguna mengetik di kolom User ID.
     */
    fun setUserId(id: String?) {
        if (_userId.value != id) {
            _userId.value = id
            validateInputs()
        }
    }

    /**
     * Dipanggil dari Fragment saat pengguna mengetik di kolom Zone ID.
     */
    fun setZoneId(id: String?) {
        if (_zoneId.value != id) {
            _zoneId.value = id
            validateInputs()
        }
    }

    /**
     * Dipanggil dari Adapter saat pengguna memilih paket diamond.
     */
    fun setSelectedDiamond(diamondPackage: DiamondPackage?) {
        if (_selectedDiamond.value != diamondPackage) {
            _selectedDiamond.value = diamondPackage
            validateInputs()
        }
    }

    /**
     * Dipanggil dari Fragment saat pengguna memilih metode pembayaran.
     */
    fun setSelectedPayment(paymentMethod: String?) {
        if (_selectedPayment.value != paymentMethod) {
            _selectedPayment.value = paymentMethod
            validateInputs()
        }
    }


    // --- Logika Validasi Internal ---

    /**
     * Fungsi private yang dipanggil setiap kali ada data yang berubah.
     * Tugasnya adalah memeriksa apakah semua kondisi untuk checkout sudah terpenuhi.
     */
    private fun validateInputs() {
        val isUserIdValid = !_userId.value.isNullOrBlank()
        val isZoneIdValid = !_zoneId.value.isNullOrBlank()
        val isDiamondSelected = _selectedDiamond.value != null
        val isPaymentSelected = !_selectedPayment.value.isNullOrBlank()

        // Nilai _isReadyForCheckout akan menjadi 'true' hanya jika SEMUA kondisi di atas terpenuhi.
        // UI (Fragment) akan "mendengarkan" perubahan nilai ini.
        val isReady = isUserIdValid && isZoneIdValid && isDiamondSelected && isPaymentSelected
        if (_isReadyForCheckout.value != isReady) {
            _isReadyForCheckout.value = isReady
        }
    }
}
