package com.example.testapplication.presentation.ui.list

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import com.example.testapplication.MainCoroutineRule
import com.example.testapplication.collectDataForTest
import com.example.testapplication.domain.entity.Repo
import com.example.testapplication.domain.repository.RepoRepository
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.jupiter.api.assertThrows
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.io.IOException


@RunWith(JUnit4::class)
@ExperimentalCoroutinesApi
class RepoListViewModelTest {

    @Rule
    @JvmField
    val instantExecutor = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private val repository: RepoRepository = mockk(relaxed = true)
    private lateinit var viewModel: RepoListViewModel

    @Before
    fun init() {
        viewModel = RepoListViewModel(repository)
    }

    @ExperimentalPagingApi
    @Test
    fun loadAndObserveRepoList() {
        mainCoroutineRule.runBlockingTest {
            val expectedResult = listOf(
                Repo(1, "Title1", "FullName1", "Description1", Repo.Owner("login1", ""), 5),
                Repo(2, "Title2", "FullName2", "Description2", Repo.Owner("login2", ""), 5)
            )

            val pagingData = PagingData.from(expectedResult)
            val flow = flowOf(pagingData)
            every { repository.getRepoList() } returns flow

            val tmp = viewModel.getRepoList().first()
            val result = tmp.collectDataForTest()
            assertThat(expectedResult).hasSameElementsAs(result)
        }
    }

    @Test
    fun loadError() = mainCoroutineRule.runBlockingTest {
        every { runBlocking { repository.getRepoList() } } throws IOException("Test Exception")
        assertThrows<IOException> { viewModel.getRepoList() }
    }
}