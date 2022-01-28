package com.example.testapplication.presentation.core.di.module.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.testapplication.presentation.ui.detail.RepoDetailsViewModel
import com.example.testapplication.presentation.ui.list.RepoListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(RepoListViewModel::class)
    abstract fun bindRepoListViewModel(viewModel: RepoListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(RepoDetailsViewModel::class)
    abstract fun bindRepoDetailsViewModel(viewModel: RepoDetailsViewModel): ViewModel
}