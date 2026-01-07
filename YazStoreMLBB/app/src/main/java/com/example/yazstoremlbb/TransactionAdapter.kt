package com.example.yazstoremlbb

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.*

class TransactionAdapter(private var transactions: List<Transaction>) : RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder>() {

    inner class TransactionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // ID ini sekarang cocok dengan XML
        val tvDetails: TextView = itemView.findViewById(R.id.tvTransactionDetails)
        val tvBonus: TextView = itemView.findViewById(R.id.tvBonusAmount)
        val tvStatus: TextView = itemView.findViewById(R.id.tvTransactionStatus)
        val tvTransactionId: TextView = itemView.findViewById(R.id.tvTransactionId)
        val tvUserId: TextView = itemView.findViewById(R.id.tvUserId)
        val tvPaymentMethod: TextView = itemView.findViewById(R.id.tvPaymentMethod)
        val tvDate: TextView = itemView.findViewById(R.id.tvDate)
        val tvPrice: TextView = itemView.findViewById(R.id.tvTransactionPrice)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_transaction, parent, false)
        return TransactionViewHolder(view)
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        val transaction = transactions[position]

        // Mengisi data ke dalam semua TextView
        holder.tvDetails.text = "${transaction.diamondAmount} Diamond"
        holder.tvBonus.visibility = View.GONE // Sembunyikan bonus untuk sementara
        holder.tvStatus.text = transaction.status
        holder.tvTransactionId.text = transaction.transactionId
        holder.tvUserId.text = "${transaction.gameUserId} (${transaction.gameZoneId})"
        holder.tvPaymentMethod.text = transaction.paymentMethod
        holder.tvPrice.text = transaction.price

        // Mengatur warna status
        val statusColorRes = if (transaction.status.equals("SUCCESS", ignoreCase = true)) {
            R.color.success_green
        } else {
            R.color.error_red
        }
        holder.tvStatus.setTextColor(ContextCompat.getColor(holder.itemView.context, statusColorRes))

        // Format tanggal
        transaction.timestamp?.let { date ->
            val formatter = SimpleDateFormat("dd MMM yyyy - HH:mm", Locale.getDefault())
            holder.tvDate.text = formatter.format(date)
        } ?: run {
            holder.tvDate.text = "Tanggal tidak tersedia"
        }
    }

    override fun getItemCount(): Int {
        return transactions.size
    }

    fun updateData(newTransactions: List<Transaction>) {
        this.transactions = newTransactions
        notifyDataSetChanged()
    }
}
