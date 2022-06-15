package com.xslite.sharemyaccountnative.data.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.xslite.sharemyaccountnative.data.models.AccountModel
import com.xslite.sharemyaccountnative.databinding.ItemSocialBinding
import com.xslite.sharemyaccountnative.util.AccountType
import com.xslite.sharemyaccountnative.util.listeners.OnAccountClickListener


/**
 * Account adapter for the page with the creation of new accounts.
 * @see listener
 */
class AccountAdapter(private val listener:OnAccountClickListener) : RecyclerView.Adapter<AccountAdapter.ViewHolder>() {

    private val items = mutableListOf<AccountModel>()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data : List<AccountModel>) {
        items.addAll(data)
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding : ItemSocialBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent : ViewGroup, viewType : Int) : ViewHolder {
        val binding =
            ItemSocialBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder : ViewHolder, position : Int) {
        val account = items[position]
        with(holder) {
            binding.apply {

                if (account.type == AccountType.SOCIAL)
                    textInfo.visibility = View.GONE
                else
                    textInfo.visibility = View.VISIBLE

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