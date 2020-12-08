package com.rinfinity.powerplay.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rinfinity.powerplay.adapter.viewholder.BaseViewHolder
import com.rinfinity.powerplay.databinding.AppDrawingItemBinding
import com.rinfinity.powerplay.model.DrawingListItemModel

class DrawingListAdapter(
    private val list: ArrayList<DrawingListItemModel>,
    private val mContext: Context
) :
    RecyclerView.Adapter<BaseViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val binding = AppDrawingItemBinding.inflate(LayoutInflater.from(mContext), parent, false)
        return DrawingListItemViewHolder(binding)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.bindData(position, list[position])
    }

    fun addItemToList(item: DrawingListItemModel) {
        list.add(0, item)
        notifyItemInserted(0)
    }

    fun addList(newList: ArrayList<DrawingListItemModel>) {
        val position = list.size - 1
        list.addAll(newList)
        notifyItemInserted(position)
    }

    inner class DrawingListItemViewHolder(private val mBinding: AppDrawingItemBinding) :
        BaseViewHolder(mBinding.root) {
        override fun bindData(position: Int, item: Any) {
            (item as? DrawingListItemModel)?.let {
                mBinding.mDataModel = it
            }
        }
    }

}