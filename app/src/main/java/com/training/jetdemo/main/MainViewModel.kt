package com.training.jetdemo.main

import androidx.lifecycle.MutableLiveData
import com.training.jetdemo.base.BaseViewModel
import com.training.jetdemo.data.local.db.DatabaseService
import com.training.jetdemo.data.remote.NetworkService
import com.training.jetdemo.utils.common.Event
import com.training.jetdemo.utils.network.NetworkHelper
import com.training.jetdemo.utils.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable

class MainViewModel(
    schedulerProvider: SchedulerProvider,
    compositeDisposable: CompositeDisposable,
    networkHelper: NetworkHelper
) : BaseViewModel(schedulerProvider, compositeDisposable, networkHelper) {

    val profileNavigation = MutableLiveData<Event<Boolean>>()
    val homeNavigation = MutableLiveData<Event<Boolean>>()
    val photoNavigation = MutableLiveData<Event<Boolean>>()

    override fun onCreate() {
        homeNavigation.postValue(Event(true))
    }
}