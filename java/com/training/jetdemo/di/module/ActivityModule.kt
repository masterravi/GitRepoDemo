package com.training.jetdemo.di.module

import android.content.Context
import androidx.lifecycle.ViewModelProviders
import com.training.jetdemo.base.BaseActivity
import com.training.jetdemo.data.local.db.DatabaseService
import com.training.jetdemo.data.remote.NetworkService
import com.training.jetdemo.di.ActivityContext
import com.training.jetdemo.main.MainViewModel
import com.training.jetdemo.utils.ViewModelProviderFactory
import com.training.jetdemo.utils.network.NetworkHelper
import com.training.jetdemo.utils.rx.SchedulerProvider
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable

@Module
class ActivityModule(private val activity: BaseActivity<*>) {

    @ActivityContext
    @Provides
    fun provideContext(): Context = activity

    @Provides
    fun provideMainViewModel(
        schedulerProvider: SchedulerProvider,
        compositeDisposable: CompositeDisposable,
        networkHelper: NetworkHelper
    ): MainViewModel = ViewModelProviders.of(
        activity, ViewModelProviderFactory(MainViewModel::class) {
            MainViewModel(schedulerProvider,compositeDisposable, networkHelper)
        }).get(MainViewModel::class.java)
}
