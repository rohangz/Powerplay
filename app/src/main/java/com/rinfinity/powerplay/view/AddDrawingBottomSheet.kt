package com.rinfinity.powerplay.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.rinfinity.powerplay.databinding.AppBottomsheetAddDrawingBinding

class AddDrawingBottomSheet : BottomSheetDialogFragment() {

    private lateinit var mBinding: AppBottomsheetAddDrawingBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = AppBottomsheetAddDrawingBinding.inflate(inflater, container, false)
        return mBinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val activity = requireActivity()
        if (activity is IAddDrawingBottomSheetClickListener) {
            mBinding.appCamera.setOnClickListener {
                activity.onCameraClick()
                dismiss()
            }
            mBinding.appGallery.setOnClickListener {
                activity.onGalleryClick()
                dismiss()
            }
        }
    }

    interface IAddDrawingBottomSheetClickListener {

        fun onCameraClick()
        fun onGalleryClick()
    }
}