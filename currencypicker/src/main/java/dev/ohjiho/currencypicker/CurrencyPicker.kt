package dev.ohjiho.currencypicker

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import dev.ohjiho.currencypicker.databinding.CurrencySpinnerBinding
import java.util.Currency

class CurrencyPicker @JvmOverloads constructor(
    context: Context,
    attr: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : ConstraintLayout(context, attr, defStyleAttr), CurrencyAdapter.Listener {

    // Binding
    val currencyAdapter: CurrencyAdapter = CurrencyAdapter(this)
    private val binding = CurrencySpinnerBinding.inflate(LayoutInflater.from(context), this, true)

    // Attributes
    var showPopularCurrencyByDefault = true
        set(value) {
            if (field != value) {
                field = value
                onPopularCurrencyOrShowFullCurrencyChange()
            }
        }

    private var showFullCurrency = false
        set(value) {
            if (field != value) {
                field = value
                onPopularCurrencyOrShowFullCurrencyChange()
            }
        }

    // Listener
    private var listener: Listener? = null

    interface Listener {
        fun onCurrencySelected(currency: Currency)
    }

    init {
        currencyAdapter.setHasStableIds(true)
        onPopularCurrencyOrShowFullCurrencyChange()

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

    private fun onPopularCurrencyOrShowFullCurrencyChange() {
        val isPopularCurrencyShown = (showPopularCurrencyByDefault && !showFullCurrency)
        currencyAdapter.isPopularCurrencyBeingShown = isPopularCurrencyShown
        if (isPopularCurrencyShown) {
            binding.searchBar.visibility = View.GONE
        } else {
            binding.searchBar.visibility = View.VISIBLE
        }
    }

    fun setSelectedCurrency(currency: Currency) {
        val currencyCode = CurrencyCode.valueOf(currency.currencyCode)
        if (currencyCode !in CurrencyCode.getPopularCurrency() && currencyAdapter.isPopularCurrencyBeingShown){
            showFullCurrency = true
            scrollToSelectedCurrency(CurrencyCode.valueOf(currency.currencyCode))
        }
        if (!currencyAdapter.isPopularCurrencyBeingShown && binding.searchBar.query.isNotBlank()) {
            clearSearchBar()
            scrollToSelectedCurrency(currencyCode)
        }
        currencyAdapter.selectedCurrency = currencyCode
    }

    fun setListener(listener: Listener) {
        this.listener = listener
    }

    override fun onItemSelected(currencyCode: CurrencyCode) {
        currencyAdapter.selectedCurrency = currencyCode
        if (currencyCode in CurrencyCode.getPopularCurrency() && showPopularCurrencyByDefault && showFullCurrency) {
            showFullCurrency = false
            clearSearchBar()
        }

        if (listener == null) {
            try {
                (context as Listener).onCurrencySelected(Currency.getInstance(currencyCode.name))
            } catch (e: ClassCastException) {
                Log.e("CurrencySpinner", "Context must implement CurrencySpinner.Listener")
                throw e
            }
        } else {
            listener!!.onCurrencySelected(Currency.getInstance(currencyCode.name))
        }
    }

    override fun onShowMoreClick(selectedCurrency: CurrencyCode) {
        showFullCurrency = true
        scrollToSelectedCurrency(selectedCurrency)
    }

    private fun clearSearchBar() {
        binding.searchBar.setQuery("", false)
        binding.searchBar.clearFocus()
    }

    private fun scrollToSelectedCurrency(selectedCurrency: CurrencyCode) {
        binding.recyclerView.postDelayed(
            {
                (binding.recyclerView.layoutManager as LinearLayoutManager).scrollToPositionWithOffset(
                    currencyAdapter.filteredCurrencies.indexOf(selectedCurrency),
                    0
                )
            },
            200
        )
    }
}