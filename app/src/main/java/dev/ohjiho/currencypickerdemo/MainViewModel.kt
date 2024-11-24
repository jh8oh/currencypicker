package dev.ohjiho.currencypickerdemo

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import java.util.Currency

class MainViewModel : ViewModel() {
    var selectedCurrency: Currency? = null

    fun setCurrency(currency: Currency) {
        selectedCurrency = currency
    }
}