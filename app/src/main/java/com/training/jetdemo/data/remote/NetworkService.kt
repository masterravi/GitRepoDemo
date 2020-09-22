package com.training.jetdemo.data.remote

import com.training.jetdemo.data.local.db.entity.RepoEntity
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface NetworkService {
    @GET(Endpoints.REPO)
    fun getPostList(
        @Query("page") page: Int,
        @Query("per_page") limit: Int
    ): Single<List<RepoEntity>>
}