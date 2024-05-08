# Currency Picker

An Android library that provides `CurrencySpinner`, a view consisting of a `SearchView` and a `RecyclerView` which contains all the currently used currencies.

<img src="https://github.com/jh8oh/currency-picker/assets/54990767/6321da1d-98dd-4e4c-ab27-12672f766d1b" height="660"/>
<img src="https://github.com/jh8oh/currency-picker/assets/54990767/90e9124c-3e35-40b5-ba87-a14af08551c1" height="660"/>
<img src="https://github.com/jh8oh/currency-picker/assets/54990767/f177c1c7-f25c-4ab4-83be-db74dfb6b073" height="660"/>

## Implementation

Add the dependency

```gradle
implementation "dev.ohjiho:currency-picker:1.0.0"
```

## Methods

Requires whatever context using `CurrencySpinner` to extend `CurrencySpinner.Listener`:
```kotlin
interface CurrencySpinner.Listener {
  fun onCurrencySelected(currency: Currency)
}
```
