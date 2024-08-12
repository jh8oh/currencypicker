package dev.ohjiho.currencypicker

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import dev.ohjiho.currencypicker.databinding.CurrencySpinnerBinding
import java.util.Currency

class CurrencySpinner(
    context: Context,
    attr: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : LinearLayout(context, attr, defStyleAttr), CurrencyAdapter.Listener {

    private var listener: Listener? = null

    interface Listener {
        fun onCurrencySelected(currency: Currency)
    }

    init {
        val binding = CurrencySpinnerBinding.inflate(LayoutInflater.from(context), this, true)

        val currencyAdapter = CurrencyAdapter(this)
        currencyAdapter.setHasStableIds(true)

        binding.searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean {
                currencyAdapter.filter.filter(newText)
                return false
            }

            override fun onQueryTextSubmit(query: String?) = false
        })

        binding.recyclerView.apply {
            adapter = currencyAdapter
            layoutManager = LinearLayoutManager(context)
            setItemViewCacheSize(25)
        }
    }

    fun setListener(listener: Listener) {
        this.listener = listener
    }

    override fun onItemSelected(currency: Currency) {
        if (listener == null) {
            try {
                (context as Listener).onCurrencySelected(currency)
            } catch (e: ClassCastException) {
                Log.e("CurrencySpinner", "Context must implement CurrencySpinner.Listener")
                throw e
            }
        } else {
            listener!!.onCurrencySelected(currency)
        }
    }
}