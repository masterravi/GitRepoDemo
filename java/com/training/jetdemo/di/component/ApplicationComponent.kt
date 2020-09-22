package com.training.jetdemo.di.component

import android.app.Application
import android.content.Context
import com.training.jetdemo.JetApplication
import com.training.jetdemo.data.local.db.DatabaseService
import com.training.jetdemo.data.remote.NetworkService
import com.training.jetdemo.data.repository.RepoRepository
import com.training.jetdemo.di.ApplicationContext
import com.training.jetdemo.di.module.ApplicationModule
import com.training.jetdemo.utils.network.NetworkHelper
import com.training.jetdemo.utils.rx.SchedulerProvider
import dagger.Component
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class])

interface ApplicationComponent{
    fun inject(app: JetApplication)

    fun getApplication(): Application

    @ApplicationContext
    fun getContext(): Context

    fun getNetworkService(): NetworkService

    fun getDatabaseService(): DatabaseService

    fun getNetworkHelper(): NetworkHelper

    fun getPostRepository(): RepoRepository

    fun getSchedulerProvider(): SchedulerProvider

    fun getCompositeDisposable(): CompositeDisposable


}