package com.xslite.sharemyaccountnative.ui.custom

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class FooterRecyclerView<T> : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    enum class FooterRecyclerViewType(val id:Int) {
        ITEM(0),
        FOOTER(1)
    }

    private val items = mutableListOf<T>()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data:List<T>){
        if (items.isNotEmpty()) clearData()
        items.addAll(data)
        notifyDataSetChanged()
    }

    protected abstract fun getItemView(
        inflater : LayoutInflater,
        parent : ViewGroup
    ) : RecyclerView.ViewHolder

    protected abstract fun getFooterView(
        inflater : LayoutInflater,
        parent : ViewGroup
    ) : RecyclerView.ViewHolder

    protected fun getItem(position: Int): T = items[position]

    private fun clearData() {
        items.clear()
    }

    protected fun getItems():List<T> = items

    override fun onCreateViewHolder(parent : ViewGroup, viewType : Int) : RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            FooterRecyclerViewType.FOOTER.id -> getFooterView(inflater, parent)
            FooterRecyclerViewType.ITEM.id -> getItemView(inflater, parent)
            else -> throw RuntimeException("Wrong view type - $viewType")
        }
    }

    override fun getItemCount() : Int = items.size + 1

    override fun getItemViewType(position : Int) : Int =
        if (position == itemCount - 1) FooterRecyclerViewType.FOOTER.id
        else FooterRecyclerViewType.ITEM.id
}