package com.example.yazstoremlbb

data class DiamondPackage(
    val diamondAmount: String,
    val bonusAmount: String? = null, // Bonus bisa ada atau tidak (nullable)
    val price: String,
    var isSelected: Boolean = false
)
