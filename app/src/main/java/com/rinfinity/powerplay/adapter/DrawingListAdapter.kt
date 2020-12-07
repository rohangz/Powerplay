package com.rinfinity.powerplay.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rinfinity.powerplay.adapter.viewholder.BaseViewHolder
import com.rinfinity.powerplay.model.DrawingListItemModel

class DrawingListAdapter(private val list: List<DrawingListItemModel>): RecyclerView.Adapter<BaseViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.bindData(position, list[position])
    }

    inner class DrawingListItemViewHolder(root: View): BaseViewHolder(root) {
        override fun bindData(position: Int, item: Any) {

        }
    }

}