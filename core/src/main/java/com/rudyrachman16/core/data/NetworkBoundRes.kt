package com.rudyrachman16.core.data

import com.rudyrachman16.core.data.api.retrofit.ApiStatus
import kotlinx.coroutines.flow.*

abstract class NetworkBoundRes<ReqType, ResType> {
    private var result: Flow<Status<ResType>> = flow {
        val db = loadDB().firstOrNull()
        if (shouldFetch(db)) {
            emit(Status.Loading())
            when (val response = apiCall().first()) {
                is ApiStatus.Success -> {
                    saveCallResult(response.data, db)
                    emitAll(loadDB().map { Status.Success(it) })
                }
                is ApiStatus.Empty -> emitAll(loadDB().map { Status.Success(it) })
                is ApiStatus.Failed -> {
                    emitAll(loadDB().map { Status.Error(it, response.error) })
                }
            }
        } else emitAll(loadDB().map { Status.Success(it) })
    }

    protected abstract fun loadDB(): Flow<ResType>
    protected abstract fun shouldFetch(db: ResType?): Boolean
    protected abstract suspend fun apiCall(): Flow<ApiStatus<ReqType>>
    protected abstract suspend fun saveCallResult(data: ReqType, result: ResType?)
    fun asFlow() = result
}