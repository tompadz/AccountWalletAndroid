package com.xslite.sharemyaccountnative.data.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.xslite.sharemyaccountnative.data.models.AccountModel
import com.xslite.sharemyaccountnative.databinding.ItemSocialUserBinding
import com.xslite.sharemyaccountnative.util.listeners.OnAccountClickListener

/**
 * Adapter of created accounts for the main page of the application,
 * used in HomePageAdapter
 * @param listener - Click listener for accounts
 * @see HomePageAdapter
 * @see listener
 */
class AccountCreatedAdapter(private val listener:OnAccountClickListener) : RecyclerView.Adapter<AccountCreatedAdapter.ViewHolder>() {

    private val items = mutableListOf<AccountModel>()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data : List<AccountModel>) {
        items.clear()
        items.addAll(data)
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding : ItemSocialUserBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent : ViewGroup, viewType : Int) : ViewHolder {
        val binding =
            ItemSocialUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder : ViewHolder, position : Int) {
        val account = items[position]
        with(holder) {
            binding.apply {
                textAccountId.text = account.accountId
                textSocialTitle.text = account.title
                imageSocial.setBackgroundResource(account.image)
                root.setOnClickListener {
                    listener.onAccountClick(account)
                }
                root.setOnLongClickListener {
                    listener.onAccountLongClick(account)
                    true
                }
            }
        }
    }

    override fun getItemCount() : Int = items.size
}