package com.example.testapplication.domain.repository

import androidx.paging.PagingData
import com.example.testapplication.domain.entity.Repo
import kotlinx.coroutines.flow.Flow

interface RepoRepository {

    fun getRepoList(): Flow<PagingData<Repo>>
    suspend fun getRepoById(repoId: Int): Repo
}