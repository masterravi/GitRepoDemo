package  com.training.jetdemo.repo

import androidx.lifecycle.MutableLiveData
import com.training.jetdemo.base.BaseViewModel
import com.training.jetdemo.data.local.db.entity.RepoEntity
import com.training.jetdemo.data.repository.RepoRepository
import com.training.jetdemo.utils.common.Resource
import com.training.jetdemo.utils.network.NetworkHelper
import com.training.jetdemo.utils.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.processors.PublishProcessor
import io.reactivex.schedulers.Schedulers

class RepoViewModel(
    schedulerProvider: SchedulerProvider,
    compositeDisposable: CompositeDisposable,
    networkHelper: NetworkHelper,
    private val postRepository: RepoRepository,
    private val allRepoList: ArrayList<RepoEntity>,
    private val paginator: PublishProcessor<Int>
) : BaseViewModel(schedulerProvider, compositeDisposable, networkHelper) {

    val loading: MutableLiveData<Boolean> = MutableLiveData()
    private var  PAGE_NO:Int=1
    private val  LIMIT = 10
    val repos: MutableLiveData<Resource<List<RepoEntity>>> = MutableLiveData()

    init {

        compositeDisposable.add(
            paginator
                .onBackpressureDrop()
                .doOnNext {
                    loading.postValue(true)
                }
                .concatMapSingle { lastPageId ->
                    return@concatMapSingle postRepository
                        .getPostList(lastPageId, LIMIT)
                        .subscribeOn(Schedulers.io())
                        .doOnError {
                            handleNetworkError(it)
                        }
                }
                .subscribe(
                    {
                        if(networkHelper.isNetworkConnected()){
                            if(it.isNotEmpty() && it.size==10){
                                var repoList= ArrayList<RepoEntity>()
                                it?.forEach {
                                    repoList.add(
                                        RepoEntity(
                                            id = it?.id,
                                            name = it?.name,
                                            description = it?.description,
                                            open_issues_count = it?.open_issues_count,
                                            license = it?.license,
                                            permissions = it?.permissions,
                                            url = it?.url
                                        )
                                    )
                                }
                                allRepoList.addAll(repoList)

                                postRepository.saveRepoList(repoList)
                                loading.postValue(false)
                                repos.postValue(Resource.success(repoList))
                            }else{
                                loading.value=false
                            }
                        }else{
                            var repoList= ArrayList<RepoEntity>()
                            it?.forEach {
                                repoList.add(
                                    RepoEntity(
                                        id = it?.id,
                                        name = it?.name,
                                        description = it?.description,
                                        open_issues_count = it?.open_issues_count,
                                        license = it?.license,
                                        permissions = it?.permissions,
                                        url = it?.url
                                    )
                                )
                            }
                            allRepoList.addAll(repoList)
                            loading.postValue(false)
                            repos.postValue(Resource.success(repoList))
                            loading.value=false
                        }

                    },
                    {
                        handleNetworkError(it)
                    }
                )
        )
    }

    override fun onCreate() {
        loadMorePosts()
    }

    private fun loadMorePosts() {

        if (checkInternetConnectionWithMessage()) paginator.onNext(   if (allRepoList.size%10==0) PAGE_NO++ else 1)
    }

    fun onLoadMore() {
        if (loading.value !== null && loading.value == false) loadMorePosts()
    }
}
