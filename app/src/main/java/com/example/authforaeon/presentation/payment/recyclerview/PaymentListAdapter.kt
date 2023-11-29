package com.example.authforaeon.presentation.payment.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.authforaeon.databinding.PaymentItemBinding
import com.example.authforaeon.domain.model.PaymentModel
import java.text.SimpleDateFormat
import java.util.Locale


class PaymentListAdapter(): RecyclerView.Adapter<PaymentListAdapter.PaymentHolder>() {

    private val diffCallback = object : DiffUtil.ItemCallback<PaymentModel>() {
        override fun areItemsTheSame(oldItem: PaymentModel, newItem: PaymentModel): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: PaymentModel, newItem: PaymentModel): Boolean =
            oldItem.hashCode() == newItem.hashCode()
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    fun submitList(list: List<PaymentModel>) = differ.submitList(list)

    inner class PaymentHolder(val binding: PaymentItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaymentHolder {
        val binding = PaymentItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PaymentHolder(binding)
    }

    override fun onBindViewHolder(holder: PaymentHolder, position: Int) {
        val item = differ.currentList[position]

        holder.binding.apply {
            title.text = item.title
            if (!item.amount.isNullOrBlank())
                amount.text = item.amount.toString()

            if (item.created != null) {
                val sdf = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
                val date = sdf.format(item.created)
                dateText.text = date
            }
        }
    }

    override fun getItemCount(): Int =
        differ.currentList.size

}

