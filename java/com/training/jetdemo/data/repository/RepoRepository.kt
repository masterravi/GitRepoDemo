package com.training.jetdemo.data.repository

import com.training.jetdemo.data.local.db.DatabaseService
import com.training.jetdemo.data.local.db.entity.RepoEntity
import com.training.jetdemo.data.remote.NetworkService
import com.training.jetdemo.utils.network.NetworkHelper
import io.reactivex.Single
import javax.inject.Inject


class RepoRepository @Inject constructor(
    private val networkService: NetworkService,
    private val databaseService: DatabaseService,
    private val networkHelper: NetworkHelper
) {

    fun getPostList(page: Int,limit:Int): Single<List<RepoEntity>>{
        if(networkHelper.isNetworkConnected()){
            return networkService.getPostList(page,limit).map { it }
        }else{
            return  databaseService.repoDao().getAll()
        }
    }


    fun saveRepoList(repoList:List<RepoEntity>){
        databaseService.repoDao().insertAll(repoList)
    }


}