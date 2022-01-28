package com.example.testapplication.data.database

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.testapplication.domain.entity.Repo
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
@ExperimentalCoroutinesApi
class RepoDaoTest : DbTest() {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun insertAndRead() {
        runBlockingTest {
            val repo = Repo(1, "Name", "FullName", "Description", Repo.Owner("login", ""), 5)
            database.getRepoDao().insert(repo)
            val loadedRepoFromDatabase = database.getRepoDao().getRepoById(repo.id)
            assertThat(loadedRepoFromDatabase).isEqualTo(repo)
        }
    }

    @Test
    fun updateAndGetById() = runBlockingTest {
        val repo = Repo(1, "Name", "FullName", "Description", Repo.Owner("login", ""), 5)
        database.getRepoDao().insert(repo)
        val updatedRepo = Repo(1,
            "NameUpdated",
            "FullNameUpdated",
            "DescriptionUpdated",
            Repo.Owner("login", ""),
            5)
        database.getRepoDao().update(updatedRepo)
        val retrievedRepo: Repo = database.getRepoDao().getRepoById(repo.id)
        assertThat(retrievedRepo).isEqualTo(updatedRepo)
    }
}