package com.rinfinity.powerplay.view

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.rinfinity.powerplay.R
import com.rinfinity.powerplay.databinding.ActivitySaveDrawingBinding
import com.rinfinity.powerplay.utils.IntentParmas
import com.rinfinity.powerplay.viewmodel.SaveDrawingViewModel

class SaveDrawingActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivitySaveDrawingBinding
    private lateinit var mViewModel: SaveDrawingViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initUI()
        getDataFromIntent()
        setOnClickListeners()
    }

    private fun getDataFromIntent() {
        mViewModel.uri = intent?.data!!
    }
    private fun initUI() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_save_drawing)
        mViewModel =
            ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(application)).get(
                SaveDrawingViewModel::class.java
            )
        mBinding.mViewModel = mViewModel
    }

    private fun setOnClickListeners() {
        mBinding.appButtonSave.setOnClickListener {
            if (mViewModel.isFileNameValid) {
                Intent().apply {
                    putExtra(IntentParmas.PARAM_IMAGE_NAME, mViewModel.fileName)
                    putExtra(IntentParmas.PARAM_IMAGE_ID, System.currentTimeMillis())
                    putExtra(IntentParmas.PARAM_IMAGE_CREATION_TIME, mViewModel.getDrawingCreationTime())
                    data = mViewModel.uri
                    setResult(Activity.RESULT_OK, this)
                    finish()
                }
            } else
                Toast.makeText(
                    this,
                    getString(R.string.app_enter_valid_file_name),
                    Toast.LENGTH_LONG
                ).show()
        }
        mBinding.appBack.setOnClickListener {
            setResult(Activity.RESULT_OK)
            finish()
        }
    }
}