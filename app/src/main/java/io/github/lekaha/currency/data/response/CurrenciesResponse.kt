package io.github.lekaha.currency.data.response

data class CurrenciesResponse(
    val success: Boolean,
    val terms: String?,
    val privacy: String?,
    val currencies: Map<String, String>?,
    val error: Map<String, Any>?
)