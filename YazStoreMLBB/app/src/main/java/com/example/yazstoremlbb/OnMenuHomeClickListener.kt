package com.example.yazstoremlbb

/**
 * Interface ini berfungsi sebagai "jembatan" atau "kontrak"
 * antara HomeFragment dan MainActivity.
 *
 * Tujuannya adalah agar HomeFragment bisa "berbicara" ke MainActivity
 * tanpa harus mengenalnya secara langsung (prinsip decoupling).
 * HomeFragment hanya perlu tahu bahwa ada yang bisa merespon klik menu.
 */
interface OnMenuHomeClickListener {
    /**
     * Metode ini akan dipanggil di dalam HomeFragment dan diimplementasikan
     * (diberi logika) di dalam MainActivity.
     * @param menuId ID dari view yang diklik di dalam HomeFragment,
     *               seperti R.id.menuRiwayat, R.id.btnTopUpHome, dll.
     */
    fun onMenuHomeClick(menuId: Int)
}
