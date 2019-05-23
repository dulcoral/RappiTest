package com.coral.movietest.data.repository.db

import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.coral.movietest.data.service.MovieApiResponse
import com.coral.movietest.models.Resource
import com.coral.movietest.util.AppExecutors

abstract class NetworkBoundResource<ResultType, RequestType>
@MainThread constructor(private val mExecutors: AppExecutors) {

    private val result = MediatorLiveData<Resource<ResultType>>()

    fun asLiveData() = result as LiveData<Resource<ResultType>>

    init {
        result.setValue(Resource.loading(null))
        val dbSource = loadFromDb()
        result.addSource(dbSource) { data ->
            result.removeSource(dbSource)
            if (shouldFetch(data)) {
                fetchFromNetwork(dbSource)
            } else {
                result.addSource(dbSource) { newData -> setValue(Resource.success(newData)) }
            }
        }
    }

    @MainThread
    private fun setValue(newValue: Resource<ResultType>) {
        if (result.value !== newValue) {
            result.value = newValue
        }
    }

    private fun fetchFromNetwork(dbSource: LiveData<ResultType>) {
        val mApiResponse = createCall()
        result.addSource(dbSource) { newData -> setValue(Resource.loading(newData)) }
        result.addSource(mApiResponse) { response ->
            result.removeSource(mApiResponse)
            result.removeSource(dbSource)

            if (response.isSuccessful()) {
                mExecutors.diskIO().execute(Runnable {
                    saveCallResult(response.getBody()!!)
                    mExecutors.mainThread().execute(Runnable {
                        result.addSource(
                            loadFromDb()
                        ) { newData -> setValue(Resource.success(newData)) }
                    })
                })
            } else {
                onFetchFailed()
                result.addSource(
                    dbSource
                ) { newData -> setValue(Resource.error(response.getError()?.message!!, newData)) }
            }
        }
    }

    @WorkerThread
    protected abstract fun saveCallResult(item: RequestType)

    @MainThread
    protected abstract fun shouldFetch(data: ResultType?): Boolean

    @MainThread
    protected abstract fun loadFromDb(): LiveData<ResultType>

    @MainThread
    protected abstract fun createCall(): LiveData<MovieApiResponse<RequestType>>

    @MainThread
    protected abstract fun onFetchFailed()
}