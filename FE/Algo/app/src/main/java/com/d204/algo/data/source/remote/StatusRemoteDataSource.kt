package com.d204.algo.data.source.remote

import com.d204.algo.data.model.Status
import com.d204.algo.data.repository.datasource.StatusDataSource
import com.d204.algo.data.repository.remote.StatusRemote
import javax.inject.Inject

class StatusRemoteDataSource @Inject constructor(
    private val StatusRemote: StatusRemote,
) : StatusDataSource {
    override suspend fun getStatus(userId: Int): Status {
        return StatusRemote.getStatus(userId)
    }

    override suspend fun isRemote(): Boolean {
        return StatusRemote.isRemote()
    }
}
