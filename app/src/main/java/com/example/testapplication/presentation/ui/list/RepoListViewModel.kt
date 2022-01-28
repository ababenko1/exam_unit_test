package com.example.testapplication.presentation.ui.list

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.testapplication.domain.entity.Repo
import com.example.testapplication.domain.repository.RepoRepository
import com.example.testapplication.presentation.core.base.BaseViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RepoListViewModel @Inject constructor(private val repository: RepoRepository) :
    BaseViewModel() {

    val repos: Flow<PagingData<Repo>>

    init {
        repos = getRepoList()
    }

    fun getRepoList(): Flow<PagingData<Repo>> = repository.getRepoList().cachedIn(viewModelScope)
}
