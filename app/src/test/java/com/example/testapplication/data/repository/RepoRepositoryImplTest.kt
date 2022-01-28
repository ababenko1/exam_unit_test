package com.example.testapplication.data.repository


import com.example.testapplication.MainCoroutineRule
import com.example.testapplication.data.api.GithubService
import com.example.testapplication.data.database.AppDatabase
import com.example.testapplication.data.database.dao.RepoDao
import com.example.testapplication.domain.entity.Repo
import com.example.testapplication.domain.repository.RepoRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkClass
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4


@RunWith(JUnit4::class)
@ExperimentalCoroutinesApi
class RepoRepositoryImplTest {

    private lateinit var repository: RepoRepository
    private val dao = mockk<RepoDao>()
    private val service = mockkClass(GithubService::class)
    private val database = mockk<AppDatabase>()

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @Before
    fun init() {
        every { database.getRepoDao() } returns dao
        repository = RepoRepositoryImpl(service, database, Dispatchers.Main)
    }

    @Test
    fun loadRepoById() {
        mainCoroutineRule.runBlockingTest {
            val expectedResult =
                Repo(1, "Title1", "FullName1", "Description1", Repo.Owner("login1", ""), 5)
            every { runBlocking { dao.update(expectedResult) } } returns Unit
            every { runBlocking { dao.getRepoById(expectedResult.id) } } returns expectedResult
            every { runBlocking { service.getRepoById(expectedResult.id) } } returns expectedResult

            val result = repository.getRepoById(expectedResult.id)
            assertThat(result).isEqualTo(expectedResult)
        }
    }
}