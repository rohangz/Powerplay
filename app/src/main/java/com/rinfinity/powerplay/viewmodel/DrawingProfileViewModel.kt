package com.rinfinity.powerplay.viewmodel

import android.app.Application
import android.view.View
import androidx.databinding.ObservableInt
import androidx.lifecycle.*
import com.rinfinity.powerplay.repo.IDrawingProfileRepo
import com.rinfinity.powerplay.room.entity.DrawingItem
import java.lang.Exception

class DrawingProfileViewModel(app: Application, repo: IDrawingProfileRepo) : AndroidViewModel(app) {

    private val mDrawingItem = MutableLiveData<DrawingItem>()
    val drawingItem: LiveData<DrawingItem>
        get() = mDrawingItem
    val progressBarVisibility = ObservableInt(View.VISIBLE)


    fun onDrawingDataReceive(item: DrawingItem) {
        mDrawingItem.value = item
    }


    class DrawingProfileViewModelFactory(
        private val app: Application,
        private val repo: IDrawingProfileRepo
    ) : ViewModelProvider.AndroidViewModelFactory(app) {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (AndroidViewModel::class.java.isAssignableFrom(modelClass)) {
                when (modelClass) {
                    DrawingProfileViewModel::class.java -> return DrawingProfileViewModel(
                        app,
                        repo
                    ) as T
                    else -> throw Exception("Unable to Create Instance of view Model ${modelClass.simpleName}")
                }
            }
            return super.create(modelClass)
        }
    }

}