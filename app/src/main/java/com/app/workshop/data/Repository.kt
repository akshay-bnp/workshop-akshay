package com.app.workshop.data

import android.content.Context
import com.app.workshop.data.remote.RemoteDataSource
import com.app.workshop.data.remote.toResultFlow
import com.app.workshop.utils.NetWorkResult
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ActivityRetainedScoped
class Repository @Inject constructor(private val remoteDataSource: RemoteDataSource) {

    suspend fun getPostList(context: Context): Flow<NetWorkResult<List<Post>>> {
        return toResultFlow(context){
            remoteDataSource.getPosts()
        }
    }

}