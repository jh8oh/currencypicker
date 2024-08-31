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

internal class CurrencyAdapter(
    private val listener: Listener,
    private val selectedCurrency: CurrencyCode?,
    popularCurrency: Boolean,
) :
    RecyclerView.Adapter<CurrencyAdapter.ViewHolder>(), Filterable {

    private val currencies: List<CurrencyCode> = if (popularCurrency) CurrencyCode.getPopularCurrency() else CurrencyCode.entries
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

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        // Very hacky. Need to delay enough for recyclerview to be set up properly
        recyclerView.postDelayed(
            { recyclerView.scrollToPosition(filteredCurrencies.indexOf(selectedCurrency)) },
            200
        )
    }

    inner class ViewHolder(private val binding: ItemCurrencyBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(currencyCode: CurrencyCode) {
            with(binding) {
                root.isSelected = selectedCurrency == currencyCode
                currencyFlag.setImageDrawable(ContextCompat.getDrawable(itemView.context, currencyCode.resId))
                currencyShortCode.text = currencyCode.name
                currencyLongName.text = Currency.getInstance(currencyCode.name).displayName
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemCurrencyBinding.inflate(LayoutInflater.from(parent.context), parent, false)).apply {
            itemView.setOnClickListener {
                listener.onItemSelected(filteredCurrencies[adapterPosition])
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(filteredCurrencies[position])
    }

    override fun getItemCount() = filteredCurrencies.size

    override fun getItemId(position: Int): Long {
        return filteredCurrencies[position].ordinal.toLong()
    }

    override fun getFilter(): Filter = filter

    interface Listener {
        fun onItemSelected(currencyCode: CurrencyCode)
    }
}