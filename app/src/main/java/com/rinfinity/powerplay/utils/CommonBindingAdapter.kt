package com.rinfinity.powerplay.utils

import android.net.Uri
import android.util.Size
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso


class CommonBindingAdapter {

    companion object {
        @JvmStatic
        @BindingAdapter( "loadUri" )
        fun loadUrl(view: ImageView, url: Uri) {
            Picasso.get().load(url).into(view)
        }


        @JvmStatic
        @BindingAdapter("loadThumbnail")
        fun loadThumbnail(view: ImageView, uri: Uri) {
            val bitmap = view.context.contentResolver.loadThumbnail(uri, Size(16, 16),null)
            view.setImageBitmap(bitmap)
        }
    }


}