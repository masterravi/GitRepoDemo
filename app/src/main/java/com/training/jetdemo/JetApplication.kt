package com.training.jetdemo

import android.app.Application
import com.training.jetdemo.data.local.db.DatabaseService
import com.training.jetdemo.data.remote.NetworkService
import com.training.jetdemo.di.component.ApplicationComponent
import com.training.jetdemo.di.component.DaggerApplicationComponent
import com.training.jetdemo.di.module.ApplicationModule
import javax.inject.Inject

class JetApplication: Application() {
    lateinit var applicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        injectDependencies()
    }

    private fun injectDependencies() {
        applicationComponent = DaggerApplicationComponent
            .builder()
            .applicationModule(ApplicationModule(this))
            .build()
        applicationComponent.inject(this)
    }

    // Needed to replace the component with a test specific one
    fun setComponent(applicationComponent: ApplicationComponent) {
        this.applicationComponent = applicationComponent
    }

}