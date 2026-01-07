package com.example.yazstoremlbb

import com.google.firebase.firestore.ServerTimestamp
import java.util.Date


data class Transaction(
    val transactionId: String = "",
    val userUid: String = "",
    val gameUserId: String = "",
    val gameZoneId: String = "",
    val diamondAmount: String = "",
    val price: String = "",
    val paymentMethod: String = "",
    val status: String = "",

    // 1. Anotasi @ServerTimestamp ditempatkan di sini.
    // 2. Tipe data diubah menjadi Date? (nullable) dan nilai default-nya null.
    @ServerTimestamp
    val timestamp: Date? = null
)
