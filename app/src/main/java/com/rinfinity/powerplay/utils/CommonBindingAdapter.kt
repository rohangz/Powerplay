package com.rinfinity.powerplay.utils

import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.util.Size
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso


class CommonBindingAdapter {

    companion object {
        @JvmStatic
        @BindingAdapter("loadUri")
        fun loadUrl(view: ImageView, url: Uri) {
            Picasso.get().load(url).into(view)
        }


        @JvmStatic
        @BindingAdapter("loadThumbnail")
        fun loadThumbnail(view: ImageView, uriStr: String) {
            val bitmap =
                view.context.contentResolver.loadThumbnail(Uri.parse(uriStr)!!, Size(16, 16), null)
//            Picasso.get().load(bitmap).into(view)
            view.setImageBitmap(bitmap)
        }
    }


}