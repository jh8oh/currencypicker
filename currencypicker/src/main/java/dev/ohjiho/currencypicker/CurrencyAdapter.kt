package dev.ohjiho.currencypicker

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import dev.ohjiho.currencypicker.databinding.ItemCurrencyBinding
import dev.ohjiho.currencypicker.databinding.ItemShowMoreBinding
import java.util.Currency

class CurrencyAdapter(private val listener: Listener) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>(), Filterable {

    var isPopularCurrencyBeingShown: Boolean = true
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            if (field != value){
                currencies = if (value) CurrencyCode.getPopularCurrency() else CurrencyCode.entries
                filteredCurrencies = currencies
                notifyDataSetChanged()
                field = value
            }
        }

    var selectedCurrency: CurrencyCode = CurrencyCode.USD
        set(value) {
            filteredCurrencies.indexOf(value).let {
                if (it != -1) {
                    notifyItemChanged(it)
                }
            }
            filteredCurrencies.indexOf(field).let {
                if (it != -1) {
                    notifyItemChanged(it)
                }
            }

            field = value
        }

    private var currencies: List<CurrencyCode> = CurrencyCode.getPopularCurrency()
    var filteredCurrencies = currencies
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

    inner class CurrencyViewHolder(private val binding: ItemCurrencyBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(currencyCode: CurrencyCode) {
            with(binding) {
                root.isSelected = selectedCurrency == currencyCode
                currencyFlag.setImageDrawable(ContextCompat.getDrawable(itemView.context, currencyCode.resId))
                currencyShortCode.text = currencyCode.name
                currencyLongName.text = Currency.getInstance(currencyCode.name).displayName
            }

        }
    }

    inner class ShowMoreViewHolder(binding: ItemShowMoreBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == TYPE_CURRENCY) {
            CurrencyViewHolder(ItemCurrencyBinding.inflate(LayoutInflater.from(parent.context), parent, false)).apply {
                itemView.setOnClickListener {
                    listener.onItemSelected(filteredCurrencies[adapterPosition])
                }
            }
        } else {
            ShowMoreViewHolder(ItemShowMoreBinding.inflate(LayoutInflater.from(parent.context), parent, false)).apply {
                itemView.setOnClickListener { listener.onShowMoreClick(selectedCurrency) }
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (position < filteredCurrencies.size) {
            (holder as CurrencyViewHolder).bind(filteredCurrencies[position])
        }
    }

    override fun getItemCount() = if (isPopularCurrencyBeingShown) filteredCurrencies.size + 1 else filteredCurrencies.size

    override fun getItemId(position: Int): Long {
        return if (position < filteredCurrencies.size) filteredCurrencies[position].ordinal.toLong() else -1
    }

    override fun getItemViewType(position: Int): Int {
        return if (position < filteredCurrencies.size) TYPE_CURRENCY
        else TYPE_SHOW_MORE
    }

    override fun getFilter(): Filter = filter

    interface Listener {
        fun onItemSelected(currencyCode: CurrencyCode)
        fun onShowMoreClick(selectedCurrency: CurrencyCode)
    }

    companion object {
        private const val TYPE_CURRENCY = 0
        private const val TYPE_SHOW_MORE = 1
    }
}