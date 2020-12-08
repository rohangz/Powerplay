package com.rinfinity.powerplay.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.rinfinity.powerplay.model.DrawingListItemModel
import com.rinfinity.powerplay.repo.IMainActivityRepo
import java.lang.Exception

class MainActivityViewModel(app: Application, repo: IMainActivityRepo) : AndroidViewModel(app) {

    private val mAddedDrawingItem = MutableLiveData<DrawingListItemModel>()
    val addedDrawingItem: LiveData<DrawingListItemModel>
        get() = mAddedDrawingItem


    fun addDrawingItem(item: DrawingListItemModel) {
        mAddedDrawingItem.value = item
    }


    class MainActivityViewModelFactory(
        private val app: Application,
        private val repo: IMainActivityRepo
    ) :
        ViewModelProvider.AndroidViewModelFactory(app) {


        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (AndroidViewModel::class.java.isAssignableFrom(modelClass)) {
                return when (modelClass) {
                     MainActivityViewModel::class.java -> MainActivityViewModel(
                        app,
                        repo
                    ) as T
                    else -> throw Exception("Unable to Instantiate View model ${modelClass.simpleName}")
                }
            }
            return super.create(modelClass)
        }
    }
}