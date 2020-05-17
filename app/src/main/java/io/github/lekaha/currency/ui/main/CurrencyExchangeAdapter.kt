package io.github.lekaha.currency.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import io.github.lekaha.currency.R
import io.github.lekaha.currency.databinding.LayoutCurrencyExchangeItemBinding
import io.github.lekaha.currency.ui.model.CurrencyExchangeItem

class CurrencyExchangeAdapter : RecyclerView.Adapter<CurrencyExchangeAdapter.ViewHolder>() {

    private val dataList: MutableList<CurrencyExchangeItem> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = DataBindingUtil.inflate<LayoutCurrencyExchangeItemBinding>(
            LayoutInflater.from(parent.context),
            R.layout.layout_currency_exchange_item,
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount() = dataList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.viewModel = dataList[position]
    }

    fun setData(newList: List<CurrencyExchangeItem>) {
        val diffCallback = DiffCallback(dataList, newList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        dataList.clear()
        dataList.addAll(newList)
        diffResult.dispatchUpdatesTo(this)
    }

    class ViewHolder(val binding: LayoutCurrencyExchangeItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    class DiffCallback(
        private val oldList: List<CurrencyExchangeItem>,
        private val newList: List<CurrencyExchangeItem>
    ) : DiffUtil.Callback() {
        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            oldList[oldItemPosition].amount == newList[newItemPosition].amount

        override fun getOldListSize() = oldList.size

        override fun getNewListSize() = newList.size

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            (oldList[oldItemPosition].profile.currencyRate.from ==
                    newList[newItemPosition].profile.currencyRate.from) &&
                    (oldList[oldItemPosition].profile.currencyRate.to ==
                            newList[newItemPosition].profile.currencyRate.to)

    }
}