package com.rinfinity.powerplay.view

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
import androidx.recyclerview.widget.LinearLayoutManager
import com.rinfinity.powerplay.R
import com.rinfinity.powerplay.adapter.DrawingListAdapter
import com.rinfinity.powerplay.databinding.ActivityMainBinding
import com.rinfinity.powerplay.room.entity.DrawingItem
import com.rinfinity.powerplay.repo.DrawingRepo
import com.rinfinity.powerplay.utils.*
import com.rinfinity.powerplay.viewmodel.MainActivityViewModel


private const val CONST_RQST_CAMERA_PERMISSIONS = 0
private const val CONST_CAMERA_PICK_IMAGE = 1
private const val CONST_GALLERY_PICK_IMAGE = 2
private const val CONST_SAVE_DRAWING_ACTIVITY = 3

class MainActivity : AppCompatActivity(),
    AddDrawingBottomSheet.IAddDrawingBottomSheetClickListener {
    private val mAddDrawingBottomSheetTag = "AddDrawingBottomSheet"
    private lateinit var mViewModel: MainActivityViewModel
    private lateinit var mBinding: ActivityMainBinding
    private lateinit var mAdapter: DrawingListAdapter
    private var mSelectedImageURI: Uri? = null


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            CONST_CAMERA_PICK_IMAGE -> onCameraResult(resultCode, data)
            CONST_GALLERY_PICK_IMAGE -> onGalleryResult(resultCode, data)
            CONST_SAVE_DRAWING_ACTIVITY -> onSaveDrawingResult(resultCode, data)
        }
    }

    private fun onCameraResult(resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK && mSelectedImageURI != null) {
            startSaveDrawing()
        }
    }

    private fun onGalleryResult(resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            mSelectedImageURI = data.data
            startSaveDrawing()
        }
    }

    private fun onSaveDrawingResult(resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            val resultUri = data.data!!
            val imageCreationTime =
                data.getStringExtra(IntentParmas.PARAM_IMAGE_CREATION_TIME) ?: ""
            val imageId = data.getLongExtra(IntentParmas.PARAM_IMAGE_ID, 0L)
            val imageName = data.getStringExtra(IntentParmas.PARAM_IMAGE_NAME) ?: ""
            mViewModel.addDrawingItem(
                DrawingItem(
                    id = imageId,
                    imageUri = resultUri.toString(),
                    imageName = imageName,
                    imageCreationTime = imageCreationTime,
                    imageMarkerCount = 0
                )
            )
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
        initUI()
        setOnClickListeners()
        initObservers()
        initAdapter(ArrayList())
        fetchInitialList()
    }

    private fun fetchInitialList() {
        mViewModel.fetchInitialDrawingList()
    }

    private fun initUI() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        mViewModel = ViewModelProvider(
            this,
            MainActivityViewModel.MainActivityViewModelFactory(application, DrawingRepo())
        ).get(MainActivityViewModel::class.java)
        mBinding.mViewModel = mViewModel
    }

    private fun setOnClickListeners() {
        mBinding.appFab.setOnClickListener {
            AddDrawingBottomSheet().show(supportFragmentManager, mAddDrawingBottomSheetTag)
        }
    }

    private fun initObservers() {
        mViewModel.addedDrawingItem.observe(this, Observer {
            mAdapter.addItemToList(it)
        })
        mViewModel.initialDrawingList.observe(this, Observer {
            mAdapter.replaceList(it)
            mViewModel.initialDrawingList.removeObservers(this)
        })
    }

    private fun startSaveDrawing() {
        Intent(this, SaveDrawingActivity::class.java).apply {
            data = mSelectedImageURI
            startActivityForResult(this, CONST_SAVE_DRAWING_ACTIVITY)
        }

    }

    private fun initAdapter(arrayList: ArrayList<DrawingItem>) {
        mAdapter = DrawingListAdapter(arrayList, this)
        mBinding.appList.layoutManager = LinearLayoutManager(this)
        mBinding.appList.adapter = mAdapter
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
                FileProvider.getUriForFile(this, CONST_APP_PROVIDER_AUTHORITY, photoFile)
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
            intent.putExtra(MediaStore.EXTRA_OUTPUT, mSelectedImageURI)
            startActivityForResult(intent, CONST_CAMERA_PICK_IMAGE)
        }

    }

    override fun onGalleryClick() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.type = "image/*"
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
        startActivityForResult(intent, CONST_GALLERY_PICK_IMAGE)
    }
}