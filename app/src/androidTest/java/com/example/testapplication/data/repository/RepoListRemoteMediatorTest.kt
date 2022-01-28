package com.example.testapplication.data.repository

import androidx.paging.*
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.testapplication.data.api.GithubService
import com.example.testapplication.data.database.AppDatabase
import com.example.testapplication.domain.entity.Repo
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException


@ExperimentalPagingApi
@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
class RepoListRemoteMediatorTest {

    private val mockRepos = listOf(
        Repo(1, "Name1", "FullName1", "Description1", Repo.Owner("login1", ""), 5),
        Repo(2, "Name2", "FullName2", "Description2", Repo.Owner("login2", ""), 5)
    )
    private val githubService = mockk<GithubService>()

    private val mockDatabase = Room.inMemoryDatabaseBuilder(
        ApplicationProvider.getApplicationContext(),
        AppDatabase::class.java
    ).build()

    @After
    fun tearDown() {
        mockDatabase.clearAllTables()
    }

    @Test
    fun refreshLoadReturnsSuccessResultWhenMoreDataIsPresent() {
        runBlocking {
            val remoteMediator = RepoListRemoteMediator(
                githubService,
                mockDatabase
            )
            every { runBlocking { githubService.getRepoList(any()) } } returns mockRepos
            val pagingState = PagingState<Int, Repo>(
                listOf(),
                null,
                PagingConfig(100),
                100
            )

            val result = remoteMediator.load(LoadType.REFRESH, pagingState)

            assertThat(result).isInstanceOf(RemoteMediator.MediatorResult.Success::class.java)
            assertThat((result as RemoteMediator.MediatorResult.Success).endOfPaginationReached).isEqualTo(
                false
            )
        }
    }

    @Test
    fun refreshLoadSuccessAndEndOfPaginationWhenNoMoreData() {
        runBlocking {
            val remoteMediator = RepoListRemoteMediator(
                githubService,
                mockDatabase
            )
            every { runBlocking { githubService.getRepoList(any()) } } returns emptyList()
            val pagingState = PagingState<Int, Repo>(
                listOf(),
                null,
                PagingConfig(100),
                100
            )
            val result = remoteMediator.load(LoadType.REFRESH, pagingState)
            assertThat(result).isInstanceOf(RemoteMediator.MediatorResult.Success::class.java)
            assertThat((result as RemoteMediator.MediatorResult.Success).endOfPaginationReached).isEqualTo(
                true)
        }
    }

    @Test
    fun refreshLoadReturnsErrorResultWhenErrorOccurs() {
        runBlocking {
            val remoteMediator = RepoListRemoteMediator(
                githubService,
                mockDatabase
            )
            every { runBlocking { githubService.getRepoList(any()) } } throws IOException("Test Exception")
            val pagingState = PagingState<Int, Repo>(
                listOf(),
                null,
                PagingConfig(100),
                100
            )
            val result = remoteMediator.load(LoadType.REFRESH, pagingState)
            assertThat(result).isInstanceOf(RemoteMediator.MediatorResult.Error::class.java)
        }
    }
}