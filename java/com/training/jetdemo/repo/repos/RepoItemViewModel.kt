package com.training.jetdemo.repo.repos

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.training.jetdemo.base.BaseItemViewModel
import com.training.jetdemo.data.local.db.entity.RepoEntity
import com.training.jetdemo.data.model.License
import com.training.jetdemo.data.model.Permissions
import com.training.jetdemo.data.repository.RepoRepository
import com.training.jetdemo.utils.log.Logger
import com.training.jetdemo.utils.network.NetworkHelper
import com.training.jetdemo.utils.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class RepoItemViewModel @Inject constructor(
    schedulerProvider: SchedulerProvider,
    compositeDisposable: CompositeDisposable,
    networkHelper: NetworkHelper,
    private val postRepository: RepoRepository
) : BaseItemViewModel<RepoEntity>(schedulerProvider, compositeDisposable, networkHelper) {

    companion object {
        const val TAG = "PostItemViewModel"
    }

    val name: LiveData<String> = Transformations.map(data) { it.name }
    val userDescription: LiveData<String> = Transformations.map(data) { it.description }
    val permissions: LiveData<Permissions> = Transformations.map(data) { it.permissions }
    val license: LiveData<License> = Transformations.map(data) { it.license }
    val url: LiveData<String> = Transformations.map(data) { it.url }
    val issueCount: LiveData<Int> = Transformations.map(data) { it.open_issues_count?: 0 }

    override fun onCreate() {
        Logger.d(TAG, "onCreate called")
    }


}