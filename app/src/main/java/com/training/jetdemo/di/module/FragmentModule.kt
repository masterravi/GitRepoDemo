package com.training.jetdemo.di.module

import android.content.Context
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.training.jetdemo.base.BaseFragment
import com.training.jetdemo.data.repository.RepoRepository
import com.training.jetdemo.di.ActivityContext
import com.training.jetdemo.di.FragmentScope
import com.training.jetdemo.repo.repos.RepoAdapter
import com.training.jetdemo.repo.RepoViewModel
import com.training.jetdemo.utils.ViewModelProviderFactory
import com.training.jetdemo.utils.network.NetworkHelper
import com.training.jetdemo.utils.rx.SchedulerProvider
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.processors.PublishProcessor

@Module
class FragmentModule(private val fragment: BaseFragment<*>) {

    @ActivityContext
    @Provides
    fun provideContext(): Context = fragment.context!!


    @Provides
    fun providePostViewModel(
        schedulerProvider: SchedulerProvider,
        compositeDisposable: CompositeDisposable,
        networkHelper: NetworkHelper,
        postRepository: RepoRepository
    ): RepoViewModel = ViewModelProviders.of(
        fragment, ViewModelProviderFactory(RepoViewModel::class) {
            RepoViewModel(
                schedulerProvider, compositeDisposable, networkHelper,
                postRepository, ArrayList(), PublishProcessor.create()
            )
        }).get(RepoViewModel::class.java)

    @Provides
    @FragmentScope
    fun providePostAdapter() = RepoAdapter(fragment.lifecycle, ArrayList())

    @Provides
    fun provideLinearLayoutManager() = LinearLayoutManager(fragment.context)
}