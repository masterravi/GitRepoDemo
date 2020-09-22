package com.training.jetdemo.di.component

import com.training.jetdemo.di.FragmentScope
import com.training.jetdemo.di.module.FragmentModule
import com.training.jetdemo.repo.RepoFragment
import dagger.Component


@FragmentScope
@Component(dependencies = [ApplicationComponent::class], modules = [FragmentModule::class])
interface FragmentComponent{
    fun inject(fragment: RepoFragment)
}