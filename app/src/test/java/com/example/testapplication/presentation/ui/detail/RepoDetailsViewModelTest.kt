package com.example.testapplication.presentation.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.testapplication.MainCoroutineRule
import com.example.testapplication.domain.entity.Repo
import com.example.testapplication.domain.repository.RepoRepository
import com.example.testapplication.getOrAwaitValue
import io.mockk.every
import io.mockk.mockkClass
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.io.IOException

@RunWith(JUnit4::class)
@ExperimentalCoroutinesApi
class RepoDetailsViewModelTest {

    @Rule
    @JvmField
    val instantExecutor = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private val repository = mockkClass(RepoRepository::class)
    private lateinit var viewModel: RepoDetailsViewModel

    @Before
    fun init() {
        viewModel = RepoDetailsViewModel(repository)
    }

    @Test
    fun loadAndObserveRepo() = mainCoroutineRule.runBlockingTest {
        val expectedResult =
            Repo(1, "Title1", "FullName1", "Description1", Repo.Owner("login1", ""), 5)
        every { runBlocking { repository.getRepoById(expectedResult.id) } } returns expectedResult
        viewModel.getRepoById(expectedResult.id)
        val result = viewModel.repo.getOrAwaitValue()
        assertThat(result).isEqualTo(expectedResult)
    }

    @Test
    fun loadError() = mainCoroutineRule.runBlockingTest {
        assertThat(viewModel.snackBar.value).isNull()
        every { runBlocking { repository.getRepoById(any()) } } throws IOException("Test Exception")
        viewModel.getRepoById(1)
        assertThat(viewModel.snackBar.value).isNotNull
    }
}