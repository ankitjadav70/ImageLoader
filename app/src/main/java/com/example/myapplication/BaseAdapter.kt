package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ListItemProgressBinding

open class BaseAdapter<T>(private val dataList: ArrayList<T?>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val LIST_ITEM_PROGRESS = 101
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ProgressViewHolder(ListItemProgressBinding.bind(parent.inflate(R.layout.list_item_progress)))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (dataList.getOrNull(position) == null) {
            LIST_ITEM_PROGRESS
        } else super.getItemViewType(position)
    }

    fun addAll(list: List<T?>) {
        dataList.addAll(list)
        notifyItemChanged(
            dataList.lastIndex - list.lastIndex,
            dataList.lastIndex
        )
    }

    fun addAllWithClear(list: List<T?>) {
        dataList.clear()
        dataList.addAll(list)
        notifyItemChanged(
            dataList.lastIndex - list.lastIndex,
            dataList.lastIndex
        )
    }

    fun clearAllItems() {
        dataList.clear()
        dataList.trimToSize()
        notifyAllChange()
    }

    fun notifyAllChange() {
        notifyDataSetChanged()
    }

    fun addLoadMore() {
        if (dataList[dataList.lastIndex] != null) {
            dataList.add(null)
            notifyItemInserted(dataList.lastIndex)
        }
    }

    fun removeLoadMore(isNotify: Boolean = false) {
        if (dataList.isNotEmpty() && dataList[dataList.lastIndex] == null) {
            if (isNotify)
                notifyItemRemoved(dataList.lastIndex)
            dataList.removeAt(dataList.lastIndex)
        }
    }

    fun isLoadMore() = dataList.getOrNull(dataList.lastIndex) == null

    fun getItemsList() = dataList

    fun getSingleItem(position: Int) = dataList[position]

    inner class ProgressViewHolder(_binding: ListItemProgressBinding) :
        RecyclerView.ViewHolder(_binding.root)

    fun ViewGroup?.inflate(
        @LayoutRes resource: Int,
        attachToRoot: Boolean = false
    ): View {
        return LayoutInflater.from(this?.context).inflate(resource, this, attachToRoot)
    }
}
