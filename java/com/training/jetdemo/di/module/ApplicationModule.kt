package com.training.jetdemo.di.module

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.training.jetdemo.BuildConfig
import com.training.jetdemo.JetApplication
import com.training.jetdemo.data.local.db.DatabaseService
import com.training.jetdemo.data.remote.NetworkService
import com.training.jetdemo.data.remote.Networking
import com.training.jetdemo.di.ApplicationContext
import com.training.jetdemo.di.DatabaseInfo
import com.training.jetdemo.di.NetworkInfo
import com.training.jetdemo.utils.network.NetworkHelper
import com.training.jetdemo.utils.rx.RxSchedulerProvider
import com.training.jetdemo.utils.rx.SchedulerProvider
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Singleton

@Module
class ApplicationModule(private val application: JetApplication) {

    @Provides
    @Singleton
    fun provideApplication(): Application = application

    @Provides
    @Singleton
    @ApplicationContext
    fun provideContext(): Context = application

    /**
     * Since this function do not have @Singleton then each time CompositeDisposable is injected
     * then a new instance of CompositeDisposable will be provided
     */
    @Provides
    fun provideCompositeDisposable(): CompositeDisposable = CompositeDisposable()

    @Provides
    fun provideSchedulerProvider(): SchedulerProvider = RxSchedulerProvider()

    /**
     * We need to write @Singleton on the provide method if we are create the instance inside this method
     * to make it singleton. Even if we have written @Singleton on the instance's class
     */
    @Provides
    @Singleton
    fun provideDatabaseService(): DatabaseService =
        Room.databaseBuilder(
            application, DatabaseService::class.java,
            "jet-app-db"
        ).build()

    @Provides
    @Singleton
    fun provideNetworkService(): NetworkService =
        Networking.create(
            BuildConfig.BASE_URL,
            application.cacheDir,
            10 * 1024 * 1024 // 10MB
        )

    @Singleton
    @Provides
    fun provideNetworkHelper(): NetworkHelper = NetworkHelper(application)

}