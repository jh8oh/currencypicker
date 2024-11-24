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
    var popularCurrency = true
        set(value) {
            field = value
            onPopularCurrencyOrShowMoreClickedChange()
        }

    private var showMoreClicked = false
        set(value) {
            field = value
            onPopularCurrencyOrShowMoreClickedChange()
        }

    // Listener
    private var listener: Listener? = null

    interface Listener {
        fun onCurrencySelected(currency: Currency)
    }

    init {
        currencyAdapter.setHasStableIds(true)
        binding.searchBar.visibility = View.GONE

        binding.recyclerView.apply {
            adapter = currencyAdapter
            layoutManager = LinearLayoutManager(context)
            setItemViewCacheSize(25)
        }
    }

    private fun onPopularCurrencyOrShowMoreClickedChange() {
        binding.searchBar.setQuery("", false)
        binding.searchBar.clearFocus()

        currencyAdapter.popularCurrency = (popularCurrency && !showMoreClicked)
        if (popularCurrency && !showMoreClicked) {
            binding.searchBar.visibility = View.GONE
        } else {
            binding.searchBar.visibility = View.VISIBLE
            binding.searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextChange(newText: String?): Boolean {
                    currencyAdapter.filter.filter(newText)
                    return false
                }

                override fun onQueryTextSubmit(query: String?) = false
            })
        }
    }

    fun setSelectedCurrency(currency: Currency) {
        if (currency.currencyCode !in CurrencyCode.getPopularCurrency().map { it.name } && popularCurrency && !showMoreClicked){
            showMoreClicked = true
        }
        currencyAdapter.selectedCurrency = CurrencyCode.valueOf(currency.currencyCode)
        if (!currencyAdapter.popularCurrency) {
            binding.recyclerView.postDelayed(
                {
                    (binding.recyclerView.layoutManager as LinearLayoutManager).scrollToPositionWithOffset(
                        currencyAdapter.filteredCurrencies.indexOf(CurrencyCode.valueOf(currency.currencyCode)),
                        0
                    )
                },
                200
            )
        }
    }

    fun setListener(listener: Listener) {
        this.listener = listener
    }

    override fun showMoreClicked(selectedCurrency: CurrencyCode?) {
        showMoreClicked = true
        currencyAdapter.selectedCurrency = selectedCurrency

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

    override fun onItemSelected(currencyCode: CurrencyCode) {
        binding.searchBar.setQuery("", false)
        binding.searchBar.clearFocus()
        if (currencyCode in CurrencyCode.getPopularCurrency() && showMoreClicked) {
            showMoreClicked = false
            currencyAdapter.selectedCurrency = currencyCode
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
}