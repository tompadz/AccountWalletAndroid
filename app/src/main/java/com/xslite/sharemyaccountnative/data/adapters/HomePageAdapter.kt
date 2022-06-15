package com.xslite.sharemyaccountnative.data.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.xslite.sharemyaccountnative.data.models.AccountSectionModel
import com.xslite.sharemyaccountnative.databinding.ItemSortableSocialBinding
import com.xslite.sharemyaccountnative.databinding.WidgetSocialFooterBinding
import com.xslite.sharemyaccountnative.ui.custom.FooterRecyclerView
import com.xslite.sharemyaccountnative.util.listeners.OnAccountClickListener
import com.xslite.sharemyaccountnative.util.listeners.OnHomeFooterClickListener



/**
 * RecyclerView Adapter for the main page of the application, accepts a list of accounts divided by section,
 * Custom view FooterRecyclerView is used to display the footer.
 *
 * @param listener - Click listener for accounts;
 * @param footerListener - Click listener for recycler footer;
 * @see listener
 * @see footerListener
 */
class HomePageAdapter(
    private val listener : OnAccountClickListener,
    private val footerListener : OnHomeFooterClickListener
) : FooterRecyclerView<AccountSectionModel>() {


    sealed class ViewHolder(binding : ViewBinding) : RecyclerView.ViewHolder(binding.root) {

        class ItemViewHolder(private val binding : ItemSortableSocialBinding) :
            ViewHolder(binding) {
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
                    val adapter = AccountCreatedAdapter(listener)
                    recyclerView.setHasFixedSize(true)
                    recyclerView.setRecycledViewPool(pool)
                    recyclerView.adapter = adapter
                    adapter.setData(account.items)

                }
            }
        }

        class FooterViewHolder(private val binding : WidgetSocialFooterBinding) :
            ViewHolder(binding) {
            fun bind(listener : OnHomeFooterClickListener) {
                binding.apply {
                    button.setOnClickListener {
                        listener.onCreateAccountClick()
                    }
                }
            }
        }

    }

    private val viewPool = RecyclerView.RecycledViewPool()

    override fun onBindViewHolder(holder : RecyclerView.ViewHolder, position : Int) {
        when (holder) {
            is ViewHolder.FooterViewHolder -> holder.bind(footerListener)
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
        ViewHolder.FooterViewHolder(WidgetSocialFooterBinding.inflate(inflater, parent, false))

}