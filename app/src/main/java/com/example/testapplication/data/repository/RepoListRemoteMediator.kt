package com.example.testapplication.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.testapplication.data.api.GithubService
import com.example.testapplication.data.database.AppDatabase
import com.example.testapplication.data.database.dao.RepoDao
import com.example.testapplication.domain.entity.Repo
import retrofit2.HttpException
import java.io.IOException

@ExperimentalPagingApi
class RepoListRemoteMediator(
    private val githubService: GithubService,
    private val database: AppDatabase,
    private val repoDao: RepoDao = database.getRepoDao(),
) : RemoteMediator<Int, Repo>() {

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(loadType: LoadType, state: PagingState<Int, Repo>): MediatorResult {
        try {
            val repoId = state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()?.id

            val loadKey = when (loadType) {
                LoadType.REFRESH -> null
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> repoId
            }

            // Test loading showing
            // delay(5000)

            val data = githubService.getRepoList(loadKey ?: 0)

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    repoDao.deleteAll()
                }
                repoDao.insertAll(data)
            }
            return MediatorResult.Success(endOfPaginationReached = data.isEmpty())
        } catch (e: IOException) {
            return MediatorResult.Error(e)
        } catch (e: HttpException) {
            return MediatorResult.Error(e)
        }
    }
}