package com.rinfinity.powerplay.view

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.rinfinity.powerplay.R
import com.rinfinity.powerplay.databinding.ActivityMainBinding
import com.rinfinity.powerplay.repo.DrawingRepo
import com.rinfinity.powerplay.utils.*
import com.rinfinity.powerplay.viewmodel.MainActivityViewModel
import com.squareup.picasso.Picasso


private const val CONST_RQST_CAMERA_PERMISSIONS = 0
private const val CONST_CAMERA_PICK_IMAGE = 1
private const val CONST_GALLERY_PICK_IMAGE = 2

class MainActivity : AppCompatActivity(),
    AddDrawingBottomSheet.IAddDrawingBottomSheetClickListener {
    private val mAddDrawingBottomSheetTag = "AddDrawingBottomSheet"
    private lateinit var mViewModel: MainActivityViewModel
    private lateinit var mBinding: ActivityMainBinding
    private var mSelectedImageURI: Uri? = null


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            CONST_CAMERA_PICK_IMAGE -> onCameraResult(resultCode, data)
            CONST_GALLERY_PICK_IMAGE -> onGalleryResult(resultCode, data)
        }
    }

    private fun onCameraResult(resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK && mSelectedImageURI!=null) {
            Picasso.get().load(mSelectedImageURI!!).fit().into(mBinding.appTestImage)
        }
    }

    private fun onGalleryResult(resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK && data != null && data.data!=null) {
            Picasso.get().load(data.data!!).fit().into(mBinding.appTestImage)
        }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            CONST_RQST_CAMERA_PERMISSIONS -> onRequestCameraPermissions(permissions, grantResults)
        }
    }

    private fun onRequestCameraPermissions(
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        for (i in grantResults) {
            if (i != PackageManager.PERMISSION_GRANTED) {
                AlertDialog.Builder(this)
                    .setTitle(getString(R.string.app_all_permissions_are_necessary))
                    .setMessage(getString(R.string.app_one_or_more_permissions_denied))
                    .setPositiveButton("OK") { _, _ ->
                        moveToAppSettings(this)
                    }
                    .create()
                    .show()
                return
            }
        }
        onCameraClick()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        mViewModel = ViewModelProvider(
            this,
            MainActivityViewModel.MainActivityViewModelFactory(application, DrawingRepo())
        ).get(MainActivityViewModel::class.java)
        setOnClickListeners()
        initObservers()
    }


    @SuppressLint("SimpleDateFormat")
    private fun setOnClickListeners() {
        mBinding.appFab.setOnClickListener {
            AddDrawingBottomSheet().show(supportFragmentManager, mAddDrawingBottomSheetTag)
        }
    }

    private fun initObservers() {
        mViewModel.drawingModelList.observe(this, Observer {

        })
    }

    override fun onCameraClick() {
        if (isCameraPermissionAllowed(this)) {
            startCamera()
        } else {
            ActivityCompat.requestPermissions(
                this,
                CAMERA_PERMISSION_ARRAY,
                CONST_RQST_CAMERA_PERMISSIONS
            )
        }
    }

    private fun startCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        val photoFile = createUniqueFile(this, Extensions.PNG)
        photoFile?.let {
            mSelectedImageURI =
                FileProvider.getUriForFile(this, "com.rinfinity.powerplay.provider", photoFile)
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
            intent.putExtra(MediaStore.EXTRA_OUTPUT, mSelectedImageURI)
            startActivityForResult(intent, CONST_CAMERA_PICK_IMAGE)
        }

    }

    override fun onGalleryClick() {
        val contentSelectionIntent = Intent(Intent.ACTION_GET_CONTENT)
        contentSelectionIntent.type = "image/*"
        startActivityForResult(contentSelectionIntent, CONST_GALLERY_PICK_IMAGE)
    }
}