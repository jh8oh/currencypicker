package dev.ohjiho.currencypickerdemo

import android.icu.util.Currency
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

data class MainUiState(
    val selectedCurrency: Currency? = null,
    val isDialogShown: Boolean = false,
)

class MainViewModel : ViewModel() {
    val selectedCurrency = MutableStateFlow<Currency?>(null)

    fun setCurrency(currency: Currency) {
        selectedCurrency.update { currency }
    }
}