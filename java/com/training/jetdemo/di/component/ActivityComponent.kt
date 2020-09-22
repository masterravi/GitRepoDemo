package com.training.jetdemo.di.component

import com.training.jetdemo.main.MainActivity
import com.training.jetdemo.di.ActivityScope
import com.training.jetdemo.di.module.ActivityModule
import dagger.Component

@ActivityScope
@Component(dependencies = [ApplicationComponent::class], modules = [ActivityModule::class])
interface ActivityComponent {

    fun inject(activity: MainActivity)
}
