package com.example.testapplication.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.testapplication.data.api.GithubService
import com.example.testapplication.data.database.AppDatabase
import com.example.testapplication.domain.entity.Repo
import com.example.testapplication.domain.repository.RepoRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

private const val PAGE_SIZE = 100

class RepoRepositoryImpl @Inject constructor(
    private val githubService: GithubService,
    private val database: AppDatabase,
    private val ioDispatcher: CoroutineDispatcher,
) : RepoRepository {

    @OptIn(ExperimentalPagingApi::class)
    override fun getRepoList(): Flow<PagingData<Repo>> =
        Pager(
            config = PagingConfig(PAGE_SIZE, PAGE_SIZE, true),
            remoteMediator = RepoListRemoteMediator(githubService, database)
        ) {
            database.getRepoDao().getAll()
        }.flow

    override suspend fun getRepoById(repoId: Int): Repo = withContext(ioDispatcher) {
        val result = githubService.getRepoById(repoId)
        database.getRepoDao().update(result)
        database.getRepoDao().getRepoById(result.id)
    }
}