package com.training.jetdemo.data.remote

import android.os.AsyncTask
import androidx.annotation.MainThread
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.training.jetdemo.utils.common.Resource

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

abstract class NetworkBoundResource<ResultType, RequestType> @MainThread
internal constructor() {
    private val result = MediatorLiveData<Resource<ResultType>>()

    val asLiveData: LiveData<Resource<ResultType>>
        get() = result

    init {
        result.setValue(Resource.loading(null))
        val dbSource = loadFromDb()
        result.addSource(dbSource) { data ->
            result.removeSource(dbSource)
            if (shouldFetch(data)) {
                fetchFromNetwork(dbSource)
            } else {
                result.addSource(dbSource) { newData -> result.setValue(Resource.success(newData)) }
            }
        }
    }

    private fun fetchFromNetwork(dbSource: LiveData<ResultType>) {
        result.addSource(dbSource) { newData -> result.setValue(Resource.loading(newData)) }
        createCall().enqueue(object : Callback<RequestType> {
            override fun onResponse(call: Call<RequestType>, response: Response<RequestType>) {
                result.removeSource(dbSource)
                saveResultAndReInit(response.body())
            }

            override fun onFailure(call: Call<RequestType>, t: Throwable) {
                onFetchFailed()
                result.removeSource(dbSource)
                result.addSource(dbSource) { newData ->
                    result.setValue(
                        Resource.error(newData)
                    )
                }
            }
        })
    }

    @MainThread
    private fun saveResultAndReInit(response: RequestType?) {
        object : AsyncTask<Void, Void, Void>() {

            override fun doInBackground(vararg voids: Void): Void? {
                saveCallResult(response)
                return null
            }

            override fun onPostExecute(aVoid: Void) {
                result.addSource(loadFromDb()) { newData -> result.setValue(Resource.success(newData)) }
            }
        }.execute()
    }

    @WorkerThread
    protected abstract fun saveCallResult(@NonNull item: RequestType?)

    @MainThread
    protected fun shouldFetch(@Nullable data: ResultType): Boolean {
        return true
    }

    @NonNull
    @MainThread
    protected abstract fun loadFromDb(): LiveData<ResultType>

    @NonNull
    @MainThread
    protected abstract fun createCall(): Call<RequestType>

    @MainThread
    protected fun onFetchFailed() {
    }
}