package com.rinfinity.powerplay.view

import android.app.Activity
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.rinfinity.powerplay.R
import com.rinfinity.powerplay.application.PowerPlayApplication
import com.rinfinity.powerplay.databinding.ActivityDrawingProfileBinding
import com.rinfinity.powerplay.repo.DrawingProfileRepo
import com.rinfinity.powerplay.room.entity.DrawingItem
import com.rinfinity.powerplay.utils.IntentParmas
import com.rinfinity.powerplay.viewmodel.DrawingProfileViewModel
import com.squareup.picasso.Picasso

class DrawingProfileActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityDrawingProfileBinding
    private lateinit var mViewModel: DrawingProfileViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initUI()
        initObservers()
        setOnClickListeners()
        fetchDrawingData()
    }

    private fun initUI() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_drawing_profile)
        mViewModel = ViewModelProvider(
            this,
            DrawingProfileViewModel.DrawingProfileViewModelFactory(
                application,
                DrawingProfileRepo(PowerPlayApplication.Instance.drawingDatabase.getDrawingDAO())
            )
        ).get(DrawingProfileViewModel::class.java)
        mBinding.mViewModel = mViewModel
    }

    private fun setOnClickListeners() {
        mBinding.appBack.setOnClickListener {
            setResult(Activity.RESULT_CANCELED, null)
            finish()
        }
    }

    private fun initObservers() {
        mViewModel.drawingItem.observe(this, Observer {
            mViewModel.progressBarVisibility.set(View.GONE)
            Picasso.get().load(Uri.parse(it.imageUri)).into(mBinding.appZoomView)
            mViewModel.drawingItem.removeObservers(this)
        })
    }

    private fun fetchDrawingData() {
        intent?.apply {
            mViewModel.progressBarVisibility.set(View.VISIBLE)
            val imageName = getStringExtra(IntentParmas.PARAM_IMAGE_NAME) ?: ""
            val imageId = getLongExtra(IntentParmas.PARAM_IMAGE_ID, 0)
            val imageCreationTime = getStringExtra(IntentParmas.PARAM_IMAGE_CREATION_TIME) ?: ""
            val imageMarkerCount = getIntExtra(IntentParmas.PARAM_MARKER_COUNT, 0)
            val imagePathUri = getStringExtra(IntentParmas.PARAM_IMAGE_URI) ?: ""
            mViewModel.onDrawingDataReceive(
                item = DrawingItem(
                    id = imageId,
                    imageName = imageName,
                    imageCreationTime = imageCreationTime,
                    imageUri = imagePathUri,
                    imageMarkerCount = imageMarkerCount
                )
            )
        }
    }
}