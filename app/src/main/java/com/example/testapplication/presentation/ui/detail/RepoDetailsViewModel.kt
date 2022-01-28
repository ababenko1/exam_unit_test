package com.example.testapplication.presentation.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.testapplication.domain.entity.Repo
import com.example.testapplication.domain.repository.RepoRepository
import com.example.testapplication.presentation.core.base.BaseViewModel
import javax.inject.Inject

class RepoDetailsViewModel @Inject constructor(private val repository: RepoRepository) :
    BaseViewModel() {

    private val _repo = MutableLiveData<Repo>()
    val repo: LiveData<Repo> = _repo

    fun getRepoById(repoId: Int) {
        launchDataLoad {
            val result = repository.getRepoById(repoId)
            _repo.postValue(result)
        }
    }
}