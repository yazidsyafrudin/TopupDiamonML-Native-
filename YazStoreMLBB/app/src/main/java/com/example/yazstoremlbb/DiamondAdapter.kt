package com.example.yazstoremlbb

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView

/**
 * Adapter untuk menampilkan daftar paket diamond di dalam RecyclerView.
 * @param packages Daftar paket diamond yang akan ditampilkan.
 * @param onItemClick Sebuah fungsi (lambda) yang akan dipanggil setiap kali sebuah item diklik.
 */
class DiamondAdapter(
    private val packages: List<DiamondPackage>,
    private val onItemClick: (DiamondPackage) -> Unit // Callback untuk memberitahu Fragment
) : RecyclerView.Adapter<DiamondAdapter.DiamondViewHolder>() {

    // Variabel untuk melacak posisi item yang sedang dipilih
    private var selectedPosition = -1

    /**
     * ViewHolder menyimpan referensi ke semua view di dalam satu item layout (item_diamond_package.xml).
     */
    inner class DiamondViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cardView: MaterialCardView = itemView.findViewById(R.id.cardPackage)
        val ivCheck: ImageView = itemView.findViewById(R.id.ivCheck)
        val tvDiamonds: TextView = itemView.findViewById(R.id.tvDiamonds)
        val tvBonus: TextView = itemView.findViewById(R.id.tvBonus)
        val tvPrice: TextView = itemView.findViewById(R.id.tvPrice)

        init {
            // Menangani klik pada setiap kartu item
            cardView.setOnClickListener {
                val currentPosition = adapterPosition
                if (currentPosition != RecyclerView.NO_POSITION) {
                    // --- Logika untuk mengelola status terpilih (selected) ---

                    // 1. Batalkan pilihan pada item yang sebelumnya dipilih (jika ada)
                    if (selectedPosition != -1 && selectedPosition != currentPosition) {
                        packages[selectedPosition].isSelected = false
                        notifyItemChanged(selectedPosition)
                    }

                    // 2. Atur item yang baru diklik sebagai terpilih
                    selectedPosition = currentPosition
                    packages[selectedPosition].isSelected = true
                    notifyItemChanged(selectedPosition)

                    // 3. Panggil callback untuk memberitahu Fragment/ViewModel tentang item yang dipilih
                    onItemClick(packages[currentPosition])
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiamondViewHolder {
        // Menghubungkan adapter dengan layout XML untuk satu item
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_diamond_package, parent, false)
        return DiamondViewHolder(view)
    }

    override fun onBindViewHolder(holder: DiamondViewHolder, position: Int) {
        // Mengambil data paket diamond untuk posisi saat ini
        val currentPackage = packages[position]

        // Mengisi data dari model ke dalam komponen UI (View)
        holder.tvDiamonds.text = currentPackage.diamondAmount
        holder.tvPrice.text = currentPackage.price

        // Mengatur teks dan visibilitas untuk bonus
        if (currentPackage.bonusAmount != null) {
            holder.tvBonus.text = "+${currentPackage.bonusAmount} Bonus"
            holder.tvBonus.visibility = View.VISIBLE // Tampilkan jika ada bonus
        } else {
            holder.tvBonus.visibility = View.GONE // Sembunyikan jika tidak ada bonus
        }

        // Mengatur status visual item (terpilih atau tidak)
        holder.cardView.isChecked = currentPackage.isSelected
        holder.ivCheck.visibility = if (currentPackage.isSelected) View.VISIBLE else View.INVISIBLE
    }

    override fun getItemCount(): Int {
        // Mengembalikan jumlah total item dalam daftar
        return packages.size
    }
}
