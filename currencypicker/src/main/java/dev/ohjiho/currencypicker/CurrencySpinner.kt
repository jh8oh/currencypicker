package dev.ohjiho.currencypicker

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
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

    // Binding
    private val binding = CurrencySpinnerBinding.inflate(LayoutInflater.from(context), this, true)

    // Attributes
    var popularCurrency = true
        set(value) {
            field = value
            setUpSpinner()
        }

    private var showMoreClicked = false
        set(value) {
            field = value
            setUpSpinner()
        }

    private var selectedCurrencyCode: String? = null
        set(value) {
            field = value
            setUpSpinner()
        }

    // Listener
    private var listener: Listener? = null

    interface Listener {
        fun onCurrencySelected(currency: Currency)
    }

    init {
        setUpSpinner()

        binding.showMoreButton.setOnClickListener {
            showMoreClicked = true
        }
    }

    private fun setUpSpinner() {
        val currencyAdapter = CurrencyAdapter(this, selectedCurrencyCode,popularCurrency && !showMoreClicked)
        currencyAdapter.setHasStableIds(true)

        binding.searchBar.setQuery("", false)
        binding.searchBar.clearFocus()
        if (popularCurrency && !showMoreClicked) {
            binding.searchBar.visibility = View.GONE
            binding.showMoreButton.visibility = View.VISIBLE
        } else {
            binding.searchBar.visibility = View.VISIBLE
            binding.searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextChange(newText: String?): Boolean {
                    currencyAdapter.filter.filter(newText)
                    return false
                }

                override fun onQueryTextSubmit(query: String?) = false
            })
            binding.showMoreButton.visibility = View.GONE
        }

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
                if (currency.currencyCode in CurrencyCode.getPopularCurrency().map { it.name }) {
                    showMoreClicked = false
                }
                selectedCurrencyCode = currency.currencyCode
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