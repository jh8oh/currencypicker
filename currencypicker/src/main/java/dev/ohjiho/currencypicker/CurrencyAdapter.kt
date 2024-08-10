package dev.ohjiho.currencypicker

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import dev.ohjiho.currencypicker.databinding.ItemCurrencyBinding
import java.util.Currency

internal class CurrencyAdapter(private val listener: Listener) :
    RecyclerView.Adapter<CurrencyAdapter.ViewHolder>(), Filterable {

    private val currencies: List<CurrencyCode> = CurrencyCode.entries
    private var filteredCurrencies = currencies
    private val filter = object : Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val filtered = if (constraint.isNullOrEmpty()) {
                currencies
            } else {
                val lowerCaseConstraint = constraint.toString().lowercase()
                currencies.filter {
                    it.name.lowercase().contains(lowerCaseConstraint)
                            || Currency.getInstance(it.name).displayName.lowercase().contains(lowerCaseConstraint)
                }
            }
            return FilterResults().apply {
                values = filtered
            }
        }

        @SuppressLint("NotifyDataSetChanged")
        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            filteredCurrencies = if (results?.values == null) {
                listOf()
            } else results.values as List<CurrencyCode>
            notifyDataSetChanged()
        }
    }

    interface Listener {
        fun onItemSelected(currency: Currency)
    }

    inner class ViewHolder(private val binding: ItemCurrencyBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(currencyCode: CurrencyCode) {
            val currency = Currency.getInstance(currencyCode.name)
            binding.currencyFlag.setImageDrawable(ContextCompat.getDrawable(itemView.context, currencyCode.resId))
            binding.currencyCode.text = currencyCode.name
            binding.currencyLongName.text = currency.displayName

            binding.root.setOnClickListener {
                listener.onItemSelected(currency)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemCurrencyBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(filteredCurrencies[position])
    }

    override fun getItemCount() = filteredCurrencies.size

    override fun getFilter(): Filter = filter
}