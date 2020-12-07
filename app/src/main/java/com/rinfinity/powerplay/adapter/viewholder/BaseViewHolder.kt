package com.rinfinity.powerplay.adapter.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder(root: View): RecyclerView.ViewHolder(root) {

    abstract fun bindData(position: Int, item: Any)

}

