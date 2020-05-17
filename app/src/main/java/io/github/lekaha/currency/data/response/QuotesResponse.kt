package io.github.lekaha.currency.data.response

data class QuotesResponse(
    val success: Boolean,
    val terms: String?,
    val privacy: String?,
    val quotes: Map<String, Double>?,
    val error: Map<String, Any>?
)