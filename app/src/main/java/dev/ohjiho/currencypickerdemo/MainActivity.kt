package dev.ohjiho.currencypickerdemo

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import dev.ohjiho.currencypicker.CurrencyPicker
import dev.ohjiho.currencypickerdemo.databinding.ActivityMainBinding
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Currency

class MainActivity : AppCompatActivity(), CurrencyPicker.Listener {

    private val viewModel by viewModels<MainViewModel>()
    private lateinit var binding: ActivityMainBinding

    private lateinit var dialog: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        val spinner = CurrencyPicker(this)
        dialog = AlertDialog.Builder(this).setView(spinner).create()
        binding.button.setOnClickListener {
            dialog.show()
        }

        viewModel.selectedCurrency?.let { spinner.setSelectedCurrency(it) }

        setContentView(binding.root)
    }

    override fun onCurrencySelected(currency: Currency) {
        viewModel.setCurrency(currency)
        binding.text.text = getString(R.string.text_placeholder, currency.displayName ?: "None")
    }
}
