package com.training.jetdemo.di.module

import androidx.lifecycle.LifecycleRegistry
import com.training.jetdemo.base.BaseItemViewHolder
import com.training.jetdemo.di.ViewHolderScope
import dagger.Module
import dagger.Provides

@Module
class ViewHolderModule(private val viewHolder: BaseItemViewHolder<*, *>) {

    @Provides
    @ViewHolderScope
    fun provideLifecycleRegistry(): LifecycleRegistry = LifecycleRegistry(viewHolder)
}