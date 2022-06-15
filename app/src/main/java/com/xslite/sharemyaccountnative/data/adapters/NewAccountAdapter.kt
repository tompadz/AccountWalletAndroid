package com.xslite.sharemyaccountnative.data.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.xslite.sharemyaccountnative.data.models.AccountSectionModel
import com.xslite.sharemyaccountnative.databinding.ItemSortableSocialBinding
import com.xslite.sharemyaccountnative.databinding.WidgetNewAccountFooterBinding
import com.xslite.sharemyaccountnative.ui.custom.FooterRecyclerView
import com.xslite.sharemyaccountnative.util.listeners.OnAccountClickListener

/**
 * The RecyclerView adapter for the account creation page accepts a list of accounts broken down into sections.
 * @param listener - Click listener for accounts;
 * @see listener
 */
class NewAccountAdapter(private val listener : OnAccountClickListener) :
    FooterRecyclerView<AccountSectionModel>() {

    sealed class ViewHolder(binding : ViewBinding) : RecyclerView.ViewHolder(binding.root) {

        class ItemViewHolder(
            private val binding : ItemSortableSocialBinding,
        ) : ViewHolder(binding) {
            fun bind(
                account : AccountSectionModel,
                isSingleSection : Boolean,
                pool : RecyclerView.RecycledViewPool,
                listener : OnAccountClickListener
            ) {
                binding.apply {
                    if (isSingleSection) {
                        textSortTitle.visibility = View.GONE
                    } else {
                        textSortTitle.visibility = View.VISIBLE
                        textSortTitle.setText(account.title)
                    }
                    val adapter = AccountAdapter(listener)
                    recyclerView.setHasFixedSize(true)
                    recyclerView.setRecycledViewPool(pool)
                    recyclerView.adapter = adapter
                    adapter.setData(account.items)
                }
            }
        }

        class FooterViewHolder(binding : WidgetNewAccountFooterBinding) :
            ViewHolder(binding) {
            fun bind() {}
        }

    }

    private val viewPool = RecyclerView.RecycledViewPool()

    override fun onBindViewHolder(holder : RecyclerView.ViewHolder, position : Int) {
        when (holder) {
            is ViewHolder.FooterViewHolder -> holder.bind()
            is ViewHolder.ItemViewHolder -> holder.bind(
                account = getItem(position),
                isSingleSection = getItems().size == 1,
                pool = viewPool,
                listener = listener
            )
        }
    }

    override fun getItemView(
        inflater : LayoutInflater,
        parent : ViewGroup
    ) : RecyclerView.ViewHolder =
        ViewHolder.ItemViewHolder(ItemSortableSocialBinding.inflate(inflater, parent, false))

    override fun getFooterView(
        inflater : LayoutInflater,
        parent : ViewGroup
    ) : RecyclerView.ViewHolder =
        ViewHolder.FooterViewHolder(WidgetNewAccountFooterBinding.inflate(inflater, parent, false))

}