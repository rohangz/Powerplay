package com.rinfinity.powerplay.viewmodel

import android.app.Application
import android.view.View
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import androidx.lifecycle.*
import com.rinfinity.powerplay.application.PowerPlayApplication
import com.rinfinity.powerplay.room.entity.DrawingItem
import com.rinfinity.powerplay.repo.IMainActivityRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class MainActivityViewModel(app: Application, repo: IMainActivityRepo) : AndroidViewModel(app) {

    private val mAddedDrawingItem = MutableLiveData<DrawingItem>()
    val addedDrawingItem: LiveData<DrawingItem>
        get() = mAddedDrawingItem

    private val mInitialDrawingsList = MutableLiveData<ArrayList<DrawingItem>>()
    val initialDrawingList: LiveData<ArrayList<DrawingItem>>
        get() = mInitialDrawingsList

    private val mProgressVisibility = ObservableInt(View.VISIBLE)
    val progressVisibility: ObservableInt
        get() = mProgressVisibility


    fun fetchInitialDrawingList() {
        viewModelScope.launch {
            mProgressVisibility.set(View.VISIBLE)
            getApplication<PowerPlayApplication>().drawingDatabase.getDrawingDAO()
                .getAllDrawingItems().collect {
                    mInitialDrawingsList.value = ArrayList<DrawingItem>().apply {
                        addAll(it)
                    }
                    mProgressVisibility.set(View.GONE)
                }
        }
    }

    fun addDrawingItem(item: DrawingItem) {
        viewModelScope.launch(Dispatchers.Main) {
            mAddedDrawingItem.value = item
            withContext(Dispatchers.IO) {
                getApplication<PowerPlayApplication>().drawingDatabase.getDrawingDAO().addItem(item)
            }
        }
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